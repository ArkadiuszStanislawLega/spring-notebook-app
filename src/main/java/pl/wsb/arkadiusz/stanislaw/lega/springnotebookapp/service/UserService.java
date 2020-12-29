package pl.wsb.arkadiusz.stanislaw.lega.springnotebookapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.wsb.arkadiusz.stanislaw.lega.springnotebookapp.model.Role;
import pl.wsb.arkadiusz.stanislaw.lega.springnotebookapp.model.User;
import pl.wsb.arkadiusz.stanislaw.lega.springnotebookapp.repository.RoleRepository;
import pl.wsb.arkadiusz.stanislaw.lega.springnotebookapp.repository.UserRepository;
import pl.wsb.arkadiusz.stanislaw.lega.springnotebookapp.statics.Roles;

import java.util.Arrays;
import java.util.HashSet;

@Service
public class UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService(UserRepository userRepository,
                       RoleRepository roleRepository,
                       BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User findUserByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }


    public User saveUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setActive(true);
        Role userRole = roleRepository.findByRole(Roles.ROLE_USER);
        user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
        return userRepository.save(user);

    }
}

