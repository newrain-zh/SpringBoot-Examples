package com.example.qrcode.service;

import cn.hutool.core.date.DateUtil;
import com.beust.jcommander.internal.Lists;
import com.example.qrcode.dto.QrCodeSize;
import com.example.qrcode.dto.QrcodeDownloadCommonParam;
import com.example.qrcode.dto.QrcodeFormat;
import com.example.qrcode.utils.PackUtils;
import com.example.qrcode.utils.QrcodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.net.URLEncoder;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.example.qrcode.utils.QrcodeUtils.SMALL;

@Slf4j
@Service
public class QrcodeService {


    public void downloadQrcode(QrcodeDownloadCommonParam dto, HttpServletResponse httpServletResponse) {
        String tempBasePath = "./" + File.separator + UUID.randomUUID() + File.separator;
        // 查询投放信息,用于生成文件名,已经校验过只能下载单个投放下二维码
        String nowDate = DateUtil.format(new Date(), "yyyy-MM-dd");
        List<QrcodeUtils.QrcodeParam> qrcodeParams = getQrcodeParams(dto, tempBasePath, nowDate);
        QrcodeUtils.generateQrcodeImages(qrcodeParams);
        try {
            startDownload(tempBasePath, nowDate,httpServletResponse);
        } catch (Exception e) {
            log.error("downloadQrcode error", e);
            throw new RuntimeException("二维码打包/上传失败");
        }
    }

    private void startDownload(String basePath, String date, HttpServletResponse response) throws Exception {
        // 多文件打包后上传,单文件直接上传
        File dir = new File(basePath);
        if (!dir.isDirectory()) {
            throw new RuntimeException("基础目录不是文件夹");
        }
        File[] files = dir.listFiles();
        if (files == null || files.length == 0) {
            throw new RuntimeException("基础目录下没有待处理问卷");
        }
        List<File> fileList = Lists.newArrayList(files);
        fileList = fileList.stream().filter(File::isFile).collect(Collectors.toList());
        File toUpload;
        if (fileList.size() == 1) {
            toUpload = fileList.get(0);
        } else {
            // 打包
            Map<String, String> filePaths = new HashMap<>();
            fileList.forEach(file -> filePaths.put(file.getName(), file.getAbsolutePath()));
            // 投放名称 问卷名称 YYYYMMDD
            String zipFileName = "二维码";
            PackUtils.packZip(filePaths, basePath, zipFileName, false);
            toUpload = new File(basePath + zipFileName);
        }
        try {
            response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode("二维码下载.zip", "UTF-8"));
            FileInputStream fileInputStream = new FileInputStream(toUpload);
            // 文件上传
            MultipartFile multipartFile = PackUtils.toMultipartFile(fileInputStream, toUpload.getName());
            ServletOutputStream outputStream = response.getOutputStream();
            outputStream.write(multipartFile.getBytes());
            outputStream.close();
        } catch (Exception e) {
            log.error("上传oss失败:", e);
        }
    }

    private List<QrcodeUtils.QrcodeParam> getQrcodeParams(QrcodeDownloadCommonParam param, String basePath, String nowDate) {
        Boolean downloadAll = param.getDownloadAll();
        QrcodeFormat qrcodeFormat = param.getQrcodeFormat();
        QrCodeSize qrCodeSize = param.getQrCodeSize();
        List<QrcodeUtils.QrcodeParam> qrcodeParams = new ArrayList<>();
        Map<String, Integer> indexMap = new HashMap<>();
        if (downloadAll) {
            // 全格式，全尺寸生成
            getQrcodeUrl().forEach(url -> {
                qrcodeParams.add(new QrcodeUtils.QrcodeParam(url, SMALL, SMALL, basePath, getFileName(indexMap, nowDate, QrcodeFormat.SVG), QrcodeFormat.SVG));
                qrcodeParams.add(new QrcodeUtils.QrcodeParam(url, SMALL, SMALL, basePath, getFileName(indexMap, nowDate, QrcodeFormat.PDF), QrcodeFormat.PDF));
                qrcodeParams.add(new QrcodeUtils.QrcodeParam(url, SMALL, SMALL, basePath, getFileName(indexMap, nowDate, QrcodeFormat.EPS), QrcodeFormat.EPS));
                qrcodeParams.add(new QrcodeUtils.QrcodeParam(url, SMALL, SMALL, basePath, getFileName(indexMap, nowDate, QrcodeFormat.PNG), QrcodeFormat.PNG));
                qrcodeParams.add(new QrcodeUtils.QrcodeParam(url, QrcodeUtils.MIDDLE, QrcodeUtils.MIDDLE, basePath, getFileName(indexMap, nowDate, QrcodeFormat.PNG), QrcodeFormat.PNG));
                qrcodeParams.add(new QrcodeUtils.QrcodeParam(url, QrcodeUtils.LARGE, QrcodeUtils.LARGE, basePath, getFileName(indexMap, nowDate, QrcodeFormat.PNG), QrcodeFormat.PNG));
            });

        } else {
            if (qrcodeFormat.isVector()) {
                // 矢量图
                getQrcodeUrl().forEach(url -> {
                    qrcodeParams.add(new QrcodeUtils.QrcodeParam(url, SMALL, SMALL, basePath, getFileName(indexMap, nowDate, qrcodeFormat), qrcodeFormat));
                });

            } else {
                getQrcodeUrl().forEach(url -> {
                    qrcodeParams.add(new QrcodeUtils.QrcodeParam(url, qrCodeSize.getWidth(), qrCodeSize.getLength(), basePath, getFileName(indexMap, nowDate, qrcodeFormat), qrcodeFormat));
                });

            }
        }
        return qrcodeParams;
    }


    private String getFileName(Map<String, Integer> indexMap, String date, QrcodeFormat format) {
        // 客户/渠道/员工/组织结构名称 问卷名称 YYYYMMDD 01
        StringBuilder fileName = new StringBuilder();
        fileName.append(date).append(" ");
        Integer index = indexMap.get(fileName + format.getFormat());
        if (index == null) {
            index = 0;
        }
        indexMap.put(fileName + format.getFormat(), index + 1);
        fileName.append(index + 1).append(format.getFormat());
        return fileName.toString();
    }

    public List<String> getQrcodeUrl() {
        return Stream.of("1", "2", "3").collect(Collectors.toList());
    }

}