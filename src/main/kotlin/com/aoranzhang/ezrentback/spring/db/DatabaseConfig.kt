package com.aoranzhang.ezrentback.spring.db

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary

import javax.sql.DataSource
import org.apache.tomcat.jni.SSL.setPassword
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.env.Environment
import org.springframework.jdbc.datasource.DriverManagerDataSource



@Configuration
class DatabaseConfig {
    @Autowired
    lateinit var env : Environment

    @Bean
    @Primary
    fun dataSource() : DataSource{
        val dataSource = DriverManagerDataSource()
        dataSource.setDriverClassName(env.getProperty("spring.datasource.driverClassName")!!)
        dataSource.url = env.getProperty("spring.datasource.url")
        dataSource.username = env.getProperty("spring.datasource.username")
        dataSource.password = env.getProperty("spring.datasource.password")

        return dataSource

    }
}

