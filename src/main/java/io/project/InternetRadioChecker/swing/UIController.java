package io.project.InternetRadioChecker.swing;

import io.project.InternetRadioChecker.support.*;
import io.project.InternetRadioChecker.support.thread.*;
import io.project.InternetRadioChecker.swing.listener.panel.AppStatsUpdateTableModelListener;
import io.project.InternetRadioChecker.swing.table.ProgressTable;
import io.project.InternetRadioChecker.swing.table.ProgressTableModel;
import io.project.InternetRadioChecker.swing.table.data.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.nio.file.*;
import java.util.concurrent.*;

public class UIController {

    private static final Logger log = LoggerFactory.getLogger(UIController.class);
    private JFrame frame;
    private ProgressTable table;

    public UIController() {
        init();
    }

    private void init() {
        QueryPool queryPool = new QueryPool(); //init first of all

        //Main App Frame
        this.frame = new AppFrame("Radio Checker");

        //Table with data and scroll
        this.table = new ProgressTable();
        ProgressTableModel model = table.getModel();
        JScrollPane scrollPane = table.getScrollTablePane();

        //Infopanel
        AppStatsPanel statsPanel = new AppStatsPanel();
        JPanel statsJPanel = statsPanel.getPanel();
        JPanel sideWrapper = new JPanel(new FlowLayout(FlowLayout.LEFT));
        sideWrapper.add(statsJPanel);

        AppStatsUpdateTableModelListener updateListener = new AppStatsUpdateTableModelListener(statsPanel, model);
        model.addTableModelListener(updateListener); //update infopanel from table

        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
        frame.getContentPane().add(sideWrapper, BorderLayout.EAST);

        //Button panel
        AppNavigationButtonPane buttonPane = new AppNavigationButtonPane();

        demoWithThreads demoWithThreads = new demoWithThreads(model, table.getTooltipCache(), queryPool); //refactor
        ActionListener startAction = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                demoWithThreads.preInit();
            }
        };

        ActionListener stopAction = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Path filePath = Path.of("C:\\Users\\Alexander\\Desktop\\TestTest.m3u");
                    CopyOnWriteArrayList<PlaylistData> playlistData = PlaylistParser.parsePlaylist(filePath);
                    model.loadTableData(playlistData);
                    statsPanel.setLoadedFileInfo(playlistData.size());
                }
        };

        JPanel buttonPanel = buttonPane.createButtonPanel(startAction, stopAction);
        frame.getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        //Menu - after all components
        AppMenuBar bar = new AppMenuBar(frame, model);
        JMenuBar menuBar = bar.createMenuBar();
        frame.setJMenuBar(menuBar);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}

