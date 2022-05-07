package com.helpduck.helpduckusers.model.equipment;

import com.helpduck.helpduckusers.controller.EquipmentController;
import com.helpduck.helpduckusers.model.LinkAdd;
import com.helpduck.helpduckusers.model.hateoas.EquipmentHateoas;

import org.springframework.data.domain.Page;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

@Component
public class EquipmentLinkAdder implements LinkAdd<EquipmentHateoas> {

    @Override
    public void addLink(Page<EquipmentHateoas> list) {

        for (EquipmentHateoas equipment : list) {
            String id = equipment.getId();
            Link selfLink = WebMvcLinkBuilder
                    .linkTo(WebMvcLinkBuilder
                            .methodOn(EquipmentController.class)
                            .getEquipment(id))
                    .withSelfRel();
            equipment.add(selfLink);
        }

    }

    @Override
    public void addLink(EquipmentHateoas object) {
        Link selfLink = WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder
                        .methodOn(EquipmentController.class)
                        .getEquipments(null))
                .withRel("equipment");
        object.add(selfLink);
    }

}
