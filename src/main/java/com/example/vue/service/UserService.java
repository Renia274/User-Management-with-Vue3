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

    // Automatically injects an instance of UserRepository into this service.
    @Autowired
    private UserRepository userRepository;

    // Defines a pattern for validating names and surnames.
    private static final Pattern NAME_PATTERN = Pattern.compile("^[A-Z][a-zA-Z]*$");

    // Retrieves a paginated list of users from the repository.
    public Page<Users> getAllUsers(int page, int size) {
        // Creates a Pageable object for pagination using the provided page number and size.
        Pageable pageable = PageRequest.of(page, size);
        // Returns a paginated list of users from the repository.
        return userRepository.findAll(pageable);
    }

    // Retrieves a user by their ID from the repository.
    public Users getUserById(Long id) {
        // Finds the user by ID, or returns null if not found.
        return userRepository.findById(id).orElse(null);
    }

    // Saves a new or existing user after validating them.
    public Users saveUser(Users user) {
        // Validates the user details before saving.
        validateUser(user);
        // Sets the user in the address if the address is not null.
        if (user.getAddress() != null) {
            user.getAddress().setUser(user);
        }
        // Saves the user to the repository and returns the saved user.
        return userRepository.save(user);
    }

    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

    // Validates user details before saving.
    private void validateUser(Users user) throws FillingAndValidationException {
        // StringBuilder to accumulate validation error messages.
        StringBuilder validationErrors = new StringBuilder();

        // Validate mandatory fields
        // Checks if the name is null or empty and appends an error message if it is.
        if (user.getName() == null || user.getName().trim().isEmpty()) {
            validationErrors.append("Name is required.\n");
        }
        // Checks if the surname is null or empty and appends an error message if it is.
        if (user.getSurname() == null || user.getSurname().trim().isEmpty()) {
            validationErrors.append("Surname is required.\n");
        }
        // Checks if the gender is null or empty and appends an error message if it is.
        if (user.getGender() == null || user.getGender().trim().isEmpty()) {
            validationErrors.append("Gender is required.\n");
        }
        // Checks if the birthdate is null and appends an error message if it is.
        if (user.getBirthdate() == null) {
            validationErrors.append("Birthdate is required.\n");
        }

        // Validate name and surname formats
        // Checks if the name does not match the required pattern and appends an error message.
        if (user.getName() == null || !NAME_PATTERN.matcher(user.getName()).matches()) {
            validationErrors.append("Name must start with a capital letter and contain only letters.");
        }
        // Checks if the surname does not match the required pattern and appends an error message.
        if (user.getSurname() == null || !NAME_PATTERN.matcher(user.getSurname()).matches()) {
            // Adds a newline if there were previous validation errors.
            if (validationErrors.length() > 0) {
                validationErrors.append("\n");
            }
            validationErrors.append("Surname must start with a capital letter and contain only letters.");
        }

        // If there are validation errors, throw an exception with the accumulated error messages.
        if (validationErrors.length() > 0) {
            throw new FillingAndValidationException(validationErrors.toString());
        }
    }
}
