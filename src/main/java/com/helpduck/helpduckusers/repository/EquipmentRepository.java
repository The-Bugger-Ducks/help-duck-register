package com.helpduck.helpduckusers.repository;

import com.helpduck.helpduckusers.entity.Equipment;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;

public interface EquipmentRepository extends MongoRepository<Equipment, String> {
    @Query("{'equipment.name': {$regex: ?0 }})")
    Page<Equipment> findAllByName(Pageable pageable, String name);
}