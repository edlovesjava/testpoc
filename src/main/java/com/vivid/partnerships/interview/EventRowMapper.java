package com.vivid.partnerships.interview;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.Nullable;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EventRowMapper implements RowMapper<Event> {
    @Nullable
    @Override
    public Event mapRow(ResultSet resultSet, int rowNum) throws SQLException {

        final Event event = new Event();
        event.id = resultSet.getInt("events.id");
        event.date = resultSet.getTimestamp("events.date");
        event.name = resultSet.getString("events.name");

        Venue venue = new Venue();
        venue.id = resultSet.getInt("events.venue_id");
        venue.name = resultSet.getString("venues.name");
        venue.state = resultSet.getString("venues.state");
        venue.city = resultSet.getString("venues.city");
        event.venue = venue;
        return event;
    }
}
