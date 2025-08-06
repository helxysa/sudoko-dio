package br.com.dio.ui.custom.button;

import javax.swing.JButton;
import java.awt.event.ActionListener;

public class HelpButton extends JButton {

    public HelpButton(final ActionListener actionListener) {
        super("Ajuda");
        this.addActionListener(actionListener);
    }
}