package no.ntnu.appdevapi.services;

import no.ntnu.appdevapi.DAO.UserRepository;
import no.ntnu.appdevapi.DTO.UserDto;
import no.ntnu.appdevapi.entities.PermissionLevel;
import no.ntnu.appdevapi.entities.User;
import no.ntnu.appdevapi.entities.UserAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private UserAddressService userAddressService;

    @Autowired
    private BCryptPasswordEncoder bcryptEncoder;

    private Set<SimpleGrantedAuthority> getAuthority(User user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        user.getPermissionLevels().forEach(permissionLevel -> {
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

            UserAddress address = user.getAddressFromDto();
            if (null != address) {
                System.out.println("saving address: " + address.getAddressLine());
                userAddressService.save(address);
                nUser.setAddress(address);
            }

            System.out.println("saving user: " + nUser.getFirstName() + " " + nUser.getLastName());
            userRepository.save(nUser);
        }
        return userRepository.findByEmail(nUser.getEmail());
    }

    @Override
    public User saveUserObject(User user) {
        return this.userRepository.save(user);
    }

    @Override
    public List<User> findAll() {
        List<User> list = new ArrayList<>();
        userRepository.findAll().iterator().forEachRemaining(list::add);
        return list;
    }

    @Override
    public User findOneByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User findOneByID(long id) {
        return userRepository.findById(id);
    }

    @Override
    public void updateWithUser(long id, User user) {
        User existingUser = findOneByID(id);
        if (user != null && user.getId() == id && existingUser != null) {
            if (!user.getPassword().equals(existingUser.getPassword()) && !bcryptEncoder.matches(user.getPassword(), existingUser.getPassword())) {
                user.setPassword(bcryptEncoder.encode(user.getPassword()));
                this.userRepository.save(user);
                return;
            }
            this.userRepository.save(user);
        }
    }

    @Override
    public void updateWithUserDto(long id, UserDto userDto) {
        User updatedUser = userDto.getUserFromDto();
        User user = userRepository.findById(id);

        if (updatedUser != null && user != null) {
            if (updatedUser.getFirstName() != null) {
                user.setFirstName(updatedUser.getFirstName());
            }
            if (updatedUser.getLastName() != null) {
                user.setLastName(updatedUser.getLastName());
            }
            if (updatedUser.getEmail() != null) {
                user.setEmail(updatedUser.getEmail());
            }
            if (userDto.getNewPassword() != null) {
                user.setPassword(bcryptEncoder.encode(userDto.getNewPassword()));
            }

            user.setUpdatedAt(LocalDateTime.now());

            UserAddress address = userDto.getAddressFromDto();
            if (address != null && user.getAddress() != null) {
                UserAddress existingAddress = user.getAddress();
                existingAddress.setAddressLine1(address.getAddressLine1());
                existingAddress.setAddressLine2(address.getAddressLine2());
                existingAddress.setCity(address.getCity());
                existingAddress.setPostalCode(address.getPostalCode());
                existingAddress.setCountry(address.getCountry());
                userAddressService.save(existingAddress);
            }
            if (address != null) {
                System.out.println("saving address: " + address.getAddressLine());
                address.setUser(user);
                user.setAddress(address);
                userAddressService.save(address);
            }
            userRepository.save(user);
            System.out.println("updating user: " + user.getFirstName() + " " + user.getLastName());
        }
    }

    @Override
    public void deleteUser(String email) {
        userRepository.delete(userRepository.findByEmail(email));
    }

    @Override
    public void disableUser(String email) {
        User user = findOneByEmail(email);
        user.setEnabled(false);
        userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if(user == null) {
            throw new UsernameNotFoundException("No such user in database.");
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), getAuthority(user));
    }

    @Override
    public User getSessionUser() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        String email = authentication.getName();
        return userRepository.findByEmail(email);
    }

}
