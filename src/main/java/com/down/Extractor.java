package com.down;


import java.io.*;
import java.util.Scanner;

public class Extractor {
    private static boolean debug = false;

    public static void main(String[] args) {
        clean();
        if (wget()) {
            try {
                if (debug) {
                    Scanner sc = new Scanner(System.in);
                    System.out.print("URL Eingeben: ");
                    downloadHTML(sc.next());
                } else {
                    try {
                        if (args[0].equals("--no-ui")) {
                            downloadHTML(args[2]);
                        }
                    } catch (ArrayIndexOutOfBoundsException er) {
                        UI.create();
                    }
                }
            } catch (IllegalArgumentException es) {
                System.out.println("Keine URL angegeben");
            }
        } else {
            if (!debug) {
                new Errors().x001();
            }
        }
    }

    public static void initUI(String urls) {
        if (wget()) {
            Extractor.clean();
            Extractor.downloadHTML(urls);
        } else {
            new Errors().x001();
        }
    }

    public static String getPornName() {
        try {
            File myObj = new File("index.html");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                if (data.contains("<title")) {
                    String title = data.replace("<title>", "").replace("</title>", "").replace(" | Young Gays porn", "");
                    return title;
                }
            }
            myReader.close();
        } catch (FileNotFoundException f) {
            System.err.println("Error");
        }
        return null;
    }
    public static boolean wget() {
        String path = System.getenv().values().toString();
        if(path.toLowerCase().contains("gnuwin32")) {
            return true;
        }else{
            File f = new File("C:\\windows\\system32\\wget.exe");
            if(f.exists()) {
                return true;
            }else{
                return false;
            }
        }
    }
    public static void downloadHTML(String url) {
        try {
            Runtime.getRuntime().exec("wget -O index.html " + url);
            System.out.println("Download Page");
            Thread.sleep(999);
            readPage1();
        }catch (IOException | InterruptedException es) {
            System.err.println("Error");
        }
    }
    public static void readPage1() {
        try {
            File myObj = new File("index.html");
            Scanner myReader = new Scanner(myObj);
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
            System.out.println("Download Embed");
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
                if(data.contains("video_url: ")) {
                    String use = data;
                    String[] json = use.split(",");
                    String conv = json[4].replace("video_url:", "").replace("'", "");
                    String[] split  = conv.split("/?br=");
                    nach =  split[0].replace("/?", "");
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
                String name = getPornName();
                Thread.sleep(0);
                Runtime.getRuntime().exec("cmd /c start cmd.exe /c wget -O \"" + name + ".mp4\" " + url);
            }else{
                Runtime.getRuntime().exec("/bin/bash -c wget -O " + getPornName() + " " + url);
            }
        }catch (IOException | InterruptedException er) {
            System.out.println("Error");
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
