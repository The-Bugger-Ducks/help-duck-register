package com.helpduck.helpduckusers.model.user;

import com.helpduck.helpduckusers.entity.Equipment;
import com.helpduck.helpduckusers.model.NullStringVerify;

public class EquipmentUpdater {
    private NullStringVerify verifier = new NullStringVerify();

    public void updateData(Equipment equipment, Equipment updatedEquipment) {
        if (!verifier.verify(updatedEquipment.getName()))
            equipment.setName(updatedEquipment.getName());

        if (!verifier.verify(updatedEquipment.getModel()))
            equipment.setModel(updatedEquipment.getModel());

        if (!verifier.verify(updatedEquipment.getBrand()))
            equipment.setBrand(updatedEquipment.getBrand());

        if (!verifier.verify(updatedEquipment.getType()))
            equipment.setType(updatedEquipment.getType());

        if (!verifier.verify(updatedEquipment.getDepartment()))
            equipment.setDepartment(updatedEquipment.getDepartment());

    }
}
