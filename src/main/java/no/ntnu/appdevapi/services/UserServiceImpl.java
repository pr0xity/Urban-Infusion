package no.ntnu.appdevapi.services;

import no.ntnu.appdevapi.DAO.UserRepository;
import no.ntnu.appdevapi.DTO.UserDto;
import no.ntnu.appdevapi.entities.PermissionLevel;
import no.ntnu.appdevapi.entities.Product;
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
import java.util.*;
import java.util.stream.Collectors;

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

    /**
     * Gets a Set of all rights owned by the given user.
     * @param user the user to fetch the authorities of.
     * @return {@code Set<SimpleGrantedAuthority>} of authorities held by given user.
     */
    private Set<SimpleGrantedAuthority> getAuthority(User user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        user.getPermissionLevels().forEach(permissionLevel -> {
            authorities.add(new SimpleGrantedAuthority(permissionLevel.getAdminType()));
        });
        return authorities;
    }

    /**
     * Saves a user to the database from DTO.
     *
     * @param user the userDTO to save.
     * @return {@code User} that was saved.
     */
    @Override
    public User save(UserDto user) {
        User nUser = user.getUserFromDto();
        if (userRepository.findByEmail(nUser.getEmail()) == null) {
            nUser.setPassword(bcryptEncoder.encode(user.getPassword()));
            PermissionLevel permissionLevel = permissionLevelService.findByAdminType(user.getPermissionLevel());
            nUser.setCreatedAt(LocalDateTime.now());
            nUser.setPermissionLevel(permissionLevel);

            User savedUser = userRepository.save(nUser);
            UserAddress address = user.getAddressFromDto();
            if (null != address) {
                System.out.println("saving address: " + address.getAddressLine());
                savedUser.setAddress(address);
                address.setUser(savedUser);
                userAddressService.save(address);
            }

            System.out.println("saving user: " + nUser.getFirstName() + " " + nUser.getLastName());
            userRepository.save(savedUser);
        }
        return userRepository.findByEmail(nUser.getEmail());
    }

    /**
     * Saves a user to the database.
     *
     * @param user the user to save.
     * @return {@code User} that was saved.
     */
    @Override
    public User saveUserObject(User user) {
        return this.userRepository.save(user);
    }

    /**
     * Gets a list of all users, sorted by ID (ascending).
     *
     * @return sorted {@List<User>} of all users.
     */
    @Override
    public List<User> findAll() {
        List<User> list = new ArrayList<>();
        userRepository.findAll().iterator().forEachRemaining(list::add);
        return list.stream().sorted(Comparator.comparingLong(User::getId)).collect(Collectors.toList());
    }

    /**
     * Gets the user with given email.
     *
     * @param email email of the user.
     * @return {@code User} with given email, or null if no match.
     */
    @Override
    public User findOneByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    /**
     * Gets the user with the given ID.
     * @param id ID of the user.
     * @return {@code User} with given ID, or null if no match.
     */
    @Override
    public User findOneByID(long id) {
        return userRepository.findById(id);
    }

    /**
     * Updates the user with the given ID.
     *
     * @param id id of the user to update.
     * @param user the user object to update to.
     */
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

    /**
     * Updates the user with the given ID from DTO.
     *
     * @param id id of the user to update.
     * @param userDto the user dto to update to.
     */
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

    /**
     * Deletes the user with the given email.
     *
     * @param email email of the user to be deleted.
     */
    @Override
    public void deleteUser(String email) {
        userRepository.delete(userRepository.findByEmail(email));
    }

    /**
     * Disables the user with the given email.
     *
     * @param email email of the user to set as disabled.
     */
    @Override
    public void disableUser(String email) {
        User user = findOneByEmail(email);
        user.setEnabled(false);
        userRepository.save(user);
    }

    /**
     * Returns the user with the given email as a userDetails object.
     *
     * @param email the email of the user.
     * @return {@code UserDetails} of the user with the given email.
     * @throws UsernameNotFoundException if no user with given email exists.
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if(user == null) {
            throw new UsernameNotFoundException("No such user in database.");
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), getAuthority(user));
    }

    /**
     * Returns the currently authenticated user.
     * @return the {@code User} of the current session.
     */
    @Override
    public User getSessionUser() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        String email = authentication.getName();
        return userRepository.findByEmail(email);
    }
}
