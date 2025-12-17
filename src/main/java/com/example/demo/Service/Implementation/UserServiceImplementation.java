package com.example.demo.Service.Implementation;

import com.example.demo.Model.Permission;
import com.example.demo.Model.User;
import com.example.demo.Repository.PermissionRepository;
import com.example.demo.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class UserServiceImplementation implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User checkedUser = userRepository.findByUsername(username);

        if (Objects.nonNull(checkedUser)) {
            return checkedUser;
        }

        throw new UsernameNotFoundException(username);
    }

    public boolean registerUser(User user) {

        if (Objects.isNull(userRepository.findByUsername(user.getUsername()))) {
            List<Permission> permissions = List.of(permissionRepository.findByName("ROLE_USER"));
            user.setPermissions(permissions);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
            return true;
        }

        return false;
    }
}
