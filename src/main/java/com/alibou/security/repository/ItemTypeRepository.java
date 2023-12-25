package com.alibou.security.repository;

import com.alibou.security.entity.ItemType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ItemTypeRepository extends JpaRepository<ItemType, Integer> {


    Optional<ItemType> findByName(String name);
}
