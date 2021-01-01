package com.down;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UI {
    public static void main(String[] args) {
        create();
    }
    public static void create() {
        JFrame f = new JFrame("YoungGaysPorn Downloader");
        JButton b = new JButton("Download");
        final JTextField url = new JTextField(8);
        JPanel p = new JPanel();
        JTextArea info = new JTextArea();
        info.setText("Enter URL then click download");
        f.add(info, BorderLayout.NORTH);
        info.setEditable(false);
        info.setBackground(f.getBackground());
        f.setResizable(false);
        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Extractor.clean();
                String urls = url.getText();
                if(!urls.equals("")) {
                    Extractor.downloadHTML(urls);
                    try {
                        Thread.sleep(999);
                    } catch (InterruptedException interruptedException) {
                        interruptedException.printStackTrace();
                    }
                }
            }
        });
        p.add(b);
        p.add(url);
        f.add(p);
        f.pack();
        f.setVisible(true);
        f.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }
}
