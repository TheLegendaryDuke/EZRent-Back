package com.aoranzhang.ezrentback.spring.security

import org.springframework.http.HttpStatus

class SecurityException(override val message : String, val httpStatus: HttpStatus) : Exception(message) {
}