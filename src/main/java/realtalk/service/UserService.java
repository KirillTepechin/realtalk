package realtalk.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import realtalk.jwt.JwtProvider;
import realtalk.model.User;
import realtalk.repository.UserRepository;
import realtalk.service.exception.UserLoginExistsException;
import realtalk.service.exception.UserNotFoundException;
import realtalk.service.exception.WrongLoginOrPasswordException;
import realtalk.util.FileUploadUtil;

import java.util.*;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private FileUploadUtil fileUploadUtil;
    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private PasswordEncoder encoder;

    @Transactional(readOnly = true)
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Transactional(readOnly = true)
    public User findUser(Long id) {
        final Optional<User> user = userRepository.findById(id);
        return user.orElseThrow(() -> new UserNotFoundException(id));
    }

    @Transactional(readOnly = true)
    public User findUserByLogin(String login){
        return userRepository.findByLogin(login);
    }

    @Transactional(readOnly = true)
    public List<User> findUsersByQuery(String query){
//        String template = "%" + query + "%";
//        query = template.toUpperCase();
        query = query.toUpperCase();
        return userRepository.findByLoginIgnoreCaseContainingOrNameIgnoreCaseContainingOrSurnameIgnoreCaseContaining(query, query, query);
    }

    @Transactional
    public void registration(String login,String password, String name, String surname, MultipartFile file,
                           Date borthdate, String city){
        var user = new User();
        if(name != null && !name.isBlank())
            user.setName(name);
        if(login != null && !login.isBlank()){
            if(userRepository.findByLogin(login) == null) {
                user.setLogin(login);
            } else {
                throw new UserLoginExistsException(login);
            }
        }
        if(password != null && !password.isBlank())
            user.setPassword(encoder.encode(password));
        if(surname != null && !surname.isBlank())
            user.setSurname(surname);
        if(file!=null)
            user.setPhoto(fileUploadUtil.uploadFile(file));
        if(borthdate != null)
            user.setBorthdate(borthdate);
        if(city != null && !city.isBlank())
            user.setCity(city);

        userRepository.save(user);
    }

    @Transactional
    public String authentication(User user) {
        User userDB = userRepository.findByLogin(user.getLogin());
        if (userDB == null) {
            throw new WrongLoginOrPasswordException();
        }
        if (encoder.matches(user.getPassword(), userDB.getPassword())) {
            return jwtProvider.generateAccessToken(user);
        } else {
            throw new WrongLoginOrPasswordException();
        }
    }

    @Transactional
    public User updateUser(User user,String login,String password, String name, String surname, MultipartFile file,
                           Date borthdate, String city){
        final User curUser = findUser(user.getId());
        if(name != null && !name.isBlank())
            curUser.setName(name);
        if(login != null && !login.isBlank() && !Objects.equals(login, user.getLogin())){
            if(userRepository.findByLogin(user.getLogin()) == null) {
                curUser.setLogin(login);
            } else {
                throw new UserLoginExistsException(login);
            }
        }
        if(password != null && !password.isBlank())
            curUser.setPassword(encoder.encode(password));
        if(surname != null && !surname.isBlank())
            curUser.setSurname(surname);
        if(file!=null)
            curUser.setPhoto(fileUploadUtil.uploadFile(file));
        if(borthdate != null)
            curUser.setBorthdate(borthdate);
        if(city != null && !city.isBlank())
            curUser.setCity(city);

        return userRepository.save(curUser);
    }

    @Transactional
    public User updateUserPreferences(User user, Set<String> tags){
        final User curUser = findUser(user.getId());
        curUser.setTags(tags);
        return userRepository.save(curUser);
    }

    @Transactional
    public boolean subscribe(User user, User subscribed){
        if(!user.getSubscriptions().contains(subscribed)) {
            user.getSubscriptions().add(subscribed);
            subscribed.getSubscribers().add(user);

            userRepository.save(user);
            userRepository.save(subscribed);

            return true;
        }else {
            user.getSubscriptions().remove(subscribed);
            subscribed.getSubscribers().remove(user);

            userRepository.save(user);
            userRepository.save(subscribed);

            return false;
        }
    }

    @Transactional
    public User deleteUser(Long id) {
        final User user = findUser(id);
        userRepository.delete(user);
        return user;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByLogin(username);
    }

}
