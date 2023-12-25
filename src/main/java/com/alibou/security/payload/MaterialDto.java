package com.alibou.security.payload;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class MaterialDto {

    int materialTypeId;
    int categoryId;
    String description;
    long quantity;
    int adminId;
}
