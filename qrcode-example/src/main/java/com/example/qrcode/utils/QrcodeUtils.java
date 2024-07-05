package com.example.qrcode.utils;

import cn.hutool.core.collection.CollUtil;
import com.example.qrcode.dto.QrcodeFormat;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;
import de.erichseifert.vectorgraphics2d.EPSGraphics2D;
import de.erichseifert.vectorgraphics2d.PDFGraphics2D;
import de.erichseifert.vectorgraphics2d.SVGGraphics2D;
import de.erichseifert.vectorgraphics2d.VectorGraphics2D;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * 二维码处理工具
 * <p>
 * 多线程批量生成,提升速度
 *
 * @author tfji
 * @date 2023/1/12 16:44
 */
@Slf4j
public class QrcodeUtils {

    public static final Integer LARGE = 1000;

    public static final Integer MIDDLE = 500;

    public static final Integer SMALL = 300;

    private static final ThreadPoolExecutor QRCODE_EXECUTOR = new ThreadPoolExecutor(2, 4,
            1, TimeUnit.MINUTES, new LinkedBlockingQueue<>(10),
            new ThreadFactory() {
                private final AtomicInteger atomicInteger = new AtomicInteger(0);

                @Override
                public Thread newThread(@Nullable @NotNull Runnable runnable) {
                    return new Thread(runnable, "qrcodeExecutor" + "-" + atomicInteger.incrementAndGet());
                }
            }, new ThreadPoolExecutor.CallerRunsPolicy());

    /**
     * 多线程批量生成二维码
     * 会等待所有二维码生成完成
     *
     * @param params 生成二维码参数列表
     */
    public static void generateQrcodeImages(List<QrcodeParam> params) {
        if (CollUtil.isEmpty(params)) {
            return;
        }

        // 先将filePath统一创建好
        Set<String> allPath = params.stream().map(QrcodeParam::getFilePath).collect(Collectors.toSet());
        allPath.forEach(path -> {
            File dir = new File(path);
            boolean mkdir = dir.mkdirs();
            if (!mkdir) {
                throw new RuntimeException("创建目录失败");
            }
        });

        List<CompletableFuture<Void>> futures = new ArrayList<>();
        params.forEach(param -> futures.add(CompletableFuture.runAsync(() -> generateQrcodeImage(param), QRCODE_EXECUTOR)));

        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
    }

    /**
     * 生成二维码
     *
     * @param param 生成二维码参数
     *              filePath需要存在,不会主动创建
     */
    public static void generateQrcodeImage(QrcodeParam param) {
        int width = param.getWidth();
        int height = param.getLength();
        String filePath = param.getFilePath();
        String fileName = param.getFileName();
        String wholeFilePath = filePath + fileName;
        QrcodeFormat qrcodeFormat = param.getQrcodeFormat();
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        try {
            BitMatrix bitMatrix = qrCodeWriter.encode(param.getText(), BarcodeFormat.QR_CODE, width, height);
            if (qrcodeFormat.isVector()) {
                switch (qrcodeFormat) {
                    case SVG:
                        generateSvg(width, height, bitMatrix, wholeFilePath);
                        break;
                    case EPS:
                        generateEps(width, height, bitMatrix, wholeFilePath);
                        break;
                    case PDF:
                        generatePdf(width, height, bitMatrix, wholeFilePath);
                        break;
                    default:
                        break;
                }
            } else {
                Path path = FileSystems.getDefault().getPath(wholeFilePath);
                MatrixToImageWriter.writeToPath(bitMatrix, qrcodeFormat.name(), path);
            }
        } catch (Exception e) {
            log.error("QrcodeUtils generateQrcodeImage error param : {}", param, e);
        }
    }

    /**
     * 生成SVG格式二维码
     */
    private static void generateSvg(int width, int height, BitMatrix bitMatrix, String wholeFilePath) throws IOException {
        SVGGraphics2D svgGraphics2D = new SVGGraphics2D(0, 0, width, height);
        fill2VectorLine(svgGraphics2D, bitMatrix);
        writeBytesToFile(wholeFilePath, svgGraphics2D.getBytes());
    }

    /**
     * 生成EPS格式二维码
     */
    private static void generateEps(int width, int height, BitMatrix bitMatrix, String wholeFilePath) throws IOException {
        EPSGraphics2D epsGraphics2D = new EPSGraphics2D(0, 0, width, height);
        fill2VectorLine(epsGraphics2D, bitMatrix);
        writeBytesToFile(wholeFilePath, epsGraphics2D.getBytes());
    }

    /**
     * 生成PDF二维码
     */
    private static void generatePdf(int width, int height, BitMatrix bitMatrix, String wholeFilePath) throws IOException {
        PDFGraphics2D pdfGraphics2D = new PDFGraphics2D(0, 0, width, height);
        fill2VectorLine(pdfGraphics2D, bitMatrix);
        writeBytesToFile(wholeFilePath, pdfGraphics2D.getBytes());
    }

    private static void writeBytesToFile(String wholeFilePath, byte[] bytes) throws IOException {
        File file = new File(wholeFilePath);
        if (!file.exists()) {
            boolean newFile = file.createNewFile();
            if (!newFile) {
                throw new RuntimeException("创建文件失败");
            }
        }

        FileUtils.writeByteArrayToFile(file, bytes);
    }

    private static void fill2VectorLine(VectorGraphics2D vectorGraphics2D, BitMatrix bitMatrix) {
        if (vectorGraphics2D == null || bitMatrix == null) {
            return;
        }
        // 256 和32是有区别的,
        double width = bitMatrix.getWidth();
        double height = bitMatrix.getHeight();

        for (int x = 0; x < width; x++) {
            List<String> temp = new ArrayList<>();
            int jsq = 0;
            int prev = -1;
            for (int y = 0; y < height; y++) {

                if (bitMatrix.get(x, y)) {
                    if (prev == -1) {
                        jsq++;
                        prev = y;
                        continue;
                    }
                    if (1 == y - prev) {
                        // 判断是否是连续的 下一个-上一个=1
                        jsq++;
                    } else {
                        temp.add(String.format("a:%s->%s", (y - jsq), (y)));
                        // 把上一步的图形输出出去
                        drawLine(vectorGraphics2D, x, (y - jsq), x, y);
                        jsq = 0;
                    }
                    prev = y;
                } else {
                    if (prev >= 0) {
                        int y1 = (prev - jsq + 1);
                        int y2 = prev;
                        temp.add(String.format("b:%s->%s", (y1), (y2)));
                        // 把之前的输出
                        if (y1 == y2) {
                            vectorGraphics2D.fillRect(x, y1, 1, 1);
                        } else {
                            drawLine(vectorGraphics2D, x, y1, x, y2);
                        }
                        jsq = 0;
                        prev = -1;
                    }
                }
            }
            if (jsq > 0) {
                // 把剩余的输出
                drawLine(vectorGraphics2D, x, prev, x, (prev + jsq - 1));
                temp.add(String.format("c:%s->%s", (prev), (prev + jsq - 1)));
            }
        }
    }

    private static void drawLine(VectorGraphics2D vectorGraphics2D, int x1, int y1, int x2, int y2) {
        java.awt.Rectangle s = new java.awt.Rectangle(x1, y1, 1, (y2 - y1 + 1));
        vectorGraphics2D.fill(s);
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class QrcodeParam implements Serializable {
        private String text;
        private int width;
        private int length;
        private String filePath;
        private String fileName;
        private QrcodeFormat qrcodeFormat;
    }
}