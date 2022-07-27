package com.cjh.oop.string_buffer_build;

import java.io.*;
@SuppressWarnings("all") // �������еľ���
/**
 * @ClassName IOTest
 * @Description
 * @Author Administrator
 * @Date 2022/7/22 9:55
 * @Version 1.0
 */
public class IOTest {
    public static void main(String[] args) {
        FileInputStream fis = null; // ������
        FileOutputStream fos = null; // �����

        File srcFile = new File(new File(".."), "�������.md");
        File destFile = new File(new File(".."), "�������1.md");
        try {
            fis = new FileInputStream(srcFile);
            fos = new FileOutputStream(destFile);
            int len;
            while ((len =  fis.read()) != -1) {
                fos.write(len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
