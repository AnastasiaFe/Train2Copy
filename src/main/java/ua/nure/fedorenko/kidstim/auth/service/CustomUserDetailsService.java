package ua.nure.fedorenko.kidstim.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ua.nure.fedorenko.kidstim.auth.UserPrincipal;
import ua.nure.fedorenko.kidstim.model.entity.User;
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
        User user = parentService.getParentByEmail(s);
        String role="parent";
        if (user == null) {
            user = childService.getChildByEmail(s);
            role="child";
        }
        return UserPrincipal.create(user,role);
    }

    public UserDetails loadUserById(String id) {
        User user = parentService.getParentById(id);
        String role="parent";
        if (user == null) {
            user = childService.getChildById(id);
            role="child";
        }
        return UserPrincipal.create(user,role);
    }
}
