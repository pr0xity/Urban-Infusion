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
    void updateWithUser(long id, User user);

    /**
     * Updates the user with the given id with the user dto object given.
     *
     * @param id id of the user to update.
     * @param userDto the user dto to update to.
     */
    void updateWithUserDto(long id, UserDto userDto);

    void deleteUser(String email);

    /**
     * Returns the current user in session.
     *
     * @return current user in session.
     */
    User getSessionUser();
}

