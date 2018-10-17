package com.vivid.partnerships.interview;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Service
public class VenueService {
    private static final Logger LOGGER = LoggerFactory.getLogger(VenueService.class);

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public VenueService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

     public List<Venue> getVenues() {
         return jdbcTemplate.query("SELECT id, name, city, state FROM venues", new VenueRowMapper());
    }
    public List<Venue> findVenueByNameCityState(String name, String city, String state) {
         return jdbcTemplate.query("SELECT id, name, city, state FROM venues WHERE name = ? and city = ? and state = ?", new Object[] {name, city, state}, new VenueRowMapper());
    }

    public Venue addVenue(Venue venue) {

        if (venue.id == null) {
            KeyHolder keyHolder = new GeneratedKeyHolder();

            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection
                        .prepareStatement("INSERT INTO venues( name, city, state) VALUES (?,?, ?)", Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, venue.name);
                ps.setString(2, venue.city);
                ps.setString(3, venue.state);
                return ps;
            }, keyHolder );
            venue.id = (int)keyHolder.getKey();
            LOGGER.info(String.format("venue.id = %d, venue.name=%s", venue.id,venue.name));
        }
        return venue;
    }
}
