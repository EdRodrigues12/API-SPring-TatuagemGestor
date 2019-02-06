package com.br.tattoo.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.br.tattoo.api.upload.FileStorageProperties;

@SpringBootApplication
@EnableConfigurationProperties({
    FileStorageProperties.class
})
public class TattooApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(TattooApiApplication.class, args);
	}
}
