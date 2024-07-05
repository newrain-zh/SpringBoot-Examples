package com.example.qrcode.controller;


import com.example.qrcode.dto.QrcodeDownloadCommonParam;
import com.example.qrcode.service.QrcodeService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

@RestController
public class QrcodeController {

    @Resource
    private QrcodeService qrcodeService;

    @PostMapping("/qrcode/download")
    public void downloadQrcode(@RequestBody QrcodeDownloadCommonParam param, HttpServletResponse response) {
        qrcodeService.downloadQrcode(param, response);
    }
}