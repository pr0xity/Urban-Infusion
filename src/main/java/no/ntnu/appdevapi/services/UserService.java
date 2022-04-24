package no.ntnu.appdevapi.services;

import no.ntnu.appdevapi.DTO.UserDto;
import no.ntnu.appdevapi.entities.User;

import java.util.List;

public interface UserService {
    User save(UserDto user);

    List<User> findAll();

    User findOneByEmail(String email);

    User findOneByID(long id);

    void deleteUser(String email);

}

