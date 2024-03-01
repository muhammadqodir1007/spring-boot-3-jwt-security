package com.alibou.security.payload.response;

import com.alibou.security.entity.MaterialCategory;
import com.alibou.security.entity.MaterialType;
import com.alibou.security.payload.dto.UserDto;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MaterialTransactionRes {


    private int id;
    private MaterialType materialType;
    private UserDto userDto;
    private MaterialCategory category;
    private long quantity;
    private LocalDateTime actionDate;
    private String actionType;
}
