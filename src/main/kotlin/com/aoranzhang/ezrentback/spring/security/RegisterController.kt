package com.aoranzhang.ezrentback.spring.security

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.social.connect.Connection
import org.springframework.social.connect.ConnectionFactoryLocator
import org.springframework.social.connect.UserProfile
import org.springframework.social.connect.UsersConnectionRepository
import org.springframework.social.connect.web.ProviderSignInUtils
import org.springframework.social.facebook.api.Facebook
import org.springframework.social.facebook.api.User
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.view.RedirectView

import javax.servlet.http.HttpSession

@Controller
@RequestMapping("/register")
class RegisterController @Autowired constructor(
        @Value("\${application.URL}") private val applicationURL: String,
        private var connectionFactoryLocator: ConnectionFactoryLocator,
        private val usersConnectionRepository: UsersConnectionRepository
){

    @GetMapping
    fun postSocial(request: WebRequest): RedirectView {
        val providerSignInUtils = ProviderSignInUtils(connectionFactoryLocator, usersConnectionRepository)

        val connection = providerSignInUtils.getConnectionFromSession(request)
        val redirectView = RedirectView(applicationURL + "/registerWithSocial")

        if (connection != null) {
            if(connection.api is Facebook) {
                val userProfile = (connection.api as Facebook).fetchObject("me", User::class.java, "id", "email", "name")
                redirectView.addStaticAttribute("email", userProfile.email)
                redirectView.addStaticAttribute("name", userProfile.name)
            }else {
                val userProfile = connection.fetchUserProfile()
                redirectView.addStaticAttribute("email", userProfile.email)
                redirectView.addStaticAttribute("name", userProfile.name)
            }
        }

        return redirectView
    }
}
