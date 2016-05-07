package le.builder;

import com.google.inject.Inject;
import le.oa.core.models.Permission;
import le.oa.core.models.User;
import le.oa.core.repositories.UserRepository;
import le.test.Builder;
import le.util.SecurityEncode;


public class UserBuilder implements Builder<User> {

    @Inject
    private UserRepository userRepository;

    private String name = "admin";
    private String pass = "1";
    private String email = "admin@xx.com";
    private String photo = "photo1.jpg";
    private boolean isOwner = true;
    private Permission permission = Permission.NONE;
    private Integer teamId;

    @Override
    public User build() {
        User user = new User();
        user.setName(name);
        user.setPass(SecurityEncode.encoderByMd5(pass));
        user.setEmail(email);
        user.setPhoto(photo);
        user.setOwner(isOwner);
        user.setPermission(permission);
        user.setTeamId(teamId);
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

    public UserBuilder email(String email) {
        this.email = email;
        return this;
    }

    public UserBuilder isOwner(Boolean isOwner) {
        this.isOwner = isOwner;
        return this;
    }

    public UserBuilder permission(Permission permission) {
        this.permission = permission;
        return this;
    }

    public UserBuilder teamId(Integer teamId) {
        this.teamId = teamId;
        return this;
    }

}
