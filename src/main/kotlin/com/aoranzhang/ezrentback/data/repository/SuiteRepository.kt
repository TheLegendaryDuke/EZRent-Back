package com.aoranzhang.ezrentback.data.repository

import com.aoranzhang.ezrentback.data.entity.Suite
import org.springframework.data.repository.CrudRepository

interface SuiteRepository : CrudRepository<Suite, String>
