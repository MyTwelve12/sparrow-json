package com.sparrowjson.util;

import java.io.*;
import java.util.regex.Pattern;

/**
 * Description:
 * Company    : 上海黄豆网络科技有限公司
 *
 * @author : hll
 * Date       : 2024/11/12
 * Modify     : 修改日期          修改人员        修改说明          JIRA编号
 * v1.0.0       2024/11/12       hll    新增              1001
 ********************************************************************/
public class FileEncodingUtil {


    /**
     * 检测文件编码
     */
    public static String detectFileEncoding(File file) {
        // 优先使用第三方库检测
        try {
            //return UniversalDetector.detectCharset(file);
            return detectEncodingManually(file);
        } catch (Exception e) {
            // 如果第三方库检测失败，使用自己的检测逻辑
            return "UTF-8";
        }
    }

    /**
     * 手动检测文件编码
     */
    private static String detectEncodingManually(File file) throws IOException {
        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file))) {
            byte[] first4Bytes = new byte[4];
            bis.read(first4Bytes, 0, 4);

            // 检测BOM
            if (first4Bytes[0] == (byte) 0xEF && first4Bytes[1] == (byte) 0xBB && first4Bytes[2] == (byte) 0xBF) {
                return "UTF-8";
            }
            if (first4Bytes[0] == (byte) 0xFE && first4Bytes[1] == (byte) 0xFF) {
                return "UTF-16BE";
            }
            if (first4Bytes[0] == (byte) 0xFF && first4Bytes[1] == (byte) 0xFE) {
                return "UTF-16LE";
            }
            if (first4Bytes[0] == (byte) 0x00 && first4Bytes[1] == (byte) 0x00
                    && first4Bytes[2] == (byte) 0xFE && first4Bytes[3] == (byte) 0xFF) {
                return "UTF-32BE";
            }
            if (first4Bytes[0] == (byte) 0xFF && first4Bytes[1] == (byte) 0xFE
                    && first4Bytes[2] == (byte) 0x00 && first4Bytes[3] == (byte) 0x00) {
                return "UTF-32LE";
            }

            // 尝试检测UTF-8
            if (isValidUTF8(file)) {
                return "UTF-8";
            }

            // 默认返回GBK
            return "GBK";
        }
    }

    /**
     * 检查是否为有效的UTF-8文件
     */
    private static boolean isValidUTF8(File file) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"))) {
            char[] buffer = new char[4096];
            while (reader.read(buffer) != -1) {
                for (char c : buffer) {
                    if (c == 0xFFFD) { // 替换字符
                        return false;
                    }
                }
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 读取文件内容
     */
    public static String readFileContent(File file) throws IOException {
        String encoding = detectFileEncoding(file);
        System.out.println("文件编码：" + encoding);

        // 读取文件内容
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), encoding))) {
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
            return content.toString();
        }
    }

    /**
     * 验证文件内容是否有乱码
     */
    public static boolean hasGarbledText(String content) {
        // 检查常见乱码字符
        Pattern pattern = Pattern.compile("[\\u0001-\\u0008\\u000B-\\u000C\\u000E-\\u001F\\uFFFD\\u0000]");
        return pattern.matcher(content).find();
    }


//    public static void main(String[] args) {
//        //String filePath = "D:\\application副本---中文.properties";
//        String filePath = "D:\\中文文档.md";
//        File file = new File(filePath);
//        try {
//            // 读取文件内容
//            String content = FileEncodingUtil.readFileContent(file);
//
//
//            // 检查是否有乱码
//            boolean hasGarbledText = FileEncodingUtil.hasGarbledText(content);
//            System.out.println("文件内容是否有乱码：" + hasGarbledText);
//            if (hasGarbledText) {
//                throw new IllegalArgumentException("文件内容包含乱码，请检查文件编码");
//            }
//
//
//
//        } catch (IOException e) {
//            throw new RuntimeException("文件读取失败: " + e.getMessage(), e);
//        } finally {
//            if (file != null && file.exists()) {
//                //file.delete();
//            }
//        }
//    }
}
