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
    private val suiteRepository: SuiteRepository? = null

    @Transactional
    fun saveSuite(suite: Suite) {
        val existing = suiteRepository!!.findById(suite.id)
        if(existing.isPresent) {
            existing.get().copy(suite)
            suiteRepository.save(existing.get())
        } else {
            suiteRepository.save(suite)
        }
    }

    fun findSuite(id: String): Optional<Suite> {
        return suiteRepository!!.findById(id)
    }
}
