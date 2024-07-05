package com.example.qrcode.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.io.IOUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author tfji
 */
@Slf4j
public class PackUtils {

    /**
     * 打包文件
     *
     * @param filePaths 文件路径
     *                  key -> 文件名
     *                  value->文件路径
     * @throws Exception 输出流为null或者打包文件路径为空
     */
    public static void packZip(Map<String, String> filePaths, String zipFilePath, String zipFileName, Boolean fromOnline) throws Exception {
        if (filePaths == null|| filePaths.isEmpty()) {
            throw new Exception("输出流为null或者打包文件路径为空");
        }

        File zipFile = new File(zipFilePath);
        if (!zipFile.exists()) {
            boolean mkdir = zipFile.mkdirs();
            if (!mkdir){
                throw new RuntimeException("创建目录失败");
            }
        }
        FileOutputStream fileOutputStream = new FileOutputStream(zipFilePath + zipFileName);
        ZipOutputStream zipOutputStream = new ZipOutputStream(fileOutputStream);
        try {
            for (Map.Entry<String, String> entry : filePaths.entrySet()) {
                String fineName = entry.getKey();
                String filePath = entry.getValue();
                writeFileToZip(fineName, filePath, zipOutputStream, fromOnline);
            }
        } catch (Exception e) {
            log.error("write zip error", e);
        } finally {
            fileOutputStream.flush();
            zipOutputStream.flush();
            zipOutputStream.close();
            fileOutputStream.close();
        }
    }

    public static void writeFileToOutputStream(String filePath, OutputStream outputStream) throws Exception {
        if (outputStream == null) {
            throw new Exception("输出流为null或者文件路径为空");
        }
        InputStream stream = null;
        try {
            URL httpUrl = new URL(filePath);
            stream = httpUrl.openStream();
            IOUtils.copy(stream, outputStream);
        } catch (Exception e) {
            log.error("outputStream write error", e);
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (Exception e) {
                    log.error("InputStream close error", e);
                }
            }
        }
    }

    public static MultipartFile toMultipartFile(InputStream inputStream, String fileName) throws IOException {
        OutputStream outputStream = null;

        try {
            File file = new File(UUID.randomUUID() + fileName);
            int binarySize = inputStream.available();
            FileItem fileItem = new DiskFileItem(null, null, false, fileName, binarySize, file);
            outputStream = fileItem.getOutputStream();
            IOUtils.copy(inputStream, outputStream);
            outputStream.flush();
            return new CommonsMultipartFile(fileItem);
        } finally {
            inputStream.close();
            if (outputStream != null) {
                outputStream.close();
            }
        }
    }

    private static void writeFileToZip(String fileName, String filePath, ZipOutputStream zipOutputStream, Boolean fromOnline) {
        InputStream stream = null;
        try {
            if (fromOnline) {
                URL httpUrl = new URL(filePath);
                stream = httpUrl.openStream();
            } else {
                File file = new File(filePath);
                stream = Files.newInputStream(file.toPath());
            }
            zipOutputStream.putNextEntry(new ZipEntry(fileName));
            IOUtils.copy(stream, zipOutputStream);
        } catch (Exception e) {
            log.error("zipOutputStream write error", e);
        } finally {
            if (zipOutputStream != null) {
                try {
                    zipOutputStream.closeEntry();
                } catch (Exception e) {
                    log.error("zipOutputStream closeEntry error", e);
                }
            }

            if (stream != null) {
                try {
                    stream.close();
                } catch (Exception e) {
                    log.error("InputStream close error", e);
                }
            }
        }
    }

    public static String configHtml(String htmlStr) {
        String regEx_script = "<script[^>]*?>[\\s\\S]*?<\\/script>"; //定义script的正则表达式
        String regEx_style = "<style[^>]*?>[\\s\\S]*?<\\/style>"; //定义style的正则表达式
        String regEx_html = "<[^>]+>"; //定义HTML标签的正则表达式

        Pattern p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
        Matcher m_script = p_script.matcher(htmlStr);
        htmlStr = m_script.replaceAll(""); //过滤script标签

        Pattern p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
        Matcher m_style = p_style.matcher(htmlStr);
        htmlStr = m_style.replaceAll(""); //过滤style标签

        Pattern p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
        Matcher m_html = p_html.matcher(htmlStr);
        htmlStr = m_html.replaceAll(""); //过滤html标签

        return htmlStr.trim(); //返回文本字符串
    }


}