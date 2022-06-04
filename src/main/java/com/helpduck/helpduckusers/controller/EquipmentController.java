package com.helpduck.helpduckusers.controller;

import java.util.Optional;

import com.helpduck.helpduckusers.entity.Equipment;
import com.helpduck.helpduckusers.model.equipment.EquipmentLinkAdder;
import com.helpduck.helpduckusers.model.hateoas.EquipmentHateoas;
import com.helpduck.helpduckusers.model.user.EquipmentUpdater;
import com.helpduck.helpduckusers.repository.EquipmentRepository;
import com.helpduck.helpduckusers.service.EquipmentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/equipment")
public class EquipmentController {

    @Autowired
    private EquipmentRepository repository;
    @Autowired
    private EquipmentLinkAdder linkAdder;
    @Autowired
    private EquipmentService service;

    @GetMapping("/")
    public ResponseEntity<Page<EquipmentHateoas>> getEquipments(Pageable pageable) {
        Page<EquipmentHateoas> pageEquipmentHateoas = service.findAll(pageable);
        if (pageEquipmentHateoas.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        linkAdder.addLink(pageEquipmentHateoas);
        return new ResponseEntity<Page<EquipmentHateoas>>(pageEquipmentHateoas, HttpStatus.FOUND);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EquipmentHateoas> getEquipment(@PathVariable String id) {
        EquipmentHateoas equipmentHateoas = service.findById(id);
        if (equipmentHateoas == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        linkAdder.addLink(equipmentHateoas);
        return new ResponseEntity<EquipmentHateoas>(equipmentHateoas, HttpStatus.FOUND);

    }

    @PostMapping("/create")
    public ResponseEntity<Equipment> EquipmentCreate(@RequestBody Equipment equipment) {
        if (equipment.getId() != null) {
            return new ResponseEntity<Equipment>(HttpStatus.CONFLICT);
        }

        service.createEquipment(equipment);
        return new ResponseEntity<Equipment>(HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<Equipment> EquipmentUpdate(@RequestBody Equipment updatedEquipment) {
        HttpStatus status = HttpStatus.CONFLICT;
        Optional<Equipment> equipmentOptional = repository.findById(updatedEquipment.getId());
        if (!equipmentOptional.isEmpty()) {
            Equipment equipment = equipmentOptional.get();
            EquipmentUpdater updater = new EquipmentUpdater();
            updater.updateData(equipment, updatedEquipment);
            repository.save(equipment);
            status = HttpStatus.OK;
        } else {
            status = HttpStatus.BAD_REQUEST;
        }

        return new ResponseEntity<Equipment>(status);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Equipment> DeleteEquipment(@PathVariable String id) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        Optional<Equipment> equipmentOptional = repository.findById(id);
        if (!equipmentOptional.isEmpty()) {
            repository.delete(equipmentOptional.get());
            status = HttpStatus.OK;
        }
        return new ResponseEntity<Equipment>(status);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<EquipmentHateoas>> getAllEquipmentByDepartment(
            @RequestParam Optional<String> name,
            @RequestParam Optional<String> department,
            Pageable page) {

        Page<EquipmentHateoas> pageEquipmentHateoas;

        if (department.isPresent() && name.isPresent()) {
            pageEquipmentHateoas = service.findEquipmentNameAndFilterPerDepartment(page, name.get(),
                    department.get());
        } else if (!department.isPresent() && !name.isPresent()) {
            pageEquipmentHateoas = service.findAll(page);
        } else if (department.isPresent() && !name.isPresent()) {
            pageEquipmentHateoas = service.findByDepartment(page, department.get());
        } else {
            pageEquipmentHateoas = service.findByEquipmentName(page, name.get());
        }

        if (pageEquipmentHateoas.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        linkAdder.addLink(pageEquipmentHateoas);
        return new ResponseEntity<Page<EquipmentHateoas>>(pageEquipmentHateoas, HttpStatus.FOUND);
    }

}
