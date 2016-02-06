package le.builder;

import com.google.inject.Inject;
import le.oa.core.models.User;
import le.oa.core.repositories.UserRepository;
import le.test.Builder;
import le.util.SecurityEncode;


public class UserBuilder implements Builder<User> {

    @Inject
    private UserRepository userRepository;

    private String name = "admin";
    private String account = "admin";
    private String pass = "1";
    private String email = "admin@xx.com";

    @Override
    public User build() {
        User user = new User();
        user.setName(name);
        user.setPass(SecurityEncode.encoderByMd5(pass));
        user.setAccount(account);
        user.setEmail(email);
        return user;
    }

    @Override
    public User create() {
        User user = build();
        userRepository.save(user);
        return user;
    }

    public UserBuilder username(String username) {
        this.name = username;
        return this;
    }

    public UserBuilder password(String password) {
        this.pass = password;
        return this;
    }

    public UserBuilder account(String account) {
        this.account = account;
        return this;
    }

    public UserBuilder email(String email) {
        this.email = email;
        return this;
    }

}
