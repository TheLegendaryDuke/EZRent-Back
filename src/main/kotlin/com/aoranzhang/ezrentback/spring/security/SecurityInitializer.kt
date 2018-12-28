package com.aoranzhang.ezrentback.spring.security

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer

class SecurityInitializer : AbstractSecurityWebApplicationInitializer(SecurityConfig::class.java)
