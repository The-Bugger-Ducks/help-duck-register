package com.helpduck.helpduckusers.repository;

import com.helpduck.helpduckusers.entity.Equipment;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;


public interface EquipmentRepository extends MongoRepository<Equipment, String> {

    // { }
    @Query("{ 'department' : ?0 }")
    Page<Equipment> findByDepartment(Pageable page, String department);

    @Query("{ 'name' : ?0 }")
    Page<Equipment> findByEquipmentName(Pageable page, String name);

    @Query("{ 'name' : ?0, 'department' : ?1 }")
    Page<Equipment> searchEquipmentNameAndFilterPerDepartment(Pageable page, String name, String department);
  


}