package com.srm.bcScraper;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.srm.bcScraper.webScraper.ExtractJson;

@SpringBootApplication
public class BencinasChileScraperApplication {
	
	private static final Logger log = LogManager.getLogger(BencinasChileScraperApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(BencinasChileScraperApplication.class, args);
		
		ExtractJson extractor = new ExtractJson();
		try {
			extractor.executeExtract();
			
		} catch (Exception e) {
			log.error(e);
		}
		
		
	}

}