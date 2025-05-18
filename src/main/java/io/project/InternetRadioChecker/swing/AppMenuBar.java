package io.project.InternetRadioChecker.swing;

import io.project.InternetRadioChecker.support.PlaylistParser;
import io.project.InternetRadioChecker.swing.table.ProgressTableModel;
import io.project.InternetRadioChecker.swing.table.data.PlaylistData;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.nio.file.Path;
import java.util.concurrent.CopyOnWriteArrayList;

public class AppMenuBar {

    private final JFrame frame;
    private final ProgressTableModel model;

    public AppMenuBar(JFrame frame, ProgressTableModel model) {
        this.frame = frame;
        this.model = model;
    }

    public JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(createFileMenu());
        return menuBar;
    }

    private JMenu createFileMenu() {
        JMenu menu = new JMenu("File");
        menu.setMnemonic('A');
        menu.setPreferredSize(new Dimension( 70, 20));
        menu.getPopupMenu().setPreferredSize(new Dimension( 70, 50));
        menu.add(createOpenDialog());
        menu.addSeparator();
        menu.add(new ExitAction());
        return menu;
    }

    private JMenuItem createOpenDialog() {
        JMenuItem menuItem = new JMenuItem("Open");
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                FileDialog dialog = new FileDialog(frame, "Open File", FileDialog.LOAD);
//                dialog.setFile("*.m3u;*.m3u8");
//                dialog.setMultipleMode(false);
//                dialog.setVisible(true);
//                dialog.setAlwaysOnTop(true);
//                dialog.setLocationRelativeTo(null);

//                dialog.addWindowListener(new WindowAdapter() {
//                    @Override
//                    public void windowClosed(WindowEvent e) {
//                        super.windowClosed(e);
//                    }
//                });
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                fileChooser.setMultiSelectionEnabled(false);
                fileChooser.setDialogTitle("Open File");
                fileChooser.setFileFilter(new FileNameExtensionFilter("m3u/m3u8 files","m3u","m3u8"));
                int returnValue = fileChooser.showOpenDialog(frame);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    String fileName = selectedFile.getName();
                    Path filePath = Path.of(selectedFile.getAbsolutePath());
                    if (fileName.endsWith("m3u") || fileName.endsWith("m3u8")) {
                        //JOptionPane.showMessageDialog(frame, "Выбран файл: " + fileName);
                        CopyOnWriteArrayList<PlaylistData> playlistData = PlaylistParser.parsePlaylist(filePath);
                        model.loadTableData(playlistData);
                    } else {
                        JOptionPane.showMessageDialog(frame, String.format("Выбранный файл %s имеет неверное расширение!", fileName));
                    }
                }
            }
        });
        return menuItem;
    }

    /*
     *	Close the frame
     */
    class ExitAction extends AbstractAction
    {
        public ExitAction()
        {
            putValue(Action.NAME, "Exit");
            putValue(Action.SHORT_DESCRIPTION, getValue(Action.NAME));
            putValue(Action.MNEMONIC_KEY, KeyEvent.VK_X);
        }

        public void actionPerformed(ActionEvent e)
        {
            System.exit(0);
        }
    }
}
