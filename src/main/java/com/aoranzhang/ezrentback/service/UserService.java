package com.aoranzhang.ezrentback.service;

import com.aoranzhang.ezrentback.data.entity.User;
import com.aoranzhang.ezrentback.data.entity.UserFormInput;
import com.aoranzhang.ezrentback.data.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public void saveUser(User user) throws DataIntegrityViolationException {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Transactional
    public User saveUser(UserFormInput userFormInput) throws DataIntegrityViolationException {
        User user = new User();
        user.setPassword(userFormInput.getPassword());
        user.setEmail(userFormInput.getEmail());
        user.setName(userFormInput.getName());
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        //user.setLastLogin(userFormInput.getLastLogin());
        userRepository.save(user);
        return user;
    }

    @Transactional
    public boolean updateUser(User user) {
        if (userRepository.exists(user.getId())) {
            userRepository.save(user);
            return true;
        } else {
            return false;
        }
    }

    public User getUserById(String id) {
        return userRepository.findOne(id);
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public List<User> findAll() {
        ArrayList users = new ArrayList();
        userRepository.findAll().forEach((u) -> users.add(u));
        return users;
    }
}
