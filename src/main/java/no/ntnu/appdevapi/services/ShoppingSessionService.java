package no.ntnu.appdevapi.services;

import no.ntnu.appdevapi.entities.ShoppingSession;

import java.util.List;

public interface ShoppingSessionService {
  List<ShoppingSession> getAllShoppingSessions();

  ShoppingSession getShoppingSession(long id);

  void addShoppingSession(ShoppingSession shoppingSession);

  void update(long id, ShoppingSession shoppingSession);

  void deleteShoppingSession(long id);
}
