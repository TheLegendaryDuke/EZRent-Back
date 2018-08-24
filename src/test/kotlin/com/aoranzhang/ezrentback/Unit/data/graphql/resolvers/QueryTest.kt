package com.aoranzhang.ezrentback.Unit.data.graphql.resolvers

import com.aoranzhang.ezrentback.data.entity.User
import com.aoranzhang.ezrentback.data.graphql.resolvers.Mutation
import com.aoranzhang.ezrentback.data.graphql.resolvers.Query
import com.aoranzhang.ezrentback.service.BuildingService
import com.aoranzhang.ezrentback.service.CityService
import com.aoranzhang.ezrentback.service.RoomService
import com.aoranzhang.ezrentback.service.SuiteService
import com.aoranzhang.ezrentback.service.UserService
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.springframework.social.connect.ConnectionFactoryLocator
import org.springframework.social.connect.UsersConnectionRepository
import java.util.*
import javax.servlet.http.HttpSession
import kotlin.collections.HashSet

class QueryTest {
    companion object {
        val userServiceMock = Mockito.mock(UserService::class.java)
        val cityServiceMock = mock(CityService::class.java)
        val buildingServiceMock = Mockito.mock(BuildingService::class.java)
        val httpSessionMock = Mockito.mock(HttpSession::class.java)
        val query = Query(httpSessionMock, userServiceMock, cityServiceMock, buildingServiceMock)
        val testEmail = "snake@mgs.com"
        val testName = "big boss"
        val testPassword = "12345"
    }

    @Test
    fun testUserQueryOk() {
        val user = User(testName, testEmail, testPassword, Date(), HashSet())

        `when`(httpSessionMock.getAttribute("userEmail")).thenReturn(testEmail)
        `when`(userServiceMock.getUserByEmail(testEmail)).thenReturn(user)

        assertEquals(query.user(), user)
    }

    @Test
    fun testUserQueryNotLoggedIn() {
        `when`(httpSessionMock.getAttribute("userEmail")).thenReturn(null)

        assertNull(query.user())
    }


}