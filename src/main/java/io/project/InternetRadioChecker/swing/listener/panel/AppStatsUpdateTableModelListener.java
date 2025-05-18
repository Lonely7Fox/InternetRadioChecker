package io.project.InternetRadioChecker.swing.listener.panel;

import io.project.InternetRadioChecker.swing.AppStatsPanel;
import io.project.InternetRadioChecker.swing.table.ProgressTableModel;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

public class AppStatsUpdateTableModelListener implements TableModelListener {

    private final AppStatsPanel appStatsPanel;
    private final ProgressTableModel progressTableModel;

    public AppStatsUpdateTableModelListener(AppStatsPanel appStatsPanel, ProgressTableModel progressTableModel) {
        this.appStatsPanel = appStatsPanel;
        this.progressTableModel = progressTableModel;
    }

    @Override
    public void tableChanged(TableModelEvent e) {
        //check only update one row
        if (e.getType() == TableModelEvent.UPDATE && e.getColumn() != TableModelEvent.ALL_COLUMNS && (e.getFirstRow() == e.getLastRow())) {
            int verifiedCount = progressTableModel.getVerifiedValueCount();
            int noVerifiedCount = progressTableModel.getNoVerifiedValueCount();
            appStatsPanel.setInfo(verifiedCount, noVerifiedCount);
        }
    }
}
