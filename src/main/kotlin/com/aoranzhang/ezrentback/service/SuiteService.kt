package com.aoranzhang.ezrentback.service

import com.aoranzhang.ezrentback.data.entity.Suite
import com.aoranzhang.ezrentback.data.repository.SuiteRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class SuiteService {
    @Autowired
    private lateinit var suiteRepository: SuiteRepository

    @Transactional
    fun saveSuite(suite: Suite) : Suite? {
        return suiteRepository.save(suite)
    }

    fun findSuite(id: String): Optional<Suite> {
        return suiteRepository.findById(id)
    }
}
