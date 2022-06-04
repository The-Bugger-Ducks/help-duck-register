package com.helpduck.helpduckusers.repository;

import com.helpduck.helpduckusers.entity.Equipment;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface EquipmentRepository extends MongoRepository<Equipment, String> {

    @Query("{ 'department' : {$regex:?0, '$options' : 'i'} }")
    Page<Equipment> findByDepartment(Pageable page, String department);

    @Query("{ 'name' : {$regex:?0, '$options' : 'i'} }")
    Page<Equipment> findByEquipmentName(Pageable page, String name);

    @Query("{ 'name' :{$regex:?0, '$options' : 'i'}, $and: [{'department': {$regex:?1, '$options' : 'i'} }] }")
    Page<Equipment> findEquipmentNameAndFilterPerDepartment(Pageable page, String name, String department);

}