package ua.nure.fedorenko.kidstim.service;

import ua.nure.fedorenko.kidstim.model.entity.Child;
import ua.nure.fedorenko.kidstim.model.entity.Parent;

import java.util.List;

public interface ParentService {

    void addParent(Parent parent);

    Parent getParentById(String id);

    Parent getParentByEmail(String email);

    void updateParent(Parent parent);

    List<Child> getParentsChildren(String parent);
}
