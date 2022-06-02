package com.helpduck.helpduckusers.service;

import java.util.Optional;

import com.helpduck.helpduckusers.entity.Equipment;
import com.helpduck.helpduckusers.model.hateoas.EquipmentHateoas;
import com.helpduck.helpduckusers.repository.EquipmentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EquipmentService {
    @Autowired
    private EquipmentRepository repository;

    @Transactional(readOnly = true)
    public Page<EquipmentHateoas> findAll(Pageable pageable) {
        Page<Equipment> equipments = repository.findAll(pageable);
        Page<EquipmentHateoas> page = equipments.map(x -> new EquipmentHateoas(x));
        return page;
    }

    @Transactional(readOnly = true)
    public EquipmentHateoas findById(String id) {
        Optional<Equipment> equipment = repository.findById(id);
        if (equipment.isEmpty())
            return null;

        EquipmentHateoas equipmentHateoas = new EquipmentHateoas(equipment.get());
        return equipmentHateoas;
    }

    @Transactional(readOnly = true)
    public Page<EquipmentHateoas> searchEquipmentNameAndFilterPerDepartment(Pageable pageable, String name,
            String department) {
        Page<Equipment> equipments = repository.searchEquipmentNameAndFilterPerDepartment(pageable, name, department);
        Page<EquipmentHateoas> page = equipments.map(x -> new EquipmentHateoas(x));
        return page;
    }

    @Transactional(readOnly = true)
    public Page<EquipmentHateoas> findByEquipmentName(Pageable pageable, String name) {
        Page<Equipment> equipments = repository.findByEquipmentName(pageable, name);
        Page<EquipmentHateoas> page = equipments.map(x -> new EquipmentHateoas(x));
        return page;
    }

    @Transactional(readOnly = true)
    public Page<EquipmentHateoas> findByDepartment(Pageable pageable, String department) {
        Page<Equipment> equipments = repository.findByDepartment(pageable, department);
        Page<EquipmentHateoas> page = equipments.map(x -> new EquipmentHateoas(x));
        return page;
    }

    @Transactional(readOnly = true)
    public Equipment createEquipment(Equipment equipment) {
        return repository.insert(equipment);
    }

}
