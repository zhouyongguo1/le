package le.oa.home.controllers.form;

import le.oa.core.models.Permission;
import le.oa.core.models.Role;
import le.oa.core.models.User;
import le.util.SecurityEncode;

public class UserForm {
    private String name;
    private String email;
    private String photo;
    private Integer roleId;
    private Permission permission = Permission.NONE;

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

    public User toUser(Role role) {
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPass(SecurityEncode.encoderByMd5(User.DEFAULT_PASS));
        user.setPhoto(photo);
        user.setPermission(permission);
        user.setRole(role);
        return user;
    }


}
