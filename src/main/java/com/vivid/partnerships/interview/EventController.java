package com.vivid.partnerships.interview;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

@RestController
public class EventController {
    private static final Logger LOGGER = LoggerFactory.getLogger(EventController.class);

    private final EventService eventService;
    private final VenueService venueService;

    @Autowired
    public EventController(
            final EventService eventService,
            final VenueService venueService
    ) {
        this.eventService = eventService;
        this.venueService = venueService;
    }


    @GetMapping("/events")
    public List<Event> getEvents() {
        List<Event> events = eventService.getEvents();
        LOGGER.info("Returning {} events", events.size());
        return events;
    }


    @PostMapping("/events")
    public Event addEvent(
            @RequestParam  String eventJson

     ) throws IOException {


        LOGGER.info(String.format("eventjson %s", eventJson));

        ObjectMapper om = new ObjectMapper();
        Event eventToAdd = om.readValue(eventJson, Event.class);

        if (eventToAdd.date == null) {
            eventToAdd.date = new Date();
        }
        Venue venueToAdd = eventToAdd.venue;

        List<Venue> venues = venueService.findVenueByNameCityState(venueToAdd.name, venueToAdd.city, venueToAdd.state);
        if (CollectionUtils.isEmpty(venues)) {

            venueToAdd = venueService.addVenue(venueToAdd);

        } else {

            venueToAdd = venues.get(0);

        }


        Event event = eventService.addEvent(eventToAdd);
        LOGGER.info("Adding  event id {}  ", event.id);

        return event;
    }


}
