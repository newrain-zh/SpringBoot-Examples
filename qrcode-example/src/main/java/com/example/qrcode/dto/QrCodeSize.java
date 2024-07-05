package com.example.qrcode.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.example.qrcode.utils.QrcodeUtils.SMALL;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class QrCodeSize {

    /**
     * 长
     */
    private Integer length = SMALL;

    /**
     * 宽
     */
    private Integer width = SMALL;
}