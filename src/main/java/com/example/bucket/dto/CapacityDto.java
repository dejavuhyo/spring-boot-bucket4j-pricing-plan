package com.example.bucket.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CapacityDto {
    private int free;
    private int basic;
    private int professional;
}
