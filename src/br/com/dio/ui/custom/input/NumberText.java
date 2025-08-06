package br.com.dio.ui.custom.input;

import br.com.dio.model.Space;
import br.com.dio.service.EventEnum;
import br.com.dio.service.EventListener;

import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import static br.com.dio.service.EventEnum.CLEAR_SPACE;
import static java.awt.Font.BOLD;

public class NumberText extends JTextField implements EventListener {

    // Paleta de cores 8-bit
    private static final Color ENABLED_BG = new Color(64, 64, 96);
    private static final Color DISABLED_BG = new Color(96, 32, 32);
    private static final Color TEXT_COLOR = new Color(255, 255, 255);
    private static final Color FIXED_TEXT_COLOR = new Color(255, 255, 128);
    private static final Color BORDER_COLOR = new Color(128, 128, 160);
    private static final Color FOCUS_COLOR = new Color(255, 128, 0);

    private final Space space;
    private boolean isHovered = false;

    public NumberText(final Space space) {
        this.space = space;
        
        var dimension = new Dimension(50, 50);
        this.setSize(dimension);
        this.setPreferredSize(dimension);
        this.setVisible(true);
        
        setupRetroStyle();
        
        this.setDocument(new NumberTextLimit());
        this.setEnabled(!space.isFixed());
        
        if (space.isFixed()) {
            this.setText(space.getActual().toString());
        }

        this.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(final DocumentEvent e) {
                changeSpace();
            }

            @Override
            public void removeUpdate(final DocumentEvent e) {
                changeSpace();
            }

            @Override
            public void changedUpdate(final DocumentEvent e) {
                changeSpace();
            }

            private void changeSpace() {
                if (getText().isEmpty()) {
                    space.clearSpace();
                    return;
                }
                space.setActual(Integer.parseInt(getText()));
            }
        });

        addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                isHovered = true;
                repaint();
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                isHovered = false;
                repaint();
            }
        });
    }

    private void setupRetroStyle() {
        Font pixelFont = new Font("Courier New", BOLD, 24);
        this.setFont(pixelFont);
        
        this.setHorizontalAlignment(CENTER);
        
        if (space.isFixed()) {
            this.setBackground(DISABLED_BG);
            this.setForeground(FIXED_TEXT_COLOR);
        } else {
            this.setBackground(ENABLED_BG);
            this.setForeground(TEXT_COLOR);
        }
        
        Border outerBorder = new LineBorder(BORDER_COLOR, 2, false);
        Border innerBorder = new LineBorder(new Color(32, 32, 48), 1, false);
        this.setBorder(new CompoundBorder(outerBorder, innerBorder));
        
        this.setCaretColor(FOCUS_COLOR);
        this.setSelectionColor(new Color(255, 128, 0, 100));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
        
        if (isHovered && isEnabled()) {
            g2d.setColor(new Color(80, 80, 112));
            g2d.fillRect(0, 0, getWidth(), getHeight());
        }
        
        if (hasFocus() && isEnabled()) {
            g2d.setColor(new Color(255, 128, 0, 50));
            g2d.fillRect(2, 2, getWidth() - 4, getHeight() - 4);
        }
        
        super.paintComponent(g);
        
        if (space.isFixed()) {
            g2d.setColor(new Color(255, 255, 128, 100));
            g2d.fillRect(2, 2, 3, 3);
            g2d.fillRect(getWidth() - 5, 2, 3, 3);
            g2d.fillRect(2, getHeight() - 5, 3, 3);
            g2d.fillRect(getWidth() - 5, getHeight() - 5, 3, 3);
        }
    }

    @Override
    public void update(final EventEnum eventType) {
        if (eventType.equals(CLEAR_SPACE) && (this.isEnabled())) {
            this.setText("");
        }
    }
}