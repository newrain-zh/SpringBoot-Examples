package com.example.qrcode.dto;

/**
 * 二维码格式
 *
 * @author tfji
 * @date 2023/1/9 16:24
 */
public enum QrcodeFormat {
    /**
     * 二维码格式
     */
    PNG(false, ".png"),
    PDF(true, ".pdf"),
    SVG(true, ".svg"),
    EPS(true, ".eps");

    private boolean vector;
    private String format;

    QrcodeFormat(boolean vector, String format) {
        this.vector = vector;
        this.format = format;
    }

    public void setVector(boolean vector) {
        this.vector = vector;
    }

    public boolean isVector() {
        return vector;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }
}