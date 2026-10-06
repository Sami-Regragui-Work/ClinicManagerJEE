package org.jee.clinicmanager.service;

import org.jee.clinicmanager.dto.UserLoginDTO;
import org.jee.clinicmanager.dto.UserRegistrationDTO;
import org.jee.clinicmanager.exception.DuplicateEmailException;
import org.jee.clinicmanager.exception.InvalidEmailException;
import org.jee.clinicmanager.exception.InvalidPasswordException;
import org.jee.clinicmanager.exception.UserException;
import org.jee.clinicmanager.model.User;
import org.jee.clinicmanager.model.enums.UserRole;
import org.jee.clinicmanager.repository.UserRepository;
import org.jee.clinicmanager.util.PasswordUtil;

public class AuthService {
    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User registerPatient(UserRegistrationDTO userRegistrationDTO) throws DuplicateEmailException {
        if (this.userRepository.existsByEmail(userRegistrationDTO.getEmail()))
            throw new DuplicateEmailException("Email already registered");

        String hashedPassword = PasswordUtil.hash(userRegistrationDTO.getPassword());

        User user = new User(
                userRegistrationDTO.getFirstName(),
                userRegistrationDTO.getLastName(),
                userRegistrationDTO.getEmail(),
                userRegistrationDTO.getPhone(),
                hashedPassword,
                UserRole.PATIENT,
                true
        );

        return this.userRepository.save(user);
    }

    public User authenticate(UserLoginDTO userLoginDTO) throws UserException {
        String email = userLoginDTO.getEmail();

        User currentUser = this.userRepository.findByEmail(email);

        if (currentUser == null)
            throw new InvalidEmailException("Email is not registered");

        if (!currentUser.isActive())
            throw new UserException("Account is disabled");

        if (PasswordUtil.verify(userLoginDTO.getPassword(), currentUser.getPasswordHash()))
            return currentUser;

       throw new InvalidPasswordException("Wrong password");
    }
}
