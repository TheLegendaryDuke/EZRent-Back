package com.aoranzhang.ezrentback.Unit.data.graphql.resolvers

import com.aoranzhang.ezrentback.data.entity.User
import com.aoranzhang.ezrentback.data.graphql.resolvers.Mutation
import com.aoranzhang.ezrentback.service.BuildingService
import com.aoranzhang.ezrentback.service.RoomService
import com.aoranzhang.ezrentback.service.SuiteService
import com.aoranzhang.ezrentback.service.UserService
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.springframework.social.connect.ConnectionFactoryLocator
import org.springframework.social.connect.UsersConnectionRepository
import java.util.*
import javax.servlet.http.HttpSession
import kotlin.collections.HashSet

class MutationTest {
    companion object {
        val userServiceMock = mock(UserService::class.java)
        val roomServiceMock = mock(RoomService::class.java)
        val suiteServiceMock = mock(SuiteService::class.java)
        val buildingServiceMock = mock(BuildingService::class.java)
        val connectionFactoryLocatorMock = mock(ConnectionFactoryLocator::class.java)
        val usersConnectionRepositoryMock = mock(UsersConnectionRepository::class.java)
        val httpSessionMock = mock(HttpSession::class.java)
        val mutation = Mutation(userServiceMock, roomServiceMock, suiteServiceMock, buildingServiceMock, connectionFactoryLocatorMock, usersConnectionRepositoryMock, httpSessionMock)
        val testEmail = "snake@mgs.com"
        val testName = "big boss"
        val testPassword = "12345"
    }

    @Test
    fun testRegisterAccountAlreadyRegistered() {
        `when`(userServiceMock.getUserByEmail(testEmail)).thenReturn(User(testName, testEmail, "doesn't matter", Date(), HashSet()))

        assertEquals(mutation.register(testEmail, testName, testPassword), null)
    }
}