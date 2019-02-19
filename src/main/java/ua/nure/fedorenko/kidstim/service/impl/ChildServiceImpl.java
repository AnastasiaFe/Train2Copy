package ua.nure.fedorenko.kidstim.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.nure.fedorenko.kidstim.model.entity.Child;
import ua.nure.fedorenko.kidstim.model.entity.Parent;
import ua.nure.fedorenko.kidstim.model.repository.ChildRepository;
import ua.nure.fedorenko.kidstim.service.ChildService;
import ua.nure.fedorenko.kidstim.service.ParentService;

import java.util.Optional;

@Transactional
@Service
public class ChildServiceImpl implements ChildService {

    @Autowired
    private ChildRepository childRepository;

    @Autowired
    private ParentService parentService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void addChild(Child child, String parentId) {
        child.setPassword(bCryptPasswordEncoder.encode(child.getPassword()));
        childRepository.save(child);

        Parent parent = parentService.getParentById(parentId);
        parent.getChildren().add(child);
        parentService.updateParent(parent);
    }

    @Override
    public Child getChildById(String id) {
        Optional<Child> child = childRepository.findById(id);
        return child.orElse(null);
    }

    @Override
    public Child getChildByEmail(String email) {
        return childRepository.findByEmail(email);
    }

    @Override
    public void updateChild(Child child) {
        childRepository.save(child);
    }
}
