package com.aoranzhang.ezrentback

//import com.cloudinary.Cloudinary
//import com.cloudinary.utils.ObjectUtils
import com.google.maps.GeoApiContext
//import com.pubnub.api.PNConfiguration
//import com.pubnub.api.PubNub
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Profile
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.session.data.redis.config.ConfigureRedisAction
import org.springframework.util.ObjectUtils

@SpringBootApplication
class EzrentBackApplication {

    @Value(value = "\${google.api-key}")
    lateinit var googleApiKey: String

    @Bean
    fun passwordEncoder(): BCryptPasswordEncoder {
        return BCryptPasswordEncoder()
    }

//    @Bean
//    fun pubNub(): PubNub {
//        val pnConfiguration = PNConfiguration()
//        pnConfiguration.setSubscribeKey("sub-c-0be896f4-a54c-11e7-95db-52f8a99aa42b")
//        pnConfiguration.setPublishKey("pub-c-d7c400d4-0640-4972-9661-dbfa1b9de87f")
//
//        return PubNub(pnConfiguration)
//    }
//
//    @Bean
//    fun cloudinary(): Cloudinary {
//        return Cloudinary(ObjectUtils.asMap(
//                "cloud_name", "hn6a1oemf",
//                "api_key", "493371279163146",
//                "api_secret", "yKsT_dWH2ODkawbue8I5UHlOIi4"))
//    }

    @Bean
    fun geoApiContext(): GeoApiContext {
        return GeoApiContext.Builder()
                .apiKey(googleApiKey)
                .build()
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            SpringApplication.run(EzrentBackApplication::class.java, *args)
        }

        @Bean
        fun configureRedisAction(): ConfigureRedisAction {
            return ConfigureRedisAction.NO_OP
        }
    }
}
