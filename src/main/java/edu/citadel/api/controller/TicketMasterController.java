package edu.citadel.api.controller;

import edu.citadel.service.TicketMasterApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ticketmaster")
public class TicketMasterController {

    private final TicketMasterApiService ticketMasterApiService;

    @Autowired
    public TicketMasterController(TicketMasterApiService ticketMasterApiService) {
        this.ticketMasterApiService = ticketMasterApiService;
    }

    @GetMapping("/events")
    public ResponseEntity<String> getEventsByCity(@RequestParam String city) {
        String events = ticketMasterApiService.getEventsByCity(city);
        return ResponseEntity.ok(events);
    }

    @GetMapping("/events/{id}")
    public ResponseEntity<String> getEventDetailsById(@PathVariable String id) {
        String eventDetails = ticketMasterApiService.getEventDetailsById(id);
        return ResponseEntity.ok(eventDetails);
    }

    @GetMapping("/performers/{performer}")
    public ResponseEntity<String> getEventsByPerformer(@PathVariable String performer) {
        String events = ticketMasterApiService.getEventsByPerformer(performer);
        return ResponseEntity.ok(events);
    }

    @GetMapping("/categories/{category}")
    public ResponseEntity<String> getEventsByCategory(@PathVariable String category) {
        String popularCategories = ticketMasterApiService.getEventsByCategory(category);
        return ResponseEntity.ok(popularCategories);
    }

    @GetMapping("/venues/{venue}")
    public ResponseEntity<String> getEventsByVenue(@PathVariable String venue) {
        String events = ticketMasterApiService.getEventsByVenue(venue);
        return ResponseEntity.ok(events);
    }

    @GetMapping("/events/featured")
    public ResponseEntity<String> getFeaturedEvents() {
        String featuredEvents = ticketMasterApiService.getFeaturedEvents();
        return ResponseEntity.ok(featuredEvents);
    }

    @GetMapping("/categories/popular")
    public ResponseEntity<String> getPopularCategories() {
        String popularCategories = ticketMasterApiService.getPopularCategories();
        return ResponseEntity.ok(popularCategories);
    }
}