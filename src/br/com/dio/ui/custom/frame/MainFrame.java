package br.com.dio.ui.custom.frame;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

public class MainFrame extends JFrame {
    
    private static final Color FRAME_BG = new Color(16, 16, 32);
    private static final Color TITLE_COLOR = new Color(255, 255, 255);
    
    public MainFrame(final Dimension dimension, final JPanel mainPanel) {
        super("◆ SUDOKU 8-BIT ◆");
        
        this.setSize(dimension);
        this.setPreferredSize(dimension);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        
        setupRetroStyle();
        
        this.add(mainPanel);
        this.setVisible(true);
    }
    
    private void setupRetroStyle() {
        this.getContentPane().setBackground(FRAME_BG);
        
        try {
            Font pixelFont = new Font("Courier New", Font.BOLD, 12);
            this.setFont(pixelFont);
        } catch (Exception e) {
            this.setFont(new Font("Monospaced", Font.BOLD, 12));
        }
        
        UIManager.put("Panel.background", FRAME_BG);
        UIManager.put("TextField.background", new Color(64, 64, 96));
        UIManager.put("TextField.foreground", Color.WHITE);
    }
    
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
        
        g2d.setColor(new Color(128, 128, 160));
        g2d.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
        g2d.drawRect(1, 1, getWidth() - 3, getHeight() - 3);
    }
}