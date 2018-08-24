package com.aoranzhang.ezrentback.spring.db


import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary

import javax.sql.DataSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Profile
import org.springframework.core.env.Environment
import org.springframework.jdbc.datasource.DriverManagerDataSource
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType.H2


@Configuration
class DatabaseConfig {
    @Autowired
    lateinit var env : Environment

    @Bean
    @Primary
    @Profile("!test")
    fun dataSource() : DataSource{
        val dataSource = DriverManagerDataSource()
        dataSource.setDriverClassName(env.getProperty("spring.datasource.driverClassName")!!)
        dataSource.url = env.getProperty("spring.datasource.url")
        dataSource.username = env.getProperty("spring.datasource.username")
        dataSource.password = env.getProperty("spring.datasource.password")

        return dataSource
    }

    @Bean
    @Primary
    @Profile("test")
    fun testDataSource() : DataSource{
        return EmbeddedDatabaseBuilder()
                .generateUniqueName(true)
                .setType(H2)
                .setScriptEncoding("UTF-8")
                .ignoreFailedDrops(true)
                .build()
    }
}

