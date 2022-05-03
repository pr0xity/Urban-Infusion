package no.ntnu.appdevapi.services;

import no.ntnu.appdevapi.entities.ShoppingSession;
import no.ntnu.appdevapi.DAO.ShoppingSessionRepository;
import no.ntnu.appdevapi.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Business logic for shopping sessions.
 */
@Service
public class ShoppingSessionServiceImpl implements ShoppingSessionService {

    @Autowired
    private ShoppingSessionRepository shoppingSessionRepository;

    @Override
    public List<ShoppingSession> getAllShoppingSessions() {
        List<ShoppingSession> shoppingSessions = new ArrayList<>();
        shoppingSessionRepository.findAll().forEach(shoppingSessions::add);
        return shoppingSessions;
    }

    @Override
    public ShoppingSession getShoppingSession(long id) {
        Optional<ShoppingSession> shoppingSession = shoppingSessionRepository.findById(id);
        return shoppingSession.orElse(null);
    }

    @Override
    public ShoppingSession getShoppingSessionByUser(User user) {
        return this.shoppingSessionRepository.findByUser(user).orElse(null);
    }

    @Override
    public void addShoppingSession(ShoppingSession shoppingSession) {
        shoppingSessionRepository.save(shoppingSession);
    }

    @Override
    public void update(long id, ShoppingSession shoppingSession) {
        if (shoppingSession != null && shoppingSession.getId() == id && getShoppingSession(id) != null) {
            this.shoppingSessionRepository.save(shoppingSession);
        }
    }

    @Override
    public void deleteShoppingSession(long id) {
        shoppingSessionRepository.deleteById(id);
    }
}
