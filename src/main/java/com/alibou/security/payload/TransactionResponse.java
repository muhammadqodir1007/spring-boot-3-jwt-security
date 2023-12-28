package com.alibou.security.payload;

import com.alibou.security.entity.Category;
import com.alibou.security.entity.ItemType;
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
