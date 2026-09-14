package com.hiroinaprotagonist.songsaurus;

import com.hiroinaprotagonist.songsaurus.rest.controller.SpController;
import com.hiroinaprotagonist.songsaurus.rest.model.web.request.Cloud;
import com.hiroinaprotagonist.songsaurus.rest.model.web.request.CloudWord;
import com.hiroinaprotagonist.songsaurus.rest.model.web.response.Tracks;
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
		String[] sampleCloud = {"rainy", "cloudy"};
//				new Cloud((new CloudWord[]{
//				new CloudWord("rainy", 500),
//				new CloudWord("cloudy", 700),
//				new CloudWord("dry", 1200)}));
//		ResponseEntity<List<Tracks>> res = spController.getSongsByWords("5", sampleCloud);
//		log.info("Res from controller: {}", res);

//		spController.getSongsByWord("5", sampleCloud[0]);
	}
}
