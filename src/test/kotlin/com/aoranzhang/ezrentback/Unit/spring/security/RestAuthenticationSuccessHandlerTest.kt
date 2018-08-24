package com.aoranzhang.ezrentback.Unit.spring.security

import com.aoranzhang.ezrentback.service.UserService
import com.aoranzhang.ezrentback.spring.security.RestAuthenticationSuccessHandler
import org.joda.time.DateTime
import org.springframework.security.core.userdetails.User
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.springframework.security.core.Authentication
import java.util.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpSession
import kotlin.collections.HashSet

class RestAuthenticationSuccessHandlerTest {
    companion object {
        val httpSessionMock = mock(HttpSession::class.java)
        val userServiceMock = mock(UserService::class.java)
        val httpServletResponseMock = mock(HttpServletResponse::class.java)
        val authenticationMock = mock(Authentication::class.java)
        val restAuthenticationSuccessHandler = RestAuthenticationSuccessHandler(httpSessionMock, userServiceMock)
        val testEmail = "snake@mgs.com";
        val testName = "big boss"
    }

    @Test
    fun testOnAuthenticationSuccess() {
        val userMock = mock(User::class.java)
        `when`(authenticationMock.principal).thenReturn(userMock)
        `when`(userMock.username).thenReturn(testEmail)
        `when`(userServiceMock.getUserByEmail(testEmail)).thenReturn(com.aoranzhang.ezrentback.data.entity.User(testName, testEmail, "doesn't matter", Date(), HashSet()))

        restAuthenticationSuccessHandler.onAuthenticationSuccess(mock(HttpServletRequest::class.java), httpServletResponseMock, authenticationMock)

        verify(httpServletResponseMock, times(1)).status = HttpServletResponse.SC_OK
        verify(httpSessionMock, times(1)).setAttribute("userEmail", testEmail)
        verify(httpSessionMock, times(1)).setAttribute("userName", testName)
    }
}