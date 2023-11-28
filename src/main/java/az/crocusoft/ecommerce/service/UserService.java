package az.crocusoft.ecommerce.service;

import az.crocusoft.ecommerce.exception.CustomException;
import az.crocusoft.ecommerce.model.User;
import az.crocusoft.ecommerce.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User insert(User newUser) {
        return userRepository.save(newUser);
    }

    public User getOneUserById(Long userId) {
        return getUser(userId);
    }

    public User update(Long userId, User newUser) {

        if (userRepository.findUserByUsername(newUser.getUsername().toLowerCase()).isPresent())
            throw new CustomException("Username already taken : " + (newUser.getUsername().toLowerCase()),
                    HttpStatus.CONFLICT);
        newUser.setUsername(newUser.getUsername().toLowerCase());
        return userRepository.save(newUser);

    }


    public void removeById(Long userId) {
        userRepository.delete(getUser(userId));
    }

    private User getUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new CustomException("User not found with id : " + id, HttpStatus.NOT_FOUND));
    }
}