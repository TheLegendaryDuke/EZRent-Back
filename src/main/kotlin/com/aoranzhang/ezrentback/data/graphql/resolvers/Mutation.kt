package com.aoranzhang.ezrentback.data.graphql.resolvers

import com.aoranzhang.ezrentback.data.entity.Building
import com.aoranzhang.ezrentback.data.entity.Room
import com.aoranzhang.ezrentback.data.entity.Suite
import com.aoranzhang.ezrentback.data.entity.User
import com.aoranzhang.ezrentback.service.BuildingService
import com.aoranzhang.ezrentback.service.RoomService
import com.aoranzhang.ezrentback.service.SuiteService
import com.aoranzhang.ezrentback.service.UserService
import com.coxautodev.graphql.tools.GraphQLMutationResolver
import com.google.common.collect.ImmutableSet
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.social.connect.ConnectionFactoryLocator
import org.springframework.social.connect.UsersConnectionRepository
import org.springframework.social.connect.web.ProviderSignInUtils
import org.springframework.stereotype.Component
import org.springframework.web.context.request.RequestContextHolder

import javax.servlet.http.HttpSession
import java.util.Date
import java.util.HashSet

@Component
class Mutation @Autowired constructor(
        private val userService: UserService,
        private val roomService: RoomService,
        private val suiteService: SuiteService,
        private val buildingService: BuildingService,
        private val connectionFactoryLocator: ConnectionFactoryLocator,
        private val usersConnectionRepository: UsersConnectionRepository,
        private val httpSession: HttpSession
) : GraphQLMutationResolver {

    fun register(email: String, username: String, password: String): User? {

        val existing = userService.getUserByEmail(email)
        if (existing != null) {
            return null
        }

        val user = User(username, email, password, Date(), HashSet())

        try {
            userService.saveUser(user)
            val providerSignInUtils = ProviderSignInUtils(connectionFactoryLocator, usersConnectionRepository)
            val usernamePasswordAuthenticationToken = UsernamePasswordAuthenticationToken(user.email, user.password, HashSet<GrantedAuthority>())
            SecurityContextHolder.getContext().authentication = usernamePasswordAuthenticationToken

            val webRequest = RequestContextHolder.getRequestAttributes()

            providerSignInUtils.doPostSignUp(user.email, webRequest)

            httpSession.setAttribute("userEmail", user.email)
            httpSession.setAttribute("userName", user.name)
        } catch (e: Exception) {
            return null
        }

        return user
    }

    fun updateRoom(room: Room): Room? {
        return roomService.saveRoom(room)
    }

    fun updateSuite(suite: Suite): Suite? {
        return suiteService.saveSuite(suite)
    }

    fun updateBuilding(building: Building): Building? {
        return buildingService.save(building)
    }
}
