package com.alibou.security.service;

import com.alibou.security.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
public class HomeService {

    ItemRepository itemRepository;
    MaterialRepository materialRepository;
    ItemTypeRepository itemTypeRepository;
    MaterialTypeRepository materialTypeRepository;
    CategoryRepository categoryRepository;
    MaterialCategoryRepository materialCategoryRepository;


    public Map<String, Long> count() {
        Map<String, Long> count = new HashMap<>();
        long items = itemTypeRepository.count();
        long materials = materialTypeRepository.count();
        long itemCategories = categoryRepository.count();
        long materialCategories = materialCategoryRepository.count();
        count.put("items", items);
        count.put("materials", materials);
        count.put("itemCategories", itemCategories);
        count.put("materialCategories", materialCategories);
        return count;

    }


}
