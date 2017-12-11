package com.aoranzhang.ezrentback;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.google.maps.GeoApiContext;
import com.pubnub.api.PNConfiguration;
import com.pubnub.api.PubNub;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class EzrentBackApplication {
	public static void main(String[] args) {
		SpringApplication.run(EzrentBackApplication.class, args);
	}

    @Value("${google.api-key}")
    private String googleApiKey;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder;
    }

    @Bean
    public PubNub pubNub() {
        PNConfiguration pnConfiguration = new PNConfiguration();
        pnConfiguration.setSubscribeKey("sub-c-0be896f4-a54c-11e7-95db-52f8a99aa42b");
        pnConfiguration.setPublishKey("pub-c-d7c400d4-0640-4972-9661-dbfa1b9de87f");

        PubNub pubnub = new PubNub(pnConfiguration);

        return pubnub;
    }

    @Bean
    public Cloudinary cloudinary() {
        Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "hn6a1oemf",
                "api_key", "493371279163146",
                "api_secret", "yKsT_dWH2ODkawbue8I5UHlOIi4"));
        return cloudinary;
    }

    @Bean
    public GeoApiContext geoApiContext() {
        GeoApiContext context = new GeoApiContext.Builder()
                .apiKey(googleApiKey)
                .build();
        return context;
    }
}
