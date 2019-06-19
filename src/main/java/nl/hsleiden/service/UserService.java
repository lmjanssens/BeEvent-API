package nl.hsleiden.service;

import nl.hsleiden.model.User;
import nl.hsleiden.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserService {

    @Autowired
    UserRepository userRepository;

    public User encodePassword(User user) {
        user.setPassword(this.passwordEncoder().encode(user.getPassword()));
        return user;
    }

    public User updatePassword(User user) {
        return userRepository.save(
                this.encodePassword(user)
        );
    }

    private PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
