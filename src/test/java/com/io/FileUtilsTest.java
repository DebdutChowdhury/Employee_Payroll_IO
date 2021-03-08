package com.io;

import com.io.FileUtils;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.IntStream;

public class FileUtilsTest {
    private  static String Home = System.getProperty("user.home");
    private  static String PLAY_WITH_NIO = "TempPlayGround";
    @Test
    public void given() throws IOException{
        //check the file exits
        Path homePath = Paths.get(Home);
        Assert.assertTrue(Files.exists(homePath));

        //delete the File and check file not exit
        Path playPath = Paths.get(Home + "/"+PLAY_WITH_NIO);
        if (Files.exists(playPath)) FileUtils.deleteFiles(playPath.toFile());
        Assert.assertTrue(Files.notExists(playPath));

        //create directory
        Files.createDirectory(playPath);
        Assert.assertTrue(Files.exists(playPath));
        IntStream.range(1,10).forEach(cntr -> {
            Path tempFile = Paths.get(playPath + "/temp"+cntr);
            Assert.assertTrue(Files.notExists(tempFile));
            try {
                Files.createFile(tempFile);
            }catch (IOException e){}
            Assert.assertTrue(Files.exists(tempFile));});

        //List Files, Directories as well as files with Extension
        Files.list(playPath).filter(Files::isRegularFile).forEach(System.out::println);
        Files.newDirectoryStream(playPath).forEach(System.out::println);
        Files.newDirectoryStream(playPath, path -> path.toFile().isFile()&&
                path.toString().startsWith("temp"))
                .forEach(System.out::println);
    }
}
