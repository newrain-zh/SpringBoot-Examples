package com.example.redis.dto.simple;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * address.
 *
 * @author pdai
 */
@Data
@Builder
@AllArgsConstructor
public class AddressParam {
    
    private String city;

    private String zipcode;
}