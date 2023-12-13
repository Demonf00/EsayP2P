package com.esay.frame;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class EsayFrame extends JFrame {
    public EsayFrame () {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setTitle("Dummy frame.");
        this.setSize(1000, 700);
        this.setResizable(false);
        this.setIconImage(new ImageIcon("anya.png").getImage());
    }
}
