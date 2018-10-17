package com.vivid.partnerships.interview;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Types;
import java.util.Date;
import java.util.List;

@Service
public class EventService {
    @Autowired
    protected NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public EventService() {

    }

     public List<Event> getEvents() {
         return jdbcTemplate.query("SELECT events.id, events.name, events.date, events.venue_id, venues.name, venues.city, venues.state FROM events JOIN venues ON events.venue_id = venues.id", new EventRowMapper());
    }

    public Event addEvent(Event event) {
        if (event.id == null) {

            KeyHolder keyHolder = new GeneratedKeyHolder();


            jdbcTemplate.update("INSERT INTO events(name, venue_id, date) VALUES (:name,:venueId,:date)",
                    new MapSqlParameterSource()
                            .addValue("name", event.name)
                            .addValue("venueId", event.venue.id)
                            .addValue("date",  new java.sql.Date(event.date.getTime()),Types.TIMESTAMP)
                    , keyHolder);

            event.id = (Integer)keyHolder.getKey();

        }

        return event;
    }

}
