package com.aoranzhang.ezrentback.spring.db

import org.springframework.beans.factory.InitializingBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.ClassPathResource
import org.springframework.core.io.Resource
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator
import org.springframework.stereotype.Component

import javax.sql.DataSource

@Component
class DBInitializer @Autowired
constructor(private val dataSource: DataSource) : InitializingBean {

    @Throws(Exception::class)
    override fun afterPropertiesSet() {
        val resource = ClassPathResource("org/springframework/social/connect/jdbc/JdbcUsersConnectionRepository.sql")
        runScript(resource)
    }

    private fun runScript(resource: Resource) {
        val populator = ResourceDatabasePopulator()
        populator.setContinueOnError(true)
        populator.addScript(resource)
        DatabasePopulatorUtils.execute(populator, dataSource)
    }
}
