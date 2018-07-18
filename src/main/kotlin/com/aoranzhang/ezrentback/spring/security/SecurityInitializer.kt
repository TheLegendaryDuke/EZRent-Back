package com.aoranzhang.ezrentback.spring.security

import com.aoranzhang.ezrentback.spring.session.SessionConfig
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer

class SecurityInitializer : AbstractSecurityWebApplicationInitializer(SecurityConfig::class.java, SessionConfig::class.java)
