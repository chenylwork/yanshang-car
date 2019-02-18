package com.yanshang.car.commons;

import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;

/*
 * @ClassName FileUtil
 * @Description 文件操作工具类
 * @Author 陈彦磊
 * @Date 2019/1/21- 11:56
 * @Version 1.0
 **/
public class FileUtil {
    /**
     * 创建文件对象，若附文件夹不存在创建
     * @param path 文件路径
     * @return
     */
    public static File createFile(String path) {
        File file = new File(path);
        File parentFile = file.getParentFile();
        if (!parentFile.exists()) parentFile.mkdirs();
        return file;
    }

    /**
     * 删除文件夹，递归删除
     * @param dirFile
     * @return
     */
    public static boolean deleteFile(File dirFile) {
        // 如果dir对应的文件不存在，则退出
        if (!dirFile.exists()) {
            return false;
        }

        if (dirFile.isFile()) {
            return dirFile.delete();
        } else {

            for (File file : dirFile.listFiles()) {
                deleteFile(file);
            }
        }

        return dirFile.delete();
    }

    /**
     * 删除文件
     * @param path
     */
    public static void removeFile(String path) {
        File file = new File(path);
        if (file.exists()) file.delete();
    }

    public static void removeFile(File file) {
        if (file.exists()) file.delete();
    }

    public static void removeFiles(List<File> files) {
        if (files == null && files.isEmpty()) return;
        for (File file : files) removeFile(file);
    }
    public static void removeFilesByPath(List<String> files) {
        if (files == null && files.isEmpty()) return;
        for (String filePath : files) removeFile(filePath);
    }


    /**
     * 判断文件是否是图片
     * @param file
     * @return
     */
    public static boolean isImage(MultipartFile file) {
        if (file == null) return false;
        try {
            if (ImageIO.read(file.getInputStream()) == null) return false;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }
    /**
     * 判断文件是否是图片
     * @param files
     * @return
     */
    public static boolean isImage(MultipartFile... files){
        for(MultipartFile file : files) {
            if (!isImage(file)) return false;
        }
        return true;
    }
    /**
     * 获取文件名称
     * @param file
     * @param name
     * @return
     */
    public static String getFileName(MultipartFile file,String name) {
        if (file.getOriginalFilename().lastIndexOf(".") == -1);
        String originalFilename = file.getOriginalFilename();
        int i = originalFilename.lastIndexOf(".");
        String suffix = (i == -1) ? "" : originalFilename.substring(i);
        String fileName = name+suffix;
        return fileName;
    }
    public static String getFileName(MultipartFile file){
        return getFileName(file,"");
    }

    /**
     * 判断两个文件是否相同
     * 1.判断两者是否为空的情况
     * 2.判断两者的长度是否相等
     * 3.判断两者的文件后缀是否相等
     * 4.随机收取文件内容段判断是否相等——》 待实现
     * @param file1 需要对比的文件1
     * @param file2 需要对比的文件2
     * @return
     */
    public static boolean fileEqual(File file1,File file2) {
        if (file1 == null || file2 == null) return file1 == file2;
        if (file1.length() != file2.length()) return false;
        Pattern pattern = Pattern.compile("/*.");
        String name1 = file1.getName();
        String name2 = file2.getName();
        int a,b=0;
        String suffix1 = ((a = name1.indexOf(".")) == -1) ? "" : name1.substring(a,name1.length());
        String suffix2 = ((b = name2.indexOf(".")) == -1) ? "" : name2.substring(b,name2.length());
        if (!suffix1.equals(suffix2)) return false;
        /*待实现*/
//        byte[] buffer1 = new byte[64];
//        byte[] buffer2 = new byte[64];
//        int stat = 0,end = 0;
//        long length = file1.length();
//        Random random = new Random(file1.length());
//        try {
//            FileInputStream fileInputStream1 = new FileInputStream(file1);
//            FileInputStream fileInputStream2 = new FileInputStream(file2);
//            file1.length();
//            for (int i=0; i<3; i++) {
//                while(true) {
//                    long start = random.nextLong();
////                    if (start >= (length - CHECK_FILE_BUFFER_LENGTH)) end = length;
//                    break;
//                }
//                fileInputStream1.read(buffer1,stat,end);
//                fileInputStream2.read(buffer2,stat,end);
//                if (!Arrays.equals(buffer1,buffer2)) return false;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        /*待实现*/
        return true;
    }
    private static final int CHECK_FILE_BUFFER_LENGTH = 64;


}
