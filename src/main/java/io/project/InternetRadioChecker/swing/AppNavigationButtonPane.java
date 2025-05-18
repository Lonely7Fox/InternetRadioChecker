package io.project.InternetRadioChecker.swing;

import javax.swing.*;
import java.awt.event.ActionListener;

public class AppNavigationButtonPane {

    private final JPanel buttonPanel;

    public AppNavigationButtonPane() {
        super();
        this.buttonPanel = new JPanel();
    }

    public JPanel createButtonPanel(ActionListener startButtonListener, ActionListener loadButtonListener) {
        JButton startButton = createButton("Start", startButtonListener);
        JButton loadButton = createButton("Load", loadButtonListener);
        buttonPanel.add(startButton);
        buttonPanel.add(loadButton);
        return buttonPanel;
    }

    public JPanel getButtonPanel() {
        return buttonPanel;
    }

    private JButton createButton(String name, ActionListener listener) {
        JButton button = new JButton(name);
        button.addActionListener(listener);
        return button;
    }
}
