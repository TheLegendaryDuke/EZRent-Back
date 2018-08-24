package com.aoranzhang.ezrentback.Unit.spring.security

import com.aoranzhang.ezrentback.spring.security.RegisterController
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.springframework.social.connect.Connection
import org.springframework.social.connect.ConnectionFactoryLocator
import org.springframework.social.connect.UserProfile
import org.springframework.social.connect.UsersConnectionRepository
import org.springframework.social.connect.web.ProviderSignInAttempt
import org.springframework.social.facebook.api.Facebook
import org.springframework.social.facebook.api.User
import org.springframework.social.google.api.Google
import org.springframework.social.twitter.api.Twitter
import org.springframework.web.context.request.RequestAttributes
import org.springframework.web.context.request.WebRequest
import java.util.*
import javax.servlet.http.HttpSession

class RegisterControllerTest {

    companion object {
        val applicationURL = "myApp.com"
        val httpSessionMock = mock(HttpSession::class.java)
        val connectionFactoryLocatorMock = mock(ConnectionFactoryLocator::class.java)
        val usersConnectionRepositoryMock = mock(UsersConnectionRepository::class.java)
        val userInfoMap = HashMap<String, String>()
        val registerController = RegisterController(applicationURL, httpSessionMock, connectionFactoryLocatorMock, usersConnectionRepositoryMock)
        val webRequestMock = mock(WebRequest::class.java)
        val connectionMock = mock(Connection::class.java)
        val providerSignInAttemptMock = mock(ProviderSignInAttempt::class.java)
        val facebookMock = mock(Facebook::class.java)
        val twitterMock = mock(Twitter::class.java)
        val googleMock = mock(Google::class.java)
        val userMock = mock(User::class.java)
        val userProfileMock = mock(UserProfile::class.java)
    }

    @Before
    fun init() {
        if (userInfoMap.isEmpty()) {
            userInfoMap.put("email", "snake@mgs.com")
            userInfoMap.put("name", "big boss")
        }

        `when`(userMock.email).thenReturn(userInfoMap["email"])
        `when`(userMock.name).thenReturn(userInfoMap["name"])

        `when`(userProfileMock.email).thenReturn(userInfoMap["email"])
        `when`(userProfileMock.name).thenReturn(userInfoMap["name"])

        //Alert: This implementation depends on the spring social's implementation of providersigninutil to work
        `when`(webRequestMock.getAttribute(ProviderSignInAttempt.SESSION_ATTRIBUTE, RequestAttributes.SCOPE_SESSION)).thenReturn(providerSignInAttemptMock)
        `when`(providerSignInAttemptMock.getConnection(connectionFactoryLocatorMock)).thenReturn(connectionMock)

        `when`(facebookMock.fetchObject("me", User::class.java, "id", "email", "name")).thenReturn(userMock)
        `when`(connectionMock.fetchUserProfile()).thenReturn(userProfileMock)
    }

    @Test
    fun testPostSocialGetCallFacebook() {
        `when`(connectionMock.api).thenReturn(facebookMock)

        val redirectView = registerController.postSocial(webRequestMock)

        for(pair in userInfoMap.entries) {
            redirectView.attributesMap.containsKey(pair.key)
            assertEquals(redirectView.attributesMap[pair.key], pair.value)
        }
        assertEquals(redirectView.url, "$applicationURL/registerWithSocial")
    }

    @Test
    fun testPostSocialGetCallTwitter() {
        `when`(connectionMock.api).thenReturn(twitterMock)

        val redirectView = registerController.postSocial(webRequestMock)

        for(pair in userInfoMap.entries) {
            redirectView.attributesMap.containsKey(pair.key)
            assertEquals(redirectView.attributesMap[pair.key], pair.value)
        }
        assertEquals(redirectView.url, "$applicationURL/registerWithSocial")
    }

    @Test
    fun testPostSocialGetCallGoogle() {
        `when`(connectionMock.api).thenReturn(googleMock)

        val redirectView = registerController.postSocial(webRequestMock)

        for(pair in userInfoMap.entries) {
            redirectView.attributesMap.containsKey(pair.key)
            assertEquals(redirectView.attributesMap[pair.key], pair.value)
        }
        assertEquals(redirectView.url, "$applicationURL/registerWithSocial")
    }
}