package no.ntnu.appdevapi.services;

import no.ntnu.appdevapi.entities.UserAddress;

import java.util.List;

public interface UserAddressService {
  public List<UserAddress> getAllAddresses();

  public UserAddress getUserAddressByUserID(long id);

  public boolean save(UserAddress userAddress);

  public void deleteUserAddressByID(long id);
}
