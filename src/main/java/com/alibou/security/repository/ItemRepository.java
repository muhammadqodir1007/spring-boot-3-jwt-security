package com.alibou.security.repository;

import com.alibou.security.entity.Item;
import com.alibou.security.entity.ItemType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemRepository extends JpaRepository<Item, Integer> {

    List<Item> findAllByCategoryId(int id);

    boolean existsByItemType(ItemType itemType);

    Optional<Item> findByItemType(ItemType itemType);

}
