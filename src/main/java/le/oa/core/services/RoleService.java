package le.oa.core.services;

import com.google.inject.Inject;
import le.oa.core.models.Role;
import le.oa.core.models.User;
import le.oa.core.repositories.RoleRepository;
import le.oa.core.repositories.UserRepository;

import java.util.List;
import java.util.Optional;

public class RoleService {

    private RoleRepository roleRepository;
    private UserRepository userRepository;

    @Inject
    public RoleService(RoleRepository roleRepository, UserRepository userRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    public void save(Role role) {
        if (role.getId() == null) {
            roleRepository.save(role);
        } else {
            roleRepository.save(role);
        }
    }

    public void delete(Role role) {
        List<User> users = userRepository.findByRoleId(role.getId());
        for (User user : users) {
            user.setRole(null);
            userRepository.save(user);
        }
        roleRepository.delete(role);
    }

    public List<Role> findRoles() {
        return roleRepository.findRoles();
    }

    public Optional<Role> findById(Integer id) {
        return roleRepository.findById(id);
    }
}
