package io.project.InternetRadioChecker.swing;

import javax.swing.*;
import java.awt.*;

public class AppStatsPanel {

    private final JPanel statusPanel;
    private JLabel totalLabel;
    private JProgressBar uncheckedBar;
    private JProgressBar validBar;
    private JProgressBar invalidBar;
    private int totalItems = 0;

    public AppStatsPanel() {
        this.statusPanel = new JPanel();
        statusPanel.setLayout(new GridLayout(5, 2));
        statusPanel.setBorder(BorderFactory.createTitledBorder("Информация"));
        //statusPanel.setBorder(BorderFactory.createEtchedBorder());
        statusPanel.setPreferredSize(new Dimension(250, 100));
        //statusPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        init();
    }

    private void init() {
        JLabel total = new JLabel("Всего:", SwingConstants.LEFT);
        JLabel unchecked = new JLabel("Не проверено:", SwingConstants.LEFT);
        JLabel valid = new JLabel("Валидное:", SwingConstants.LEFT);
        JLabel invalid = new JLabel("Не валидное:", SwingConstants.LEFT);

        //first row (total items count)
        this.totalLabel = new JLabel("0", SwingConstants.RIGHT);
        statusPanel.add(total);
        totalLabel.setBorder(BorderFactory.createBevelBorder(1));
        statusPanel.add(totalLabel);

        //second row (separators)
        statusPanel.add(createSeparator());
        statusPanel.add(createSeparator());

        //third row (unchecked items count)
        statusPanel.add(unchecked);
        //progressBar.setBorder(BorderFactory.createBevelBorder(1));
        this.uncheckedBar = createProgressBar();
        statusPanel.add(uncheckedBar);

        //forth row (valid items count)
        statusPanel.add(valid);
        this.validBar = createProgressBar();
        statusPanel.add(validBar);

        //fifth row (invalid items count)
        statusPanel.add(invalid);
        this.invalidBar = createProgressBar();
        statusPanel.add(invalidBar);
    }

    public void setLoadedFileInfo(int totalRows) {
        this.totalItems = totalRows;
        totalLabel.setText(String.valueOf(totalRows));
        
        //new maximum bars count
        uncheckedBar.setMaximum(totalRows);
        validBar.setMaximum(totalRows);
        invalidBar.setMaximum(totalRows);
        
        //update unchecked
        setProgressBar(uncheckedBar, totalRows);
    }

    public void setInfo(int validCount, int invalidCount) {
        int uncheckedCount = totalItems - (validCount + invalidCount);
        setProgressBar(uncheckedBar, uncheckedCount);
        setProgressBar(validBar, validCount);
        setProgressBar(invalidBar, invalidCount);
    }

    private JProgressBar createProgressBar() {
        JProgressBar progressBar = new JProgressBar(0, 100);
        progressBar.setValue(0);
        progressBar.setStringPainted(true);
        progressBar.setString("0(0%)");
        return progressBar;
    }

    private void setProgressBar(JProgressBar progressBar, int newValue) {
        int percent = (int)(((float) newValue / (float) totalItems) * 100);
        progressBar.setValue(percent);
        progressBar.setString(String.format("%d(%s)", newValue, percent + "%"));
    }

    private JSeparator createSeparator() {
        JSeparator separator = new JSeparator();
        separator.setVisible(false);
        return separator;
    }

    public JPanel getPanel() {
        return statusPanel;
    }
}
