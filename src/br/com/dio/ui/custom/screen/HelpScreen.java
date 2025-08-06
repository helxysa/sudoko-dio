package br.com.dio.ui.custom.screen;

import javax.swing.*;
import javax.swing.border.AbstractBorder;
import java.awt.*;

public class HelpScreen extends JDialog {

    public HelpScreen(JFrame parent) {
        super(parent, "Como Jogar Sudoku", true);
        initializeComponents();
    }
    
    private static class RetroPixelBorder extends AbstractBorder {
        private Color lightColor, darkColor;
        private boolean raised;
        
        public RetroPixelBorder(boolean raised) {
            this.raised = raised;
            this.lightColor = new Color(248, 248, 248);
            this.darkColor = new Color(72, 72, 72);
        }
        
        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
            
            if (raised) {
                g2d.setColor(lightColor);
                g2d.drawLine(x, y, x + width - 2, y);
                g2d.drawLine(x, y, x, y + height - 2);
                g2d.drawLine(x + 1, y + 1, x + width - 3, y + 1);
                g2d.drawLine(x + 1, y + 1, x + 1, y + height - 3);
                
                g2d.setColor(darkColor);
                g2d.drawLine(x + width - 1, y, x + width - 1, y + height - 1);
                g2d.drawLine(x, y + height - 1, x + width - 1, y + height - 1);
                g2d.drawLine(x + width - 2, y + 1, x + width - 2, y + height - 2);
                g2d.drawLine(x + 1, y + height - 2, x + width - 2, y + height - 2);
            } else {
                g2d.setColor(darkColor);
                g2d.drawLine(x, y, x + width - 2, y);
                g2d.drawLine(x, y, x, y + height - 2);
                g2d.drawLine(x + 1, y + 1, x + width - 3, y + 1);
                g2d.drawLine(x + 1, y + 1, x + 1, y + height - 3);
                
                g2d.setColor(lightColor);
                g2d.drawLine(x + width - 1, y, x + width - 1, y + height - 1);
                g2d.drawLine(x, y + height - 1, x + width - 1, y + height - 1);
                g2d.drawLine(x + width - 2, y + 1, x + width - 2, y + height - 2);
                g2d.drawLine(x + 1, y + height - 2, x + width - 2, y + height - 2);
            }
            g2d.dispose();
        }
        
        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(4, 4, 4, 4);
        }
    }

    private void initializeComponents() {
        setSize(480, 580);
        setLocationRelativeTo(getParent());
        setResizable(false);
        
        getContentPane().setBackground(new Color(192, 192, 192));

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(192, 192, 192));
        mainPanel.setBorder(new RetroPixelBorder(true));

        JLabel titleLabel = new JLabel("COMO JOGAR SUDOKU", JLabel.CENTER);
        titleLabel.setFont(new Font("Courier New", Font.BOLD, 16));
        titleLabel.setForeground(new Color(0, 0, 0));
        titleLabel.setBackground(new Color(192, 192, 192));
        titleLabel.setOpaque(true);
        titleLabel.setBorder(BorderFactory.createCompoundBorder(
            new RetroPixelBorder(true),
            BorderFactory.createEmptyBorder(8, 8, 8, 8)
        ));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        JTextArea helpText = new JTextArea();
        helpText.setEditable(false);
        helpText.setLineWrap(true);
        helpText.setWrapStyleWord(true);
        helpText.setFont(new Font("Courier New", Font.PLAIN, 11));
        helpText.setBackground(new Color(248, 248, 248));
        helpText.setForeground(new Color(0, 0, 0));
        helpText.setCaretColor(new Color(0, 0, 0));
        helpText.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        String helpContent = """
                ╔═══════════════════════════════════════╗
                ║           OBJETIVO DO JOGO            ║
                ╚═══════════════════════════════════════╝
                
                • Preencher grade 9x9 com numeros 1-9
                • Seguir as 3 regras do Sudoku
                
                ╔═══════════════════════════════════════╗
                ║            REGRAS BASICAS             ║
                ╚═══════════════════════════════════════╝
                
                [1] REGRA DAS LINHAS:
                    - Cada linha = numeros 1 ate 9
                    - Sem repeticoes na linha
                
                [2] REGRA DAS COLUNAS:
                    - Cada coluna = numeros 1 ate 9  
                    - Sem repeticoes na coluna
                
                [3] REGRA DOS QUADRANTES:
                    - Grade tem 9 quadrantes 3x3
                    - Cada quadrante = numeros 1 ate 9
                    - Sem repeticoes no quadrante
                
                ╔═══════════════════════════════════════╗
                ║             COMO JOGAR                ║
                ╚═══════════════════════════════════════╝
                
                1. Clique na celula vazia
                2. Digite numero de 1 a 9
                3. Use logica para descobrir numero
                4. Respeite sempre as 3 regras
                
                ╔═══════════════════════════════════════╗
                ║          DICAS INICIANTES             ║
                ╚═══════════════════════════════════════╝
                
                • Comece pelos numeros frequentes
                • Procure celulas com 1 opcao
                • Use eliminacao: 8 numeros = sobra 1
                • Tenha paciencia e seja metodico
                
                ╔═══════════════════════════════════════╗
                ║             CONTROLES                 ║
                ╚═══════════════════════════════════════╝
                
                VERIFICAR STATUS - Mostra erros/progresso
                FINALIZAR JOGO   - Confirma se correto  
                REINICIAR        - Limpa tabuleiro
                AJUDA            - Mostra instrucoes
                
                ╔═══════════════════════════════════════╗
                ║            LEMBRE-SE                  ║
                ╚═══════════════════════════════════════╝
                
                Sudoku valido = APENAS UMA solucao!
                
                BOA SORTE E DIVIRTA-SE!
                """;

        helpText.setText(helpContent);

        JScrollPane scrollPane = new JScrollPane(helpText);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getViewport().setBackground(new Color(248, 248, 248));
        scrollPane.setBorder(new RetroPixelBorder(false));
        scrollPane.getVerticalScrollBar().setBackground(new Color(192, 192, 192));
        scrollPane.getVerticalScrollBar().setUI(new javax.swing.plaf.basic.BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = new Color(128, 128, 128);
                this.trackColor = new Color(248, 248, 248);
            }
            @Override
            protected JButton createDecreaseButton(int orientation) {
                JButton btn = new JButton();
                btn.setBackground(new Color(192, 192, 192));
                btn.setForeground(new Color(0, 0, 0));
                btn.setBorder(new RetroPixelBorder(true));
                btn.setPreferredSize(new Dimension(16, 16));
                return btn;
            }
            @Override
            protected JButton createIncreaseButton(int orientation) {
                JButton btn = new JButton();
                btn.setBackground(new Color(192, 192, 192));
                btn.setForeground(new Color(0, 0, 0));
                btn.setBorder(new RetroPixelBorder(true));
                btn.setPreferredSize(new Dimension(16, 16));
                return btn;
            }
        });
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        JButton closeButton = new JButton("FECHAR");
        closeButton.setFont(new Font("Courier New", Font.BOLD, 12));
        closeButton.setBackground(new Color(192, 192, 192));
        closeButton.setForeground(new Color(0, 0, 0));
        closeButton.setBorder(new RetroPixelBorder(true));
        closeButton.setFocusPainted(false);
        closeButton.setPreferredSize(new Dimension(80, 28));
        closeButton.addActionListener(e -> dispose());
        
        closeButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                closeButton.setBorder(new RetroPixelBorder(false));
                closeButton.setBackground(new Color(160, 160, 160));
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                closeButton.setBorder(new RetroPixelBorder(true));
                closeButton.setBackground(new Color(192, 192, 192));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                closeButton.setBorder(new RetroPixelBorder(true));
                closeButton.setBackground(new Color(192, 192, 192));
            }
        });
        
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(new Color(192, 192, 192));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        buttonPanel.add(closeButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    public void showHelp() {
        setVisible(true);
    }
}