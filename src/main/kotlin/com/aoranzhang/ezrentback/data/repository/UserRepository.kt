package com.aoranzhang.ezrentback.data.repository

import com.aoranzhang.ezrentback.data.entity.User
import org.springframework.data.repository.CrudRepository


// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

interface UserRepository : CrudRepository<User, String> {
    fun findByEmail(email: String): User
}
