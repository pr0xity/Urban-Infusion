package no.ntnu.appdevapi.services;

import no.ntnu.appdevapi.DAO.UserRepository;
import no.ntnu.appdevapi.DTO.UserDto;
import no.ntnu.appdevapi.entities.PermissionLevel;
import no.ntnu.appdevapi.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service(value = "userService")
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    private PermissionLevelService permissionLevelService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bcryptEncoder;

    private Set<SimpleGrantedAuthority> getAuthority(User user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        user.getPermissionLevel().forEach(permissionLevel -> {
            authorities.add(new SimpleGrantedAuthority(permissionLevel.getAdminType()));
        });
        return authorities;
    }

    @Override
    public User save(UserDto user) {
        User nUser = user.getUserFromDto();
        if (userRepository.findByEmail(nUser.getEmail()) == null) {
            nUser.setPassword(bcryptEncoder.encode(user.getPassword()));
            PermissionLevel permissionLevel = permissionLevelService.findByAdminType(user.getPermissionLevel());
            nUser.setCreatedAt(LocalDateTime.now());
            nUser.setPermissionLevel(permissionLevel);
            nUser.setEnabled(true);
            return userRepository.save(nUser);
        }
        return null;
    }

    @Override
    public List<User> findAll() {
        List<User> list = new ArrayList<>();
        userRepository.findAll().iterator().forEachRemaining(list::add);
        return list;
    }

    @Override
    public User findOne(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User findOneByID(long id) {
        return userRepository.findById(id);
    }

    @Override
    public void deleteUser(String email) {
        userRepository.delete(userRepository.findByEmail(email));
    }

    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if(user == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), getAuthority(user));
    }

}
