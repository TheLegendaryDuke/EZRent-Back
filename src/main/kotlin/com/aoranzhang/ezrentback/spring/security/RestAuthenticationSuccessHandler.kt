package com.aoranzhang.ezrentback.spring.security

import com.aoranzhang.ezrentback.service.UserService
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.AuthenticationSuccessHandler

import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import java.io.IOException

class RestAuthenticationSuccessHandler(private val userService: UserService) : AuthenticationSuccessHandler {

    @Throws(ServletException::class, IOException::class)
    override fun onAuthenticationSuccess(
            request: HttpServletRequest,
            response: HttpServletResponse,
            authentication: Authentication) {

        response.status = HttpServletResponse.SC_OK;

        val email = (authentication.principal as org.springframework.security.core.userdetails.User).username
    }
}
