package com.vivid.partnerships.interview;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.Nullable;

import java.sql.ResultSet;
import java.sql.SQLException;

public class VenueRowMapper implements RowMapper<Venue> {
    @Nullable
    @Override
    public Venue mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        final Venue venue = new Venue();
        venue.id = resultSet.getInt("id");
        venue.name = resultSet.getString("name");
        venue.state = resultSet.getString("state");
        venue.city = resultSet.getString("city");
        return venue;
    }
}
