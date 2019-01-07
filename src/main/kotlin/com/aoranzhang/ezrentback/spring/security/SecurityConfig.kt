package com.aoranzhang.ezrentback.spring.security

import com.aoranzhang.ezrentback.service.UserService
import com.aoranzhang.ezrentback.spring.social.SocialSignInAdapter
import com.google.common.collect.ImmutableList
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.encrypt.Encryptors
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler
import org.springframework.social.connect.ConnectionFactoryLocator
import org.springframework.social.connect.UsersConnectionRepository
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository
import org.springframework.social.connect.web.ProviderSignInController
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

import javax.sql.DataSource

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
class SecurityConfig : WebSecurityConfigurerAdapter() {

    @Autowired
    private lateinit var dataSource: DataSource

    @Value("\${application.URL}")
    private lateinit var applicationURL: String

    @Autowired
    private lateinit var userService: UserService

    @Autowired
    private lateinit var connectionFactoryLocator: ConnectionFactoryLocator

    @Autowired
    private lateinit var jwtTokenProvider: JwtTokenProvider

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {

        http
                .csrf().disable()
                .cors()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .anyRequest().permitAll()
                .and()
                .exceptionHandling().accessDeniedPage("/login")
                .and()
                .apply(JwtTokenFilterConfigurer(jwtTokenProvider))
    }

    @Bean
    fun providerSignInController(): ProviderSignInController {
        val providerSignInController = ProviderSignInController(
                connectionFactoryLocator,
                usersConnectionRepository(),
                SocialSignInAdapter(userService))
        providerSignInController.setSignUpUrl("/register")
        providerSignInController.setPostSignInUrl(applicationURL)
        return providerSignInController
    }

    @Bean
    @Primary
    fun usersConnectionRepository(): UsersConnectionRepository {
        val textEncryptor = Encryptors.noOpText()
        return JdbcUsersConnectionRepository(dataSource, connectionFactoryLocator, textEncryptor)
    }

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val configuration = CorsConfiguration()
        configuration.allowedOrigins = ImmutableList.of("http://localhost:3000","https://localhost:3000", "http://ezrent.aoranzhang.com", "https://ezrent.aoranzhang.com")
        configuration.allowedMethods = ImmutableList.of("HEAD",
                "GET", "POST", "PUT", "DELETE", "PATCH")
        // setAllowCredentials(true) is important, otherwise:
        // The value of the 'Access-Control-Allow-Origin' header in the response must not be the wildcard '*' when the request's credentials mode is 'include'.
        configuration.allowCredentials = true
        // setAllowedHeaders is important! Without it, OPTIONS preflight request
        // will fail with 403 Invalid CORS request
        configuration.allowedHeaders = ImmutableList.of("Authorization", "Cache-Control", "Content-Type", "access-control-allow-origin")
        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configuration)
        return source
    }
}
