package com.aoranzhang.ezrentback.data.graphql.resolvers

import com.aoranzhang.ezrentback.data.entity.Building
import com.aoranzhang.ezrentback.data.entity.Room
import com.aoranzhang.ezrentback.data.entity.Suite
import com.aoranzhang.ezrentback.data.entity.User
import com.aoranzhang.ezrentback.service.BuildingService
import com.aoranzhang.ezrentback.service.RoomService
import com.aoranzhang.ezrentback.service.SuiteService
import com.aoranzhang.ezrentback.service.UserService
import com.aoranzhang.ezrentback.spring.security.JwtTokenProvider
import com.coxautodev.graphql.tools.GraphQLMutationResolver
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.social.connect.ConnectionFactoryLocator
import org.springframework.social.connect.UsersConnectionRepository
import org.springframework.social.connect.web.ProviderSignInUtils
import org.springframework.stereotype.Component
import org.springframework.web.context.request.RequestContextHolder
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
        private val jwtTokenProvider: JwtTokenProvider
) : GraphQLMutationResolver {

    fun register(email: String, username: String, password: String): String? {

        val existing = userService.getUserByEmail(email)
        if (existing != null) {
            return null
        }

        val user = User(username, email, password, Date(), HashSet())

        try {
            userService.saveUser(user)
            val providerSignInUtils = ProviderSignInUtils(connectionFactoryLocator, usersConnectionRepository)

            val webRequest = RequestContextHolder.getRequestAttributes()

            providerSignInUtils.doPostSignUp(user.email, webRequest)

            return jwtTokenProvider.createToken(user.email, user.roles)
        } catch (e: Exception) {
            return null
        }
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
