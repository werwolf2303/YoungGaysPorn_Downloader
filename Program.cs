﻿using System;
using System.Net;
using System.IO;
using System.Text;
using System.Collections.Specialized;

namespace YGPDownloader
{
    class Program
    {
        static bool final = false;
        static void Main(string[] args)
        {
            Console.WriteLine("Enter URL:");
            string userName = Console.ReadLine();
            download(userName, "index.html");
            rIndex();
            rVideo();
        }
        static void rVideo()
        {
            const Int32 BufferSize = 128;
            using (var fileStream = File.OpenRead("video.html"))
            using (var streamReader = new StreamReader(fileStream, Encoding.UTF8, true, BufferSize))
            {
                String line;
                while ((line = streamReader.ReadLine()) != null)
                {
                    if (line.Contains("video_url: "))
                    {
                        String use = line;
                        String[] json = use.Split(",");
                        String conv = json[4].Replace("video_url:", "").Replace("'", "");
                        String[] split = conv.Split("/?br=");
                        String[] split2 = split[0].Replace("/?", "").Replace(" ", "\n").Split("\n");
                        final = true;
                        download(split2[2], "Porn.mp4");
                    }
                }
            }
        }
        static void rIndex()
        {
            const Int32 BufferSize = 128;
            using (var fileStream = File.OpenRead("index.html"))
            using (var streamReader = new StreamReader(fileStream, Encoding.UTF8, true, BufferSize))
            {
                String line;
                while ((line = streamReader.ReadLine()) != null)
                {
                    if (line.Contains("<iframe width="))
                    {
                        String[] wand1 = line.Split(" ");
                        String nach = wand1[3].Replace("\"", "").Replace("?skin=black&amp;autoplay=false", "").Replace("src=", "");
                        download(nach, "video.html");
                    }
                }
            }
        }
        static String getPornName()
        {
            const Int32 BufferSize = 128;
            using (var fileStream = File.OpenRead("index.html"))
            using (var streamReader = new StreamReader(fileStream, Encoding.UTF8, true, BufferSize))
            {
                String line;
                while ((line = streamReader.ReadLine()) != null)
                {
                    if (line.Contains("<title"))
                    {
                        String title = line.Replace("<title>", "").Replace("</title>", "").Replace(" | Young Gays porn", "");
                        return title;
                    }
                }
            }
            return null;
        }
        static void download(String url, String saveAs)
        {
            if (final)
            {
                using (WebClient web1 = new WebClient())
                {
                    Console.WriteLine("Downloading... (Please wait a moment)");
                    web1.DownloadFile(url,getPornName() + ".mp4");
                }
                Console.WriteLine("Download Complete");
            }
            else
            {
                using (WebClient web1 = new WebClient())
                {
                    Console.WriteLine("Downloading... (Please wait a moment)");
                    web1.DownloadFile(url, saveAs);
                }
                Console.WriteLine("Download Complete");
            }
        }
    }
}