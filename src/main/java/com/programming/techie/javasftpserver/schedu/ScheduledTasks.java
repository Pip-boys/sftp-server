package com.programming.techie.javasftpserver.schedu;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class ScheduledTasks {

    @Value("${sftp.rootDir}")
    private String rootDir;

    @Value("${sftp.ftpDir}")
    private String ftpDir;

    @Scheduled(fixedRate = 5000)
    public void runEveryFiveSeconds() {
        Path dir = Paths.get(rootDir); // 替换为你的目录路径
        try {
            Files.list(dir).forEach(path -> {
                if (Files.isRegularFile(path)) {
                    File file = path.toFile();
                    if (file.isDirectory()){
                        return;
                    }
                    String fileAbPath = file.toString();
                    File okFile = new File(fileAbPath + ".ok");
                    if (okFile.exists()){
                        System.out.println("转移文件...");
                        Path source = Paths.get(rootDir+"/"+file.getName()); // 源文件路径
                        Path target = Paths.get(ftpDir+"/"+file.getName()); // 目标文件路径
                        try {
                            Files.move(source, target);
                            Files.delete(Paths.get(fileAbPath + ".ok"));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("This method is called every 5 seconds");
    }

    public static void main(String[] args) throws IOException {
//        Path path = Paths.get("D:\\subject");
//        Files.list(path).forEach(paths -> {
//            File file = paths.toFile();
//            System.out.println(file.toString());
//        });
//        File file = path.toFile();
//        System.out.println(file.exists());
//        Path fileName = path.getFileName();
//        System.out.println(fileName.toString());
//        System.out.println(fileName.toString().endsWith(".ok"));

        Path dir = Paths.get("D:\\subject"); // 替换为你的目录路径

        try {
            Files.list(dir).forEach(path -> {
                if (Files.isRegularFile(path)) {
                    File file = path.toFile();
                    if (file.isDirectory()){
                        return;
                    }
                    String name = file.toString();
                    if (new File(name+".ok").exists()){
                        System.out.println(true);
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}