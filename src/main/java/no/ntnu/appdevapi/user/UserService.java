package no.ntnu.appdevapi.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        userRepository.findAll().forEach(users::add);
        return users;
    }

    public User getUser(int index) {
        Optional<User> user = userRepository.findById(index);
        return user.orElse(null);
    }

    public void addUser(User user) {
        userRepository.save(user);
    }

    public void deleteUser(int index) {
        userRepository.deleteById(index);
    }
}
