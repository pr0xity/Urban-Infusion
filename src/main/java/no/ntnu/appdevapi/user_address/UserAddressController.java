package no.ntnu.appdevapi.user_address;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@RestController
@RequestMapping("user_address")
public class UserAddressController {


    @Autowired
    private UserAddressService userAddressService;


    /**
     * Returns all user addresses
     *
     * @return List of all user addresses
     */
    @GetMapping
    @ApiOperation(value = "Get all user addresses.")
    public List<UserAddress> getAll() { return userAddressService.getAllAddresses();}

    /**
     * Get a specific address.
     *
     * @param id The ID of the address, starting from 0.
     * @return The address matching the id, or status 404.
     */
    @GetMapping("/{id}")
    @ApiOperation(value = "Get a specific address with a matching address id.", notes = "Returns the address or null when index is invalid.")
    public ResponseEntity<UserAddress> getAddressFromID(@ApiParam("id of the product.") @PathVariable int id) {
        ResponseEntity<UserAddress> response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        UserAddress address = userAddressService.getUserAddressByID(id);
        if (address != null) {
            response = new ResponseEntity<>(address, HttpStatus.OK);
        }
        return response;
    }


    /**
     * Add a users address to the database
     * @param userAddress the userAddress-object to add
     * @return 200 OK status on success, 400 Bad request on error
     */
    @PostMapping
    @ApiOperation(value = "Add a new address", notes = "An HTTP-response is returned")
    public ResponseEntity<UserAddress> add(@RequestBody UserAddress userAddress){

        ResponseEntity<UserAddress> response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        if (userAddress != null){
            userAddressService.addUserAddress(userAddress);
            response = new ResponseEntity<>(HttpStatus.OK);
        }
        return response;
    }


    /**
     * Delete an address from the list
     *
     * @param id the id of the address to delete.
     * @return 200 OK status on success, 404 not found on error
     */
    @DeleteMapping("/{id}")
    @ApiIgnore
    public ResponseEntity<String> delete(@PathVariable int id) {
        ResponseEntity<String> response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        if (userAddressService.getUserAddressByID(id) != null) {
            userAddressService.deleteUserAddressByID(id);
            response = new ResponseEntity<>(HttpStatus.OK);
        }
        return response;
    }
}
