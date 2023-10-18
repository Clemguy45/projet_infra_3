package com.example.projet_infra_3_backend.service;

import com.example.projet_infra_3_backend.exception.*;
import com.example.projet_infra_3_backend.modele.User;
import com.example.projet_infra_3_backend.repository.UserRepository;
import org.slf4j.LoggerFactory;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import static com.example.projet_infra_3_backend.config.Role.ROLE_USER;
import static com.example.projet_infra_3_backend.constant.FilesConstant.DEFAULT_USER_IMAGE_PATH;
import static com.example.projet_infra_3_backend.constant.UserImplConstant.*;
import static org.apache.commons.lang3.StringUtils.EMPTY;

@Service
@Transactional
public class UserServiceImpl implements UserDetailsService,UserService{

    private Logger LOGGER = LoggerFactory.getLogger(getClass());

    private UserRepository userRepository;

    private BCryptPasswordEncoder passwordEncoder;

    private LoginAttemptService loginAttemptService;

    private EmailService emailService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder, LoginAttemptService loginAttemptService, EmailService emailService){
        this.emailService = emailService;
        this.loginAttemptService = loginAttemptService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User register(String firstName, String lastName, String username, String email) throws UserNotFoundException, EmailExistException, UsernameExistException {
        User user = add(firstName,lastName,username,email);
        String password = generatePassword();
        user.setPassword(encodePassword(password));
        user.setNotLocked(true);
        user.setRole(ROLE_USER.name());
        user.setAuthorities(ROLE_USER.getAuthorities());
        user.setProfileImageUrl(getTemporaryProfileImageUrl(username));
        userRepository.save(user);
        emailService.sendEmail(username, password, email);

        return user;
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public User findUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    @Override
    public User registerNoGeneratedPassword(String firstName, String password, String lastName, String username, String email) throws UserNotFoundException, EmailExistException, UsernameExistException {
        return null;
    }

    @Override
    public User addNewUser(String firstName, String lastName, String username, String email, String role, boolean isNonLocked, boolean isActive, MultipartFile profileImage) throws UserNotFoundException, EmailExistException, UsernameExistException, IOException, NotAnImageFileException {
        return null;
    }

    @Override
    public User updateUser(String currentUsername, String newFirstname, String newLastName, String newUsername, String newEemail, String role, boolean isNonLocked, boolean isActive, MultipartFile profileImage) throws UserNotFoundException, EmailExistException, UsernameExistException, IOException, NotAnImageFileException {
        return null;
    }

    @Override
    public void deleteUser(String username) throws UserNotFoundException, IOException {

    }

    @Override
    public void resetPassword(String email) throws EmailNotFoundException {

    }

    @Override
    public User updateProfileImage(String username, MultipartFile profileImage) throws UserNotFoundException, EmailExistException, UsernameExistException, IOException, NotAnImageFileException {
        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }

    public User add(String firstName, String lastName, String username, String email) throws UserNotFoundException, EmailExistException, UsernameExistException {
        validateNewUsernameAndEmail(EMPTY, username, email);
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setUsername(username);
        user.setEmail(email);
        user.setJoinDate(new Date());

        return user;
    }

    private User validateNewUsernameAndEmail(String currentUsername, String newUsername, String newEmail) throws UserNotFoundException, UsernameExistException, EmailExistException {
        // on update ou cree un new ?
        User userByNewUsername = findUserByUsername(newUsername);
        User userByNewEmail = findUserByEmail(newEmail);
        if (StringUtils.isNotBlank(currentUsername)) {
            User currentUser = findUserByUsername(currentUsername);
            if (currentUser == null)
                throw new UserNotFoundException(NO_USER_FOUND_BY_USERNAME + currentUsername);

            if (userByNewUsername != null && !currentUser.getId().equals(userByNewUsername.getId()))
                throw new UsernameExistException(USERNAME_ALREADY_EXISTS);

            if (userByNewEmail != null && !currentUser.getId().equals(userByNewEmail.getId()))
                throw new EmailExistException(EMAIL_ALREADY_EXISTS);
            return currentUser;
        } else {
            if (userByNewUsername != null)
                throw new UsernameExistException(USERNAME_ALREADY_EXISTS);
            if (userByNewEmail != null)
                throw new EmailExistException(EMAIL_ALREADY_EXISTS);
        }
        return null;
    }


    private String generatePassword() {
        return RandomStringUtils.randomAlphanumeric(10);
    }

    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }
    private String getTemporaryProfileImageUrl(String username) {
        return ServletUriComponentsBuilder.fromCurrentContextPath().path(DEFAULT_USER_IMAGE_PATH + username).toUriString();
    }
}
