package com.down;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import sun.nio.ch.FileKey;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class Extractor {
    private static boolean debug = false;
    public static void main(String[] args) throws InterruptedException {
        clean();
        try {
            if (debug) {
                Scanner sc = new Scanner(System.in);    //System.in is a standard input stream
                System.out.print("URL Eingeben: ");
                downloadHTML(sc.next());
            } else {
                try {
                    if (args[0].equals("--no-ui")) {
                        downloadHTML(args[1]);
                    }
                }catch (ArrayIndexOutOfBoundsException er) {
                    UI.create();
                }
            }
        }catch (IllegalArgumentException es) {
            System.out.println("Keine URL angegeben");
        }
    }
    public static void downloadHTML(String url) {
        try {
            Runtime.getRuntime().exec("wget -O index.html " + url);
            Thread.sleep(999);
            readPage1();
        }catch (IOException | InterruptedException es) {
            System.err.println("Error");
        }
    }
    public static void readPage1() {
        //<iframe width="640" height="385" src=
        try {
            File myObj = new File("index.html");
            Scanner myReader = new Scanner(myObj);
            System.out.println("Download Embed");
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                if(data.contains("<iframe width=")) {
                    String[] wand1 = data.split(" ");
                    String nach = wand1[3].replace("\"","").replace("?skin=black&amp;autoplay=false", "").replace("src=", "");
                    page2(nach);
                }
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    public static void page2(String url) {
        try {
            Runtime.getRuntime().exec("wget -O video.html " + url);
            Thread.sleep(999);
            read2();
        }catch (IOException | InterruptedException ess) {
            System.err.println("Error");
        }
    }
    public static void read2() {
        try {
            File myObj = new File("video.html");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String nach = "";
                String title = "";
                if(data.contains("video_url: ")) {
                    String use = data;
                    String[] json = use.split(",");
                    String conv = json[4].replace("video_url:", "").replace("'", "");
                    String[] split  = conv.split("/?br=");
                    nach =  split[0].replace("/?", "");
                    System.out.println(nach);
                }
                downloadmp4(nach);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    public static void downloadmp4(String url) {
        try {
            String OS = System.getProperty("os.name").toLowerCase();
            if(OS.contains("windows")) {
                Runtime.getRuntime().exec("cmd /c start cmd.exe /c wget " + url);
            }else{
                Runtime.getRuntime().exec("/bin/bash -c wget " + url);
            }
        }catch (IOException er) {

        }
    }
    public static void clean() {
        //Delete cache and make sure that all files deleted
        File html = new File("index.html");
        File video = new File("video.html");
        html.delete();
        video.delete();
    }
}