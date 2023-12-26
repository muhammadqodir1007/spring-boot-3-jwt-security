package com.alibou.security.payload;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class ItemDto {

    int itemType;
    String description;
    long quantity;
    int adminId;
    int categoryId;


}
