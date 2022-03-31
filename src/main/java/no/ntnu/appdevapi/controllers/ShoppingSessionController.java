package no.ntnu.appdevapi.controllers;

import no.ntnu.appdevapi.entities.ShoppingSession;
import no.ntnu.appdevapi.services.ShoppingSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST API controller for shopping sessions.
 */
@RestController
@RequestMapping("/cart")
public class ShoppingSessionController {

    @Autowired
    private ShoppingSessionService shoppingSessionService;

    /**
     * Get all shopping sessions.
     *
     * @return list of all shopping sessions.
     */
    @GetMapping
    public List<ShoppingSession> getAll() {
        return this.shoppingSessionService.getAllShoppingSessions();
    }

    /**
     * Retrieves the shopping session with the given id.
     *
     * @param id id of the shopping session to get.
     * @return shopping session with the given id and 200 OK, or 404 Not found on failure.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ShoppingSession> getOne(@PathVariable Integer id) {
        ResponseEntity<ShoppingSession> response;
        ShoppingSession shoppingSession = this.shoppingSessionService.getShoppingSession(id);
        if (shoppingSession != null) {
            response = new ResponseEntity<>(shoppingSession, HttpStatus.OK);
        } else {
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return response;
    }

    /**
     * Adds a shopping session to the store.
     *
     * @param shoppingSession the shopping session to be added.
     * @return 200 OK on success, 400 Bad request on failure.
     */
    @PostMapping
    public ResponseEntity<String> add(@RequestBody ShoppingSession shoppingSession) {
        ResponseEntity<String> response;
        if (shoppingSession != null) {
            this.shoppingSessionService.addShoppingSession(shoppingSession);
            response = new ResponseEntity<>(HttpStatus.OK);
        } else {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return response;
    }

    /**
     * Deletes the shopping session which has the given id.
     *
     * @param id the id of the shopping session to be deleted.
     * @return 200 OK on success, 404 Not found on failure.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable int id) {
        ResponseEntity<String> response;
        if (this.shoppingSessionService.getShoppingSession(id) != null) {
            this.shoppingSessionService.deleteShoppingSession(id);
            response = new ResponseEntity<>(HttpStatus.OK);
        } else {
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return response;
    }

    /**
     * Updates the shopping session which has the given id by the given shopping session.
     *
     * @param id the id of the shopping session to update.
     * @param shoppingSession the shopping session to update with.
     * @return 200 OK on success, 400 Bad request on failure.
     */
    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable int id, @RequestBody ShoppingSession shoppingSession) {
        ResponseEntity<String> response;
        if (shoppingSession != null && shoppingSession.getId() == id
                && this.shoppingSessionService.getShoppingSession(id) != null ) {
            this.shoppingSessionService.update(id, shoppingSession);
            response = new ResponseEntity<>(HttpStatus.OK);
        } else {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return response;
    }
}
