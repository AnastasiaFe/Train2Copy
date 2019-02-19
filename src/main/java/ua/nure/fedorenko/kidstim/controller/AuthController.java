package ua.nure.fedorenko.kidstim.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ua.nure.fedorenko.kidstim.auth.jwt.JwtAuthenticationResponse;
import ua.nure.fedorenko.kidstim.auth.jwt.JwtTokenProvider;
import ua.nure.fedorenko.kidstim.controller.request.LoginRequest;
import ua.nure.fedorenko.kidstim.controller.request.RegistrationRequest;
import ua.nure.fedorenko.kidstim.exception.BadRequestException;
import ua.nure.fedorenko.kidstim.exception.ResourceNotFoundException;
import ua.nure.fedorenko.kidstim.model.entity.Parent;
import ua.nure.fedorenko.kidstim.model.entity.User;
import ua.nure.fedorenko.kidstim.service.ChildService;
import ua.nure.fedorenko.kidstim.service.ParentService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
public class AuthController {
    private static final Logger LOG = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private ParentService parentService;
    @Autowired
    private ChildService childService;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public JwtAuthenticationResponse login(@Valid @RequestBody LoginRequest loginRequest, HttpServletResponse httpServletResponse) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        httpServletResponse.addHeader("User", authentication.getName() + ";" + authentication.getAuthorities().iterator().next().toString());
        String token = jwtTokenProvider.generateToken(authentication);
        return new JwtAuthenticationResponse(token);
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public JwtAuthenticationResponse register(@Valid @RequestBody RegistrationRequest registrationRequest, HttpServletResponse httpServletResponse) {
        LOG.info("In register method.....................");
        if (parentService.getParentByEmail(registrationRequest.getEmail()) != null) {
            throw new BadRequestException("Such username is already taken!");
        }
        Parent user = new Parent();
        user.setEmail(registrationRequest.getEmail());
        user.setPassword(registrationRequest.getPassword());
        user.setName(registrationRequest.getName());
        user.setSurname(registrationRequest.getSurname());
        parentService.addParent(user);

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail(user.getEmail());
        loginRequest.setPassword(registrationRequest.getPassword());
        return login(loginRequest, httpServletResponse);
    }

    @GetMapping("/currentUser")
    @ResponseStatus(HttpStatus.OK)
    public User getCurrentUser(HttpServletRequest request) {
        String jwt = jwtTokenProvider.getJwtFromRequest(request);
        String userId = jwtTokenProvider.getUserIdFromJwt(jwt);
        User user = parentService.getParentById(userId);
        if (user == null) {
            user = childService.getChildById(userId);
            if (user == null) {
                throw new ResourceNotFoundException("User", "id", userId);
            }
            return user;
        }
        return user;
    }


    @PostMapping("/logout")
    public void logout(HttpServletRequest request) {
        LOG.info("In logout method.....................");
    }

}
