package io.project.InternetRadioChecker.swing;

import javax.swing.*;
import java.awt.*;

public class AppFrame extends JFrame {

    public AppFrame(String title) throws HeadlessException {
        super(title);
        //frame.setLayout(new FlowLayout());
        //frame.setResizable(true);
        //frame.setBackground(Color.RED);
        this.setMinimumSize(new Dimension(1000, 600));
        this.setPreferredSize(new Dimension(1000, 600));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

}
