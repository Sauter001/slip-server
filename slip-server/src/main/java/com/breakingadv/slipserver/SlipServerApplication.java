package com.breakingadv.slipserver;

import com.breakingadv.slipserver.service.CCTVStreamingService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SlipServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SlipServerApplication.class, args);
	}

}
