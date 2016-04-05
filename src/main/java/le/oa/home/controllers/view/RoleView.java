package le.oa.home.controllers.view;

import le.oa.core.models.Role;
import le.oa.core.models.User;

import java.util.ArrayList;
import java.util.List;

public class RoleView {
    private Integer id;
    private String name;
    private List<User> users = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public static RoleView valueOf(Role role) {
        RoleView roleView = new RoleView();
        roleView.setId(role.getId());
        roleView.setName(role.getName());
        return roleView;
    }

}
