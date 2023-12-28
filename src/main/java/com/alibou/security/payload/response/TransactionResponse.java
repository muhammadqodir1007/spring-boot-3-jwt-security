package com.alibou.security.payload.response;

import com.alibou.security.entity.Category;
import com.alibou.security.entity.ItemType;
import com.alibou.security.payload.dto.UserDto;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TransactionResponse {
    private int id;
    private ItemType itemType;

    private UserDto userDto;

    private Category category;

    private long quantity;
    private LocalDateTime actionDate;
    private String actionType;

}
