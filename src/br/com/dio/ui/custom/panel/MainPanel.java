package br.com.dio.ui.custom.panel;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

public class MainPanel extends JPanel {
    
    private static final Color BACKGROUND_COLOR = new Color(32, 32, 64);
    private static final Color BORDER_COLOR = new Color(255, 255, 255);
    
    public MainPanel(final Dimension dimension) {
        this.setSize(dimension);
        this.setPreferredSize(dimension);
        this.setBackground(BACKGROUND_COLOR);
        this.setOpaque(true);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
        
        g2d.setColor(new Color(48, 48, 80));
        for (int x = 0; x < getWidth(); x += 20) {
            for (int y = 0; y < getHeight(); y += 20) {
                if ((x / 20 + y / 20) % 2 == 0) {
                    g2d.fillRect(x, y, 20, 20);
                }
            }
        }
        
        g2d.setColor(BORDER_COLOR);
        g2d.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
        g2d.drawRect(1, 1, getWidth() - 3, getHeight() - 3);
    }
}