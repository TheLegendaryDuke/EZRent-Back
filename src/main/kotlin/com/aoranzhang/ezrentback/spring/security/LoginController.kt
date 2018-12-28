package com.aoranzhang.ezrentback.spring.security

import com.aoranzhang.ezrentback.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/login")
class LoginController @Autowired constructor(
        private val passwordEncoder: BCryptPasswordEncoder,
        private val jwtTokenProvider: JwtTokenProvider,
        private val userService: UserService
) {
    @PostMapping
    fun login(email: String, password: String) : String {
        try {
            val user = userService.getUserByEmail(email)
            if (user == null || !passwordEncoder.matches(password, user.password)) {
                return "login failed"
            }else {
                return jwtTokenProvider.createToken(email, userService.getUserByEmail(email)!!.roles)
            }
        }catch (e : Exception) {
            return "login failed"
        }
    }
}