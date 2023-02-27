package realtalk.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import realtalk.model.User;
import realtalk.repository.UserRepository;
import realtalk.service.exception.ChatNotFoundException;
import realtalk.service.exception.UserLoginExistsException;
import realtalk.service.exception.WrongLoginOrPasswordException;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Transactional(readOnly = true)
    public User findUser(Long id) {
        final Optional<User> user = userRepository.findById(id);
        return user.orElseThrow(() -> new ChatNotFoundException(id));
    }

    @Transactional
    public User registration(String login, String password, String name, String surname) {
        if(userRepository.findByLogin(login) == null) {
            final User user = new User(login, password, name, surname);
            //validate
            return userRepository.save(user);
        } else {
            throw new UserLoginExistsException(login);
        }
    }

    @Transactional
    public String authentication(String login, String password){
        final User user = userRepository.findByLogin(login);
        if(user == null){
            throw new WrongLoginOrPasswordException();
        }else {
            //TODO: jwt токен секьюрити
            return "";
        }
    }

    //TODO: update user
    @Transactional
    public User updateUser(User user){
        final User curUser = findUser(user.getId());
        return curUser;
    }

    public void subscribe(User user, User subscribed){
        if(!user.getSubscriptions().contains(subscribed)) {
            user.getSubscriptions().add(subscribed);
            subscribed.getSubscribers().add(user);
        }else {
            user.getSubscriptions().remove(subscribed);
            subscribed.getSubscribers().remove(user);
        }
    }

    @Transactional
    public User deleteUser(Long id) {
        final User user = findUser(id);
        userRepository.delete(user);
        return user;
    }
}
