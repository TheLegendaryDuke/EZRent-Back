package com.aoranzhang.ezrentback.spring.social

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment
import org.springframework.security.crypto.encrypt.Encryptors
import org.springframework.social.UserIdSource
import org.springframework.social.config.annotation.ConnectionFactoryConfigurer
import org.springframework.social.config.annotation.EnableSocial
import org.springframework.social.config.annotation.SocialConfigurer
import org.springframework.social.connect.ConnectionFactoryLocator
import org.springframework.social.connect.UsersConnectionRepository
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository
import org.springframework.social.facebook.connect.FacebookConnectionFactory
import org.springframework.social.google.connect.GoogleConnectionFactory
import org.springframework.social.security.AuthenticationNameUserIdSource
import org.springframework.social.twitter.connect.TwitterConnectionFactory

import javax.sql.DataSource

@Configuration
@EnableSocial
class SocialConfig : SocialConfigurer {

    @Autowired
    internal var dataSource: DataSource? = null

    override fun getUserIdSource() = AuthenticationNameUserIdSource()

    override fun getUsersConnectionRepository(connectionFactoryLocator: ConnectionFactoryLocator): UsersConnectionRepository {
        return JdbcUsersConnectionRepository(dataSource, connectionFactoryLocator, Encryptors.noOpText())
    }

    override fun addConnectionFactories(cfConfig: ConnectionFactoryConfigurer, env: Environment) {
        cfConfig.addConnectionFactory(TwitterConnectionFactory(
                env.getProperty("twitter.app-id"),
                env.getProperty("twitter.app-secret")))
        val facebookConnectionFactory = FacebookConnectionFactory(
                env.getProperty("facebook.app-id"),
                env.getProperty("facebook.app-secret"))
        facebookConnectionFactory.setScope("public_profile,email")
        cfConfig.addConnectionFactory(facebookConnectionFactory)
        val googleConnectionFactory = GoogleConnectionFactory(
                env.getProperty("google.app-id"),
                env.getProperty("google.app-secret"))
        googleConnectionFactory.setScope("profile email")
        cfConfig.addConnectionFactory(googleConnectionFactory)
    }
}

