package com.example.controller;

import com.example.common.Result;
import com.example.entity.UrlRecords;
import com.example.mapper.UrlRecordsMapper;
import com.example.utils.Md5Utils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.util.Date;

/**
 * @author newRain
 */
@RestController
@RequestMapping("/add")
public class UrlController {

    @Resource
    private UrlRecordsMapper urlRecordsMapper;

    @GetMapping("/url")
    public Result<Boolean> add(@RequestParam("go") String url) throws UnsupportedEncodingException {
        UrlRecords urlRecords = new UrlRecords();
        urlRecords.setUrl(url);
        urlRecords.setCreateTime(new Date());
        urlRecords.setMd5Url(Md5Utils.EncoderByMd5(url));
        int insert = urlRecordsMapper.insert(urlRecords);
        return Result.success(insert > 0);
    }
}