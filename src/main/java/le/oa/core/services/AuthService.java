package le.oa.core.services;

import com.google.inject.Inject;
import le.oa.core.models.User;
import le.oa.core.repositories.UserRepository;
import le.util.SecurityEncode;

import java.util.Optional;

public class AuthService {

    private UserRepository userRepository;

    @Inject
    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> findByNameAndPass(String userName, String pass) {
        Optional<User> optional = userRepository.findUserByName(userName);
        if (optional.isPresent()) {
            String pass1 = SecurityEncode.encoderByMd5(pass);
            if (pass.equals(pass1)) {
                return optional;
            } else {
                return Optional.empty();
            }
        } else {
            return Optional.empty();
        }
    }
}

