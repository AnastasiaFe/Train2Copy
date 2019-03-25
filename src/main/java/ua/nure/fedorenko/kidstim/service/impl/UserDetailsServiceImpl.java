package ua.nure.fedorenko.kidstim.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ua.nure.fedorenko.kidstim.model.entity.Child;
import ua.nure.fedorenko.kidstim.model.entity.LoginData;
import ua.nure.fedorenko.kidstim.model.entity.Parent;
import ua.nure.fedorenko.kidstim.service.ChildService;
import ua.nure.fedorenko.kidstim.service.ParentService;

import java.util.ArrayList;
import java.util.List;

public class UserDetailsServiceImpl implements UserDetailsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    @Autowired
    private ParentService parentService;

    @Autowired
    private ChildService childService;

    private static final String PARENT_ROLE = "parent";
    private static final String CHILD_ROLE = "child";

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        LOGGER.info("Load user by username is working...");
        List<GrantedAuthority> authorityList = new ArrayList<>();
        Parent user = parentService.getParentByEmail(s);
        LoginData loginData;
        if (user == null) {
            Child child = childService.getChildByEmail(s);
            if (child == null) {
                LOGGER.info("UserData not found!");
                throw new UsernameNotFoundException(s);
            } else {
                loginData = new LoginData(child.getId(), child.getEmail(), child.getPassword());
                authorityList.add(new SimpleGrantedAuthority(CHILD_ROLE));
            }
        } else {
            loginData = new LoginData(user.getId(), user.getEmail(), user.getPassword());
            authorityList.add(new SimpleGrantedAuthority(PARENT_ROLE));
        }
        return new org.springframework.security.core.userdetails.User(loginData.getEmail(), loginData.getPassword(), authorityList);
    }
}
