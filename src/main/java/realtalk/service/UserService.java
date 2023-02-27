package realtalk.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import realtalk.model.User;
import realtalk.repository.UserRepository;
import realtalk.service.exception.ChatNotFoundException;
import realtalk.service.exception.UserLoginExistsException;
import realtalk.service.exception.WrongLoginOrPasswordException;
import realtalk.util.FileUploadUtil;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private FileUploadUtil fileUploadUtil;

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
    public User registration(User user) {
        if(userRepository.findByLogin(user.getLogin()) == null) {
            //validate
            return userRepository.save(user);
        } else {
            throw new UserLoginExistsException(user.getLogin());
        }
    }

    @Transactional
    public String authentication(User user){
        if(userRepository.findByLogin(user.getLogin()) == null){
            throw new WrongLoginOrPasswordException();
        }else {
            //TODO: jwt токен секьюрити
            return "";
        }
    }

    @Transactional
    public User updateUser(User user, MultipartFile file){
        final User curUser = findUser(user.getId());
        curUser.setName(user.getName());
        curUser.setSurname(user.getSurname());
        if(file!=null){
            curUser.setPhoto(fileUploadUtil.uploadFile(file));
        }
        return userRepository.save(curUser);
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
