package com.example.qrcode.dto;

import lombok.Data;

/**
 * 下载二维码公共参数
 * 请求样式
 * {
 *     "downloadAll": true,
 *     "qrcodeFormat": "PNG",
 *     "qrCodeSize": {
 *         "length": 300,
 *         "width": 300
 *     }
 * }
 */

@Data
public class QrcodeDownloadCommonParam {

    /**
     * 是否下载全部格式
     */
    private Boolean downloadAll;

    /**
     * 二维码格式
     */
    private QrcodeFormat qrcodeFormat;

    /**
     * 二维码大小
     */
    private QrCodeSize qrCodeSize;
}