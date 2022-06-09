package no.ntnu.appdevapi.services;

import java.util.List;
import no.ntnu.appdevapi.entities.UserAddress;

public interface UserAddressService {
  List<UserAddress> getAllAddresses();

  UserAddress getUserAddressByUserID(long id);

  boolean save(UserAddress userAddress);

  void deleteUserAddressByID(long id);
}
