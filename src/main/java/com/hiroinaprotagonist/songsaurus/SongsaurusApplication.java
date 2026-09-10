package com.hiroinaprotagonist.songsaurus;

import com.hiroinaprotagonist.songsaurus.rest.controller.SpController;
import com.hiroinaprotagonist.songsaurus.rest.model.web.response.TracksDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;

import java.util.List;

@SpringBootApplication
@Slf4j
public class SongsaurusApplication implements CommandLineRunner {
	private final SpController spController;

	public static void main(String[] args) {
		SpringApplication.run(SongsaurusApplication.class, args);
	}

	@Autowired
	public SongsaurusApplication(SpController spController) {
		this.spController = spController;
	}

//	@Override
	public void run(String... args) throws Exception {
		String[] sampleCloud = {"rainy", "cloudy", "dry"};
		ResponseEntity<List<TracksDTO>> res = spController.getSongsByWords("5", sampleCloud);
		log.info("Res from controller: {}", res);

//		spController.getSongsByWord("5", sampleCloud[0]);
	}
}
