package no.ntnu.appdevapi.services;

import no.ntnu.appdevapi.entities.UserAddress;

import java.util.List;

public interface UserAddressService {
  List<UserAddress> getAllAddresses();

  UserAddress getUserAddressByUserID(long id);

  boolean save(UserAddress userAddress);

  void deleteUserAddressByID(long id);
}
