package com.example.vue.service;

import com.example.vue.FillingAndValidationException;
import com.example.vue.data.Users;
import com.example.vue.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private static final Pattern NAME_PATTERN = Pattern.compile("^[A-Z][a-zA-Z]*$");

    public Page<Users> getAllUsers(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return userRepository.findAll(pageable);
    }

    public Users getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public Users saveUser(Users user) throws FillingAndValidationException {
        validateUser(user);
        if (user.getAddress() != null) {
            user.getAddress().setUser(user);
        }
        return userRepository.save(user);
    }

    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

    private void validateUser(Users user) throws FillingAndValidationException {
        StringBuilder validationErrors = new StringBuilder();

        if (user.getName() == null || user.getName().trim().isEmpty()) {
            validationErrors.append("Name is required.\n");
        }
        if (user.getSurname() == null || user.getSurname().trim().isEmpty()) {
            validationErrors.append("Surname is required.\n");
        }
        if (user.getGender() == null || user.getGender().trim().isEmpty()) {
            validationErrors.append("Gender is required.\n");
        }
        if (user.getBirthdate() == null) {
            validationErrors.append("Birthdate is required.\n");
        }

        if (user.getName() != null && !NAME_PATTERN.matcher(user.getName()).matches()) {
            validationErrors.append("Name must start with a capital letter and contain only letters.");
        }
        if (user.getSurname() != null && !NAME_PATTERN.matcher(user.getSurname()).matches()) {
            if (validationErrors.length() > 0) {
                validationErrors.append("\n");
            }
            validationErrors.append("Surname must start with a capital letter and contain only letters.");
        }

        if (validationErrors.length() > 0) {
            throw new FillingAndValidationException(validationErrors.toString());
        }
    }
}
