package br.com.dio.ui.custom.panel;

import br.com.dio.ui.custom.input.NumberText;

import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.List;

public class SudokuSector extends JPanel {

    // Paleta de cores 8-bit para setores
    private static final Color SECTOR_BG = new Color(48, 48, 80);
    private static final Color BORDER_PRIMARY = new Color(255, 255, 255);
    private static final Color BORDER_SECONDARY = new Color(128, 128, 160);
    private static final Color GRID_COLOR = new Color(64, 64, 96);

    public SudokuSector(final List<NumberText> textFields) {
        var dimension = new Dimension(170, 170);
        this.setSize(dimension);
        this.setPreferredSize(dimension);
        this.setBackground(SECTOR_BG);
        
        this.setLayout(new java.awt.GridLayout(3, 3, 2, 2));
        
        Border outerBorder = new LineBorder(BORDER_PRIMARY, 3, false);
        Border innerBorder = new LineBorder(BORDER_SECONDARY, 1, false);
        this.setBorder(new CompoundBorder(outerBorder, innerBorder));
        
        this.setVisible(true);
        this.setOpaque(true);
        
        textFields.forEach(this::add);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
        
        drawPixelatedGrid(g2d);
        
        drawCornerEffects(g2d);
    }

    private void drawPixelatedGrid(Graphics2D g2d) {
        g2d.setColor(GRID_COLOR);
        
        for (int x = 0; x < getWidth(); x += 56) {
            if (x > 0 && x < getWidth() - 10) {
                g2d.drawLine(x, 5, x, getHeight() - 5);
            }
        }
        
        for (int y = 0; y < getHeight(); y += 56) {
            if (y > 0 && y < getHeight() - 10) {
                g2d.drawLine(5, y, getWidth() - 5, y);
            }
        }
    }

    private void drawCornerEffects(Graphics2D g2d) {
        g2d.setColor(new Color(255, 255, 255, 150));
        
        g2d.fillRect(4, 4, 2, 2);
        g2d.fillRect(4, 7, 2, 2);
        g2d.fillRect(7, 4, 2, 2);
        
        g2d.fillRect(getWidth() - 6, 4, 2, 2);
        g2d.fillRect(getWidth() - 9, 4, 2, 2);
        g2d.fillRect(getWidth() - 6, 7, 2, 2);
        
        g2d.fillRect(4, getHeight() - 6, 2, 2);
        g2d.fillRect(7, getHeight() - 6, 2, 2);
        g2d.fillRect(4, getHeight() - 9, 2, 2);
        
        g2d.fillRect(getWidth() - 6, getHeight() - 6, 2, 2);
        g2d.fillRect(getWidth() - 9, getHeight() - 6, 2, 2);
        g2d.fillRect(getWidth() - 6, getHeight() - 9, 2, 2);
        
        g2d.setColor(new Color(128, 128, 160, 30));
        for (int y = 0; y < getHeight(); y += 4) {
            g2d.drawLine(0, y, getWidth(), y);
        }
    }
}