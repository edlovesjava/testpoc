package com.vivid.partnerships.interview;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Date;

@SpringBootApplication
public class InterviewApplication implements CommandLineRunner {
	private static final Logger LOGGER = LoggerFactory.getLogger(InterviewApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(InterviewApplication.class, args);
	}

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Autowired
    VenueService venueService;

	@Autowired
    EventService eventService;

	@Override
	public void run(String... strings) throws Exception {
		LOGGER.info("Creating events and venues tables");

		jdbcTemplate.execute("CREATE TABLE events(" +
				"id SERIAL, name VARCHAR(255), venue_id INT, date DATETIME)");

        jdbcTemplate.execute("CREATE TABLE venues(" +
                "id SERIAL, name VARCHAR(255), city VARCHAR(255), state VARCHAR(255))");

		Venue venue = new Venue();
		venue.name = "Wrigley Field";
		venue.city = "Chicago";
		venue.state = "IL";

		venueService.addVenue(venue);

		Event event = new Event();
		event.name="Chicago White Sox vs. Chicago Cubs";
		event.date = new Date();
		event.venue = venue;

		eventService.addEvent(event);

		//jdbcTemplate.update("INSERT INTO events(name, venue_id, date) VALUES (?,?,?)", "Chicago White Sox vs. Chicago Cubs", 1,new Date());



		//  <td>Wrigley Field</td>
		//                        <td>Chicago</td>
		//                        <td>IL</td>
		//jdbcTemplate.update("INSERT INTO venues(id, name, city, state) VALUES (?,?,?, ?)", 1,"Wrigley Field", "Chicago","IL");
	}
}
