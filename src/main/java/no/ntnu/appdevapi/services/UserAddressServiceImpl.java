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

    /**
     * Gets a list of all user addresses in the database.
     *
     * @return {@code List<UserAddress>} of all user addresses.
     */
    public List<UserAddress> getAllAddresses(){
        List<UserAddress> addressList = new ArrayList<>();
        userAddressRepository.findAll().forEach(addressList::add);
        return addressList;
    }

    /**
     * Gets the user address with the given ID.
     *
     * @param id ID of the user address to look for.
     * @return {@code UserAddress} matching the given ID, or {@code null} if no match.
     */
    public UserAddress getUserAddressByUserID(long id){
        UserAddress returnAddress = null;
        for (UserAddress address : userAddressRepository.findAll()) {
            if (address.getUser().getId() == id) {
                returnAddress = address;
            }
        }
        return returnAddress;
    }

    /**
     * Saves a user address to the database.
     *
     * @param userAddress the user address to save.
     * @return {@code UserAddress} that was saved.
     */
    public boolean save(UserAddress userAddress) {
        if (null == userAddressRepository.findFirstByAddressLine1(userAddress.getAddressLine1())) {
            userAddressRepository.save(userAddress);
            return true;
        }
        return false;
    }

    /**
     * Deletes the user address with the given ID.
     *
     * @param id ID of the user address to be deleted.
     */
    public void deleteUserAddressByID(long id) {
        userAddressRepository.deleteById(id);
    }
}
