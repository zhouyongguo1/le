package le.oa.home.controllers.form;

import com.google.common.base.Strings;
import le.oa.core.models.User;
import le.util.SecurityEncode;

public class UserSettingForm {
    private String name;
    private String email;
    private String photo;
    private String pass;

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

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public User of(User user) {
        if (name != null) {
            user.setName(name);
        }
        if (email != null) {
            user.setEmail(email);
        }
        if (photo != null) {
            user.setPhoto(photo);
        }
        if (!Strings.isNullOrEmpty(pass)) {
            user.setPass(SecurityEncode.encoderByMd5(pass));
        }
        return user;
    }

}
