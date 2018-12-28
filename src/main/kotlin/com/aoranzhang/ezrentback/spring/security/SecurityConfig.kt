package com.aoranzhang.ezrentback.spring.security

import com.google.common.collect.ImmutableList
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
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
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

import javax.sql.DataSource

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
class SecurityConfig : WebSecurityConfigurerAdapter() {

    @Autowired
    private lateinit var dataSource: DataSource

//    @Value("\${application.URL}")
//    private lateinit var applicationURL: String

    @Autowired
    private lateinit var connectionFactoryLocator: ConnectionFactoryLocator

//    @Autowired
//    private lateinit var usersConnectionRepository: UsersConnectionRepository

    @Autowired
    private lateinit var jwtTokenProvider: JwtTokenProvider

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {

        val handler = SimpleUrlLogoutSuccessHandler()
        handler.setUseReferer(true)

        http
                .csrf().disable()
                .cors()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/login", "/login/**", "/register/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .exceptionHandling().accessDeniedPage("/login")
                .and()
                .apply(JwtTokenFilterConfigurer(jwtTokenProvider))
    }

//    @Throws(Exception::class)
//    override fun configure(auth: AuthenticationManagerBuilder?) {
//        auth!!.jdbcAuthentication()
//                .usersByUsernameQuery(usersQuery)
//                .authoritiesByUsernameQuery(authoritiesQuery)
//                .dataSource(dataSource)
//                .passwordEncoder(bCryptPasswordEncoder)
//    }

    @Bean
    @Primary
    fun usersConnectionRepository(): UsersConnectionRepository {
        val textEncryptor = Encryptors.noOpText()
        return JdbcUsersConnectionRepository(dataSource, connectionFactoryLocator, textEncryptor)
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
