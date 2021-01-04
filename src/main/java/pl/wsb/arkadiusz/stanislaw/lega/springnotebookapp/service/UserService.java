package pl.wsb.arkadiusz.stanislaw.lega.springnotebookapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.wsb.arkadiusz.stanislaw.lega.springnotebookapp.model.Role;
import pl.wsb.arkadiusz.stanislaw.lega.springnotebookapp.model.User;
import pl.wsb.arkadiusz.stanislaw.lega.springnotebookapp.repository.RoleRepository;
import pl.wsb.arkadiusz.stanislaw.lega.springnotebookapp.repository.UserRepository;
import pl.wsb.arkadiusz.stanislaw.lega.springnotebookapp.statics.Setup;

import java.util.Collections;
import java.util.HashSet;

@Service
public class UserService {

    private final UserRepository USER_REPOSITORY;
    private final RoleRepository ROLE_REPOSITORY;
    private final BCryptPasswordEncoder B_CRYPT_PASSWORD_ENCODER;

    @Autowired
    public UserService(UserRepository userRepository,
                       RoleRepository roleRepository,
                       BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.USER_REPOSITORY = userRepository;
        this.ROLE_REPOSITORY = roleRepository;
        this.B_CRYPT_PASSWORD_ENCODER = bCryptPasswordEncoder;
    }

    public User findUserByEmail(String email) {
        return this.USER_REPOSITORY.findByEmail(email);
    }

    public User findUserByUserName(String userName) {
        return this.USER_REPOSITORY.findByUserName(userName);
    }


    public User saveUser(User user) {
        user.setPassword(this.B_CRYPT_PASSWORD_ENCODER.encode(user.getPassword()));
        user.setActive(true);
        Role userRole = this.ROLE_REPOSITORY.findByRole(Setup.ROLE_USER);
        user.setRoles(new HashSet<>(Collections.singletonList(userRole)));
        return this.USER_REPOSITORY.save(user);
    }
}

