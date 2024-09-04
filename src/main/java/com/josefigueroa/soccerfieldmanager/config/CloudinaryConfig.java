package com.josefigueroa.soccerfieldmanager.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

@Configuration
public class CloudinaryConfig {
    
    @Bean
    Cloudinary cloudinary (){
        return new Cloudinary(ObjectUtils.asMap(
            "cloud_name", "dcczhqfbn",
            "api_key", "373621872176658", 
            "api_secret", "CpVtdi_F2DmX8ZtzclVS8F9n7aA"
        ));
    }
}
