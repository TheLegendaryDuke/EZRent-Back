package com.aoranzhang.ezrentback.spring.security

import com.aoranzhang.ezrentback.service.UserService
import com.aoranzhang.ezrentback.spring.social.RestProviderSignInController
import com.aoranzhang.ezrentback.spring.social.SocialSignInAdapter
import com.google.common.collect.ImmutableList
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Primary
import org.springframework.core.env.Environment
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.encrypt.Encryptors
import org.springframework.security.crypto.encrypt.TextEncryptor
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import org.springframework.social.connect.ConnectionFactoryLocator
import org.springframework.social.connect.UsersConnectionRepository
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository
import org.springframework.social.connect.web.ProviderSignInController
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

import javax.servlet.http.HttpSession
import javax.sql.DataSource

@EnableWebSecurity
class SecurityConfig : WebSecurityConfigurerAdapter() {

    @Autowired
    private val userService: UserService? = null

    @Autowired
    private val bCryptPasswordEncoder: BCryptPasswordEncoder? = null

    @Autowired
    private val dataSource: DataSource? = null

    @Value("\${spring.queries.users-query}")
    private val usersQuery: String? = null

    @Value("\${spring.queries.authorities-query}")
    private val authoritiesQuery: String? = null

    @Value("\${application.URL}")
    private val applicationURL: String? = null

    @Autowired
    private val connectionFactoryLocator: ConnectionFactoryLocator? = null

    @Autowired
    private val usersConnectionRepository: UsersConnectionRepository? = null

    @Autowired
    private val httpSession: HttpSession? = null

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {

        val handler = SimpleUrlLogoutSuccessHandler()
        handler.setUseReferer(true)

        http
                .csrf().disable()
                .cors()
                .and()
                .authorizeRequests()
                .antMatchers("/login", "/login/**", "/register/**", "/graphiql/**", "/graphql/**", "/user", "/vendor/**").permitAll()
                .anyRequest().denyAll()
                .and()
                .formLogin()
                .loginPage("/login")
                .successHandler(RestAuthenticationSuccessHandler(httpSession!!, userService!!))
                .failureHandler(SimpleUrlAuthenticationFailureHandler())
                .usernameParameter("email")
                .passwordParameter("password")
                .permitAll()
                .and().logout().logoutSuccessUrl("/")
                .logoutSuccessHandler(handler)
                .logoutRequestMatcher(AntPathRequestMatcher("/logout"))
    }

    @Throws(Exception::class)
    override fun configure(auth: AuthenticationManagerBuilder?) {
        auth!!.jdbcAuthentication()
                .usersByUsernameQuery(usersQuery)
                .authoritiesByUsernameQuery(authoritiesQuery)
                .dataSource(dataSource)
                .passwordEncoder(bCryptPasswordEncoder)
    }

    @Bean
    @Primary
    fun usersConnectionRepository(): UsersConnectionRepository {
        val textEncryptor = Encryptors.noOpText()
        return JdbcUsersConnectionRepository(dataSource, connectionFactoryLocator, textEncryptor)
    }

    @Bean
    fun providerSignInController(): ProviderSignInController {
        val providerSignInController = RestProviderSignInController(
                connectionFactoryLocator,
                usersConnectionRepository,
                SocialSignInAdapter(userService!!, httpSession!!))
        providerSignInController.setSignUpUrl("/register")
        providerSignInController.setPostSignInUrl(applicationURL)
        return providerSignInController
    }

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val configuration = CorsConfiguration()
        configuration.allowedOrigins = ImmutableList.of("*")
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
