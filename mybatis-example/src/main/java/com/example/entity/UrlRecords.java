package com.example.entity;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * url_records
 * @author 
 */
@Data
public class UrlRecords implements Serializable {
    private Long id;

    private String url;

    private Date createTime;

    private String md5Url;

    private static final long serialVersionUID = 1L;
}