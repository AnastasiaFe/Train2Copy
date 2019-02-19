package ua.nure.fedorenko.kidstim.service;

import ua.nure.fedorenko.kidstim.model.entity.Child;

public interface ChildService {

    void addChild(Child child, String parentId);

    Child getChildById(String id);

    Child getChildByEmail(String email);

    void updateChild(Child child);
}
