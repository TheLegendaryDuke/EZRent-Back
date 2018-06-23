package com.aoranzhang.ezrentback.spring.security

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.social.connect.Connection
import org.springframework.social.connect.ConnectionFactoryLocator
import org.springframework.social.connect.UserProfile
import org.springframework.social.connect.UsersConnectionRepository
import org.springframework.social.connect.web.ProviderSignInUtils
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.view.RedirectView

import javax.servlet.http.HttpSession

@Controller
@RequestMapping("/register")
class RegisterController {

    @Value("\${application.URL}")
    private val applicationURL: String? = null

    @Autowired
    private val httpSession: HttpSession? = null

    @Autowired
    internal var connectionFactoryLocator: ConnectionFactoryLocator? = null

    @Autowired
    private val usersConnectionRepository: UsersConnectionRepository? = null

    @GetMapping
    fun postSocial(request: WebRequest): RedirectView {
        val providerSignInUtils = ProviderSignInUtils(connectionFactoryLocator, usersConnectionRepository)

        val connection = providerSignInUtils.getConnectionFromSession(request)
        val redirectView = RedirectView(applicationURL!! + "/registerWithSocial")

        if (connection != null) {
            val userProfile = connection.fetchUserProfile()

            redirectView.addStaticAttribute("email", userProfile.getEmail())
            redirectView.addStaticAttribute("roomType", userProfile.getName())
        }

        return redirectView
    }
}
