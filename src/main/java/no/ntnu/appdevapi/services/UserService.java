package no.ntnu.appdevapi.services;

import no.ntnu.appdevapi.DTO.UserDto;
import no.ntnu.appdevapi.entities.User;

import java.util.List;

public interface UserService {

    User save(UserDto user);

    List<User> findAll();

    User findOneByEmail(String email);

    User findOneByID(long id);

    /**
     * Updates the user with the given id with the user object given.
     *
     * @param id id of the user to update.
     * @param user the user object to update to.
     */
    void update(long id, User user);

    void deleteUser(String email);

    User getSessionUser();
}

