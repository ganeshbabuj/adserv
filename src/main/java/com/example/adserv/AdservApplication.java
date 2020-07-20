package com.example.adserv;

import com.example.adserv.model.AdDocument;
import com.example.adserv.model.AdType;
import com.example.adserv.repository.AdRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AdservApplication implements CommandLineRunner {

	@Autowired
	private AdRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(AdservApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}


	@Override
	public void run(String... args) throws Exception {

		repository.deleteAll();

		AdDocument adDocument1 = new AdDocument();
		adDocument1.setUsername("ganesh");

		adDocument1.setAdName("AD 1");
		adDocument1.setDescription("TESTAD 1");
		adDocument1.setAdType(AdType.BANNER);
		adDocument1.setImageUrl("https://dummyimage.com/300");
		adDocument1.setTargetUrl("https://example.com/1");

		repository.save(adDocument1);


		AdDocument adDocument2 = new AdDocument();
		adDocument2.setUsername("ganesh");

		adDocument2.setAdName("AD 2");
		adDocument2.setDescription("TESTAD 2");
		adDocument2.setAdType(AdType.BANNER);
		adDocument2.setImageUrl("https://dummyimage.com/350");
		adDocument2.setTargetUrl("https://example.com/2");

		repository.save(adDocument2);


		AdDocument adDocument3 = new AdDocument();
		adDocument3.setUsername("user1");

		adDocument3.setAdName("AD 3");
		adDocument3.setDescription("TESTAD 3");
		adDocument3.setAdType(AdType.BANNER);
		adDocument3.setImageUrl("https://dummyimage.com/400");
		adDocument3.setTargetUrl("https://example.com/3");

		repository.save(adDocument3);


		// fetch all Ads
		System.out.println("Ads found with findAll():");
		System.out.println("-------------------------------");
		for (AdDocument adDocument : repository.findAll()) {
			System.out.println(adDocument);
		}
		System.out.println();

		// fetch Ads by username
		System.out.println("Ads found with findByUsername('ganesh'):");
		System.out.println("--------------------------------");
		for (AdDocument adDocument : repository.findByUsername("ganesh")) {
			System.out.println(adDocument);
		}

	}

}

