package com.aoranzhang.ezrentback.spring.social

import com.aoranzhang.ezrentback.data.entity.User
import com.aoranzhang.ezrentback.service.UserService
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.social.connect.web.SignInAdapter
import org.springframework.web.context.request.NativeWebRequest
import javax.servlet.http.HttpSession
import java.util.HashSet

class SocialSignInAdapter(private val userService: UserService, private val httpSession: HttpSession) : SignInAdapter {

    override fun signIn(localUserId: String, connection: org.springframework.social.connect.Connection<*>, request: NativeWebRequest): String? {
        val user = userService.getUserByEmail(localUserId)

        SecurityContextHolder.getContext().authentication = UsernamePasswordAuthenticationToken(localUserId, user?.password, HashSet<GrantedAuthority>())
        httpSession.setAttribute("userEmail", user?.email)
        httpSession.setAttribute("userName", user?.name)

        return null
    }

    companion object {

        private val logger = LogFactory.getLog(SocialSignInAdapter::class.java)
    }
}

