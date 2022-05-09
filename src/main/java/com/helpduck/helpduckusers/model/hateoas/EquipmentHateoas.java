package com.helpduck.helpduckusers.model.hateoas;

import com.helpduck.helpduckusers.entity.Equipment;

import org.springframework.data.annotation.Id;
import org.springframework.hateoas.RepresentationModel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EquipmentHateoas extends RepresentationModel<EquipmentHateoas> {
    
    @Id
    private String id;

    private String name;
    private String model;

    private String brand;
    private String type;
    private String department;

    public EquipmentHateoas(Equipment equipment){
        id = equipment.getId();

        name = equipment.getName();
        model = equipment.getModel();

        brand = equipment.getBrand();
        type = equipment.getType();

        department = equipment.getDepartment();
    }


}
