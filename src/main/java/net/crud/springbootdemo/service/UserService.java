package net.crud.springbootdemo.service;

import net.crud.springbootdemo.model.Role;
import net.crud.springbootdemo.repository.UserRepository;
import net.crud.springbootdemo.model.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, RoleService roleService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

//    public User findById(String username){
//        return userRepository.findById(username).get();
//    }

    public User findByName(String userName) { return userRepository.findByName(userName); }

    public List<User> findAll(){
        return userRepository.findAll();
    }

//    public User saveUser(User user){
//        return userRepository.save(user);
//    }
    public ResponseEntity<User> saveUser(User user){
        if (validationUser(user) == true) {
            Role role = roleService.findByName(user.getRole());
            user.setRole(role);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            return new ResponseEntity<>(userRepository.save(user), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(user, HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    public void deleteById(Long id){
        userRepository.deleteById(id);
    }

    public User updateUser(User user) { return userRepository.save(user); }

    private boolean validationUser(User user){
       if (StringUtils.isBlank(user.getName()) == true){
           return false;
       }

        if (StringUtils.isBlank(user.getLastName()) == true){
            return false;
        }

        if (StringUtils.isBlank(user.getFirstName()) == true){
            return false;
        }

        if (StringUtils.isBlank(user.getPassword()) == true){
            return false;
        }

        if (StringUtils.isBlank(user.getEmail()) == true){
            return false;
        }

        if (StringUtils.isBlank(user.getRole()) == true){
            return false;
        }

       return true;
    }
}
