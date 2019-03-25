package ua.nure.fedorenko.kidstim.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ua.nure.fedorenko.kidstim.auth.UserPrincipal;
import ua.nure.fedorenko.kidstim.model.entity.Child;
import ua.nure.fedorenko.kidstim.model.entity.LoginData;
import ua.nure.fedorenko.kidstim.model.entity.Parent;
import ua.nure.fedorenko.kidstim.service.ChildService;
import ua.nure.fedorenko.kidstim.service.ParentService;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private ParentService parentService;
    @Autowired
    private ChildService childService;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Parent parent = parentService.getParentByEmail(s);
        LoginData loginData;
        Child child;
        String role = "parent";
        if (parent == null) {
            child = childService.getChildByEmail(s);
            role = "child";
            loginData = new LoginData(child.getId(), child.getEmail(), child.getPassword());
        } else {
            loginData = new LoginData(parent.getId(), parent.getEmail(), parent.getPassword());
        }
        return UserPrincipal.create(loginData, role);
    }

    public UserDetails loadUserById(String id) {
        Parent parent = parentService.getParentById(id);
        LoginData loginData;
        Child child;
        String role = "parent";
        if (parent == null) {
            child = childService.getChildById(id);
            role = "child";
            loginData = new LoginData(child.getId(), child.getEmail(), child.getPassword());
        } else {
            loginData = new LoginData(parent.getId(), parent.getEmail(), parent.getPassword());
        }
        return UserPrincipal.create(loginData, role);
    }
}
