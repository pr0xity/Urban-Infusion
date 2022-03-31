package no.ntnu.appdevapi.services;

import no.ntnu.appdevapi.entities.UserAddress;
import no.ntnu.appdevapi.DAO.UserAddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserAddressService {


    @Autowired
    private UserAddressRepository userAddressRepository;

    public List<UserAddress> getAllAddresses(){
        List<UserAddress> addressList = new ArrayList<>();
        userAddressRepository.findAll().forEach(addressList::add);
        return addressList;
    }

    public UserAddress getUserAddressByID(int id){
        Optional<UserAddress> address = userAddressRepository.findById(id);
        return address.orElse(null);
    }

    public void addUserAddress(UserAddress userAddress) {userAddressRepository.save(userAddress);}

    public void deleteUserAddressByID(int id) {userAddressRepository.deleteById(id);}


}
