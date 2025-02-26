package edu.citadel.api.controller;

import edu.citadel.service.EventBriteApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/eventbrite")
public class EventBriteController {

    private final EventBriteApiService eventBriteApiService;

    @Autowired
    public EventBriteController(EventBriteApiService eventBriteApiService) {
        this.eventBriteApiService = eventBriteApiService;
    }

    @GetMapping("/events")
    public ResponseEntity<String> getEventsByCity(@RequestParam String city) {
        String events = eventBriteApiService.getEventsByCity(city);
        return ResponseEntity.ok(events);
    }

    @GetMapping("/events/keyword")
    public ResponseEntity<String> getEventsByKeyword(@RequestParam String keyword) {
        String events = eventBriteApiService.getEventsByKeyword(keyword);
        return ResponseEntity.ok(events);
    }

    @GetMapping("/events/{id}")
    public ResponseEntity<String> getEventDetailsById(@PathVariable String id) {
        String eventDetails = eventBriteApiService.getEventDetailsById(id);
        return ResponseEntity.ok(eventDetails);
    }

    @GetMapping("/organizers/{organizerId}/events")
    public ResponseEntity<String> getEventsByOrganizer(@PathVariable String organizerId) {
        String events = eventBriteApiService.getEventsByOrganizer(organizerId);
        return ResponseEntity.ok(events);
    }

    @GetMapping("/venues/{venueId}/events")
    public ResponseEntity<String> getEventsByVenue(@PathVariable String venueId) {
        String events = eventBriteApiService.getEventsByVenue(venueId);
        return ResponseEntity.ok(events);
    }
}
