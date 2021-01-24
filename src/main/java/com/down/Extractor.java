package com.down;


import java.io.*;
import java.util.Scanner;

public class Extractor {
    private static boolean debug = true;

    public static void main(String[] args) {
        clean();
        try {
            if (debug) {
                Scanner sc = new Scanner(System.in);
                System.out.print("URL Eingeben: ");
                downloadHTML(sc.next());
            } else {
                try {
                    if (args[0].equals("--no-ui")) {
                        downloadHTML(args[2]);
                    } else {
                        if (args[0].toLowerCase().equals("--debug")) {
                            debug = true;
                            Extractor.main(new String[]{});
                        } else {
                            if (args[0].toLowerCase().equals("--help")) {
                                System.out.println("-- YGPDownloader --\n\nYGPDownload [args]\n\nargs:\n\n--debug (Sets the program to debug mode)\n\n--no-ui [Porn URL] (Run this program from the command prompt)\n\nWritten by Werwolf2303 (Gianluca.B)\n-- YGPDownloader --");
                            } else {
                                System.out.println("-- YGPDownloader --\n\nYGPDownload [args]\n\nargs:\n\n--debug (Sets the program to debug mode)\n\n--no-ui [Porn URL] (Run this program from the command prompt)\n\nWritten by Werwolf2303 (Gianluca.B)\n-- YGPDownloader --");
                            }
                        }
                    }
                } catch (ArrayIndexOutOfBoundsException er) {
                    UI.create();
                }
            }
        } catch (IllegalArgumentException es) {
            System.out.println("Keine URL angegeben");
        }
    }

    public static void initUI(String urls) {
            Extractor.clean();
            Extractor.downloadHTML(urls);
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
                    String[] split2 = split[0].replace("/?", "").replace(" ", "\n").split("\n");
                    nach =  split2[2];
                }
                System.out.print(nach);
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
                String name = getPornName();
                Thread.sleep(0);
                Runtime.getRuntime().exec("/bin/bash -c wget -O \"" + name + "\".mp4 " + url);
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
