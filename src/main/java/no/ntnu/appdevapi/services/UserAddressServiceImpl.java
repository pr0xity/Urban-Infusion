package no.ntnu.appdevapi.services;

import no.ntnu.appdevapi.entities.UserAddress;
import no.ntnu.appdevapi.DAO.UserAddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserAddressServiceImpl implements UserAddressService {


    @Autowired
    private UserAddressRepository userAddressRepository;

    public List<UserAddress> getAllAddresses(){
        List<UserAddress> addressList = new ArrayList<>();
        userAddressRepository.findAll().forEach(addressList::add);
        return addressList;
    }

    public UserAddress getUserAddressByUserID(long id){
        UserAddress returnAddress = null;
        for (UserAddress address : userAddressRepository.findAll()) {
            if (address.getUser().getId() == id) {
                returnAddress = address;
            }
        }
        return returnAddress;
    }

    public boolean save(UserAddress userAddress) {
        if (null == userAddressRepository.findFirstByAddressLine1(userAddress.getAddressLine1())) {
            userAddressRepository.save(userAddress);
            return true;
        }
        return false;
    }

    public void deleteUserAddressByID(long id) {
        userAddressRepository.deleteById(id);
    }
}
