package com.aoranzhang.ezrentback.spring.security

import com.aoranzhang.ezrentback.data.entity.Role
import com.google.common.collect.ImmutableList
import org.springframework.security.authentication.AnonymousAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import java.io.IOException
import org.springframework.web.filter.GenericFilterBean
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


class JwtTokenFilter(private val jwtTokenProvider: JwtTokenProvider) : GenericFilterBean() {

    @Throws(IOException::class, ServletException::class)
    override fun doFilter(req: ServletRequest, res: ServletResponse, filterChain: FilterChain) {

        val token = jwtTokenProvider.resolveToken(req as HttpServletRequest)
        try {
            if (token != null && jwtTokenProvider.validateToken(token)) {
                val auth = jwtTokenProvider.getAuthentication(token)
                SecurityContextHolder.getContext().authentication = auth
            }else {
                SecurityContextHolder.getContext().authentication = AnonymousAuthenticationToken("anonymous", "anonymous", ImmutableList.of(Role.ROLE_CLIENT))
            }
        } catch (ex: SecurityException) {
            val response = res as HttpServletResponse
            response.sendError(ex.httpStatus.value(), ex.message)
            return
        }

        filterChain.doFilter(req, res)
    }

}