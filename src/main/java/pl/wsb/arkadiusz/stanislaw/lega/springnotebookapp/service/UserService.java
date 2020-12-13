package pl.wsb.arkadiusz.stanislaw.lega.springnotebookapp.service;

import org.springframework.stereotype.Service;
import pl.wsb.arkadiusz.stanislaw.lega.springnotebookapp.model.User;
import pl.wsb.arkadiusz.stanislaw.lega.springnotebookapp.repository.UserRepository;

import javax.transaction.Transactional;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public void signUpUser(User user) {
        userRepository.save(user);
    }

    public Iterable<User> listAll() {
        return userRepository.findAll();
    }
}
