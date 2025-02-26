package edu.citadel.api; 

import edu.citadel.api.controller.TicketMasterController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/ticket-team")
public class ApplicationEndpoints {

   private final TicketMasterController ticketMasterController;

    @Autowired
    public ApplicationEndpoints(TicketMasterController ticketMasterController) {
        this.ticketMasterController = ticketMasterController;
    }

    @GetMapping("/events/{id}")
    public ResponseEntity<String> getEventByID(@PathVariable String id) {
        return ticketMasterController.getEventDetailsById(id);
    }

    @GetMapping("/performers/{performer}")
    public ResponseEntity<String> getEventsByPerformer(@PathVariable String performer) {
        return ticketMasterController.getEventsByPerformer(performer);
    }
    @GetMapping("/category/{category}")
    public ResponseEntity<String> getEventsByCategory(@PathVariable String category) {
        return ticketMasterController.getEventsByCategory(category);
    }
    @GetMapping("/venues/{venue}")
    public ResponseEntity<String> getEventsByVenue(@PathVariable String venue) {
        return ticketMasterController.getEventsByVenue(venue);
    }
    @GetMapping("/events/featured")
    public ResponseEntity<String> getFeaturedEvents() {
        return ticketMasterController.getFeaturedEvents();
    }
    @GetMapping("/categories/popular")
    public ResponseEntity<String> getPopularCategories() {
        return ticketMasterController.getPopularCategories();
    }
}