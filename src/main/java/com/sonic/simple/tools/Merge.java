package com.sonic.simple.tools;

import com.alibaba.fastjson.JSONObject;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Merge {
    public static void main(String[] args) throws IOException {
        File file = new File("C:\\Users\\ZHOUYIXUN\\Desktop\\MobileModels\\brands");
        File fileb = new File("C:\\Users\\ZHOUYIXUN\\Desktop\\MobileModels\\misc");
        JSONObject s = new JSONObject();
        for (File file1 : file.listFiles()) {
            jsonRead(file1,s);
        }
        for (File file1 : fileb.listFiles()) {
            jsonRead(file1,s);
        }
        File file2 = new File("C:\\Users\\ZHOUYIXUN\\Desktop\\MobileModels\\result.json");
        Writer write = new OutputStreamWriter(new FileOutputStream(file2), StandardCharsets.UTF_8);
        write.write(s.toJSONString());
        write.flush();
        write.close();
    }

    public static void jsonRead(File file,JSONObject s) {
        Scanner scanner = null;
        StringBuilder buffer = new StringBuilder();
        try {
            scanner = new Scanner(file, "utf-8");
            while (scanner.hasNextLine()) {
                String a = scanner.nextLine();
                if (a.contains("`:")) {
                    String head = a.substring(0, a.indexOf(":"));
                    for(String h:head.split(" ")) {
                        s.put(h.replaceAll("`", ""),
                                a.substring(a.indexOf(":") + 2));
                    }
                }
            }
        } catch (Exception e) {

        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }
    }
}
