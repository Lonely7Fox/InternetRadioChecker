package io.project.InternetRadioChecker.swing.table;

import io.project.InternetRadioChecker.swing.table.data.CheckType;

import javax.swing.*;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.HashMap;

public class ProgressTable extends JTable {

    private final ProgressTableModel model;
    private final TooltipCache tooltipCache;
    private final JScrollPane scrollTablePane;

    public ProgressTable() {
        super(/*new ProgressTableModel() , new ProgressTableColumnModel()*/);
        this.model = new ProgressTableModel();
        this.tooltipCache = new TooltipCache();
        this.scrollTablePane = initPanel();
    }

    private JScrollPane initPanel() {
        setModel(model);
        this.setRowHeight(30);
        this.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        this.setIntercellSpacing(new Dimension(10, 10));
        this.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        this.setEnabled(false);
        //todo to stop moving column need release this, and addColumn manually.
//        ProgressTableColumnModel columnModel = new ProgressTableColumnModel();
//        columnModel.addColumn();
//        columnModel.getColumn(0).setWidth(50);
//        table.setColumnModel(columnModel);
        //table.setGridColor(Color.blue);
        this.setRowSelectionAllowed(false);
        TableColumn column0 = this.getColumnModel().getColumn(0);
        column0.setMinWidth(30);
        column0.setMaxWidth(50);
        TableColumn column3 = this.getColumnModel().getColumn(3);
        column3.setMinWidth(100);
        column3.setMaxWidth(100);

        JScrollPane scrollPane = new JScrollPane(this);
        scrollPane.setPreferredSize(new Dimension(785, 600));
        //scrollPane.setSize(700, 400);
        return scrollPane;
    }

    public ProgressTableModel getModel() {
        return model;
    }

    public TooltipCache getTooltipCache() {
        return tooltipCache;
    }

    public JScrollPane getScrollTablePane() {
        return scrollTablePane;
    }

    @Override
    //used only for column Check
    public String getToolTipText(MouseEvent e) {
        String tip = null;
        java.awt.Point p = e.getPoint();
        int rowIndex = rowAtPoint(p);
        CheckType type = model.getCheckType(rowIndex);
        int colIndex = columnAtPoint(p);
        int realColumnIndex = convertColumnIndexToModel(colIndex);
        //if (realColumnIndex == 3 && (type.equals(CheckType.NOT_VERIFIED))) { //tooltip for Error messages
        if (realColumnIndex == 3) {
            tip = tooltipCache.getValue(rowIndex);
        } else {
            tip = super.getToolTipText(e);
        }
        return tip;
    }
}