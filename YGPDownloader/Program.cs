using System;
using System.Net;
using System.IO;
using System.Text;
using System.Collections.Specialized;

namespace YGPDownloader
{
    class Program
    {
        static void Main(string[] args)
        {
            Console.WriteLine("Enter URL:");
            string userName = Console.ReadLine();
            download(userName, "index.html");
            rIndex();
            rVideo();
        }
        static void download(String url, String saveAs)
        {
            Console.WriteLine("Downloading...");
            using (WebClient web1 = new WebClient())
                web1.DownloadFile(url, saveAs);
            Console.WriteLine("Download Complete");
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
                        download(split2[2], "Porn.mp4");
                    }
                    }
                }
        }
    }
}
