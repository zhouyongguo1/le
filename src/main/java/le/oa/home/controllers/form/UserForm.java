package le.oa.home.controllers.form;

import le.oa.core.models.Permission;
import le.oa.core.models.Role;
import le.oa.core.models.User;
import le.util.SecurityEncode;

public class UserForm {
    public static final String USER_THEME_COOKIE_NAME = "user_theme";
    private Integer id;
    private String name;
    private String email;
    private String photo;
    private Integer roleId;
    private Permission permission;
    private Boolean resetPassword = false;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Permission getPermission() {
        return permission;
    }

    public void setPermission(Permission permission) {
        this.permission = permission;
    }

    public Boolean getResetPassword() {
        return resetPassword;
    }

    public void setResetPassword(Boolean resetPassword) {
        this.resetPassword = resetPassword;
    }

    public User toUser(User user, Role role) {
        if (user == null) {
            user = new User();
            user.setId(this.id);
        }
        user.setName(name);
        user.setEmail(email);
        if (resetPassword) {
            user.setPass(SecurityEncode.encoderByMd5(User.DEFAULT_PASS));
        }
        user.setPhoto(photo);
        if (permission != null) {
            user.setPermission(permission);
        } else {
            user.setPermission(Permission.NONE);
        }
        if (role == null && roleId != null) {
            role = new Role();
            role.setId(roleId);
        }
        user.setRole(role);
        return user;
    }

}
