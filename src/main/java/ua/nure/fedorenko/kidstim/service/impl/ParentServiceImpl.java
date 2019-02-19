package ua.nure.fedorenko.kidstim.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.nure.fedorenko.kidstim.model.entity.Child;
import ua.nure.fedorenko.kidstim.model.entity.Parent;
import ua.nure.fedorenko.kidstim.model.repository.ParentRepository;
import ua.nure.fedorenko.kidstim.service.ParentService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ParentServiceImpl implements ParentService {

    @Autowired
    private ParentRepository parentRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void addParent(Parent parent) {
        parent.setPassword(passwordEncoder.encode(parent.getPassword()));
        parent.setChildren(new ArrayList<>());
        parentRepository.save(parent);
    }

    @Override
    public Parent getParentById(String id) {
        Optional<Parent> parent=parentRepository.findById(id);
        return parent.orElse(null);
    }

    @Override
    public Parent getParentByEmail(String email) {
        return parentRepository.findByEmail(email);
    }

    @Override
    public void updateParent(Parent parent) {
        parentRepository.save(parent);
    }

    @Override
    public List<Child> getParentsChildren(String email) {
        Parent p = getParentByEmail(email);
        return p.getChildren();
    }
}
