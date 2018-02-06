package pt.ipl.isel.gallows_game_bot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GallowsBotApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(GallowsBotApplication.class);



    public static void main(String[] args) {
        LOGGER.info("::STARTING::");

        SpringApplication.run(GallowsBotApplication.class, args);

        LOGGER.info("::RUNNING::");
    }

}

