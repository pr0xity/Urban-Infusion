package no.ntnu.appdevapi.services;

import no.ntnu.appdevapi.DTO.UserDto;
import no.ntnu.appdevapi.entities.User;
import no.ntnu.appdevapi.DAO.UserRepository;

import java.util.List;

public interface UserService {
    User save(UserDto user);

    List<User> findAll();

    User findOne(String email);

    void deleteUser(String email);

}

