package com.aoranzhang.ezrentback.spring.security

import com.aoranzhang.ezrentback.data.entity.Role
import com.aoranzhang.ezrentback.service.UserService
import io.jsonwebtoken.JwtException
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import java.util.stream.Collectors
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import java.util.*
import javax.annotation.PostConstruct
import javax.servlet.http.HttpServletRequest


@Component
class JwtTokenProvider {

    /**
     * THIS IS NOT A SECURE PRACTICE! For simplicity, we are storing a static key here. Ideally, in a
     * microservices environment, this key would be kept on a config-server.
     */
    @Value("\${jwt-secret}")
    private var secretKey: String? = null

    private val validityInMilliseconds: Long = 3600000 // 1h

    @Autowired
    private val userService: UserService? = null

    @PostConstruct
    protected fun init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey!!.toByteArray())
    }

    fun createToken(username: String, roles: Set<Role>): String {

        val claims = Jwts.claims().setSubject(username)
        claims.put("auth", roles.stream().map { s -> SimpleGrantedAuthority(s.getAuthority()) }.filter({t -> t != null }).collect(Collectors.toList()))
        claims.put("name", userService!!.getUserByEmail(username)!!.name)

        val now = Date()
        val validity = Date(now.getTime() + validityInMilliseconds)

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact()
    }

    fun getAuthentication(token: String): Authentication {
        val user = userService!!.getUserByEmail(getUsername(token))!!
        return UsernamePasswordAuthenticationToken(user, user.password, user.roles)
    }

    fun getUsername(token: String): String {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject()
    }

    fun resolveToken(req: HttpServletRequest): String? {
        val bearerToken = req.getHeader("Authorization")
        return if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            bearerToken.substring(7, bearerToken.length)
        } else null
    }

    fun validateToken(token: String): Boolean {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token)
            return true
        } catch (e: JwtException) {
            throw SecurityException("Expired or invalid JWT token", HttpStatus.INTERNAL_SERVER_ERROR)
        } catch (e: IllegalArgumentException) {
            throw SecurityException("Expired or invalid JWT token", HttpStatus.INTERNAL_SERVER_ERROR)
        }

    }

}