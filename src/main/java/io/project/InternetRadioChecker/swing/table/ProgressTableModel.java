package io.project.InternetRadioChecker.swing.table;

import io.project.InternetRadioChecker.swing.table.data.CheckType;
import io.project.InternetRadioChecker.swing.table.data.PlaylistData;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.CopyOnWriteArrayList;

public class ProgressTableModel extends AbstractTableModel {

    private final String[] colNames = new String[]{"#", "Name", "URI", "Check"};
    private final Class[] colClasses = new Class[]{Integer.class, String.class, String.class, Icon.class};
    private CopyOnWriteArrayList<PlaylistData> data;
    private final AtomicInteger verifiedItemCount = new AtomicInteger(0);
    private final AtomicInteger noVerifiedItemCount = new AtomicInteger(0);

    public ProgressTableModel() {
        data = new CopyOnWriteArrayList<>();
    }

    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public int getColumnCount() {
        return colNames.length;
    }

    @Override
    public String getColumnName(int column) {
        return colNames[column];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return colClasses[columnIndex];
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        PlaylistData p = data.get(rowIndex);
        switch (columnIndex) {
            case 0: return rowIndex + 1;
            case 1: return trimTitle(p.getName());
            case 2: return p.getUri();
            case 3: return p.getCheckType().getIcon();
            default: return null;
        }
    }

    public CheckType getCheckType(int rowIndex) {
        PlaylistData p = data.get(rowIndex);
        return p.getCheckType();
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        PlaylistData p = data.get(rowIndex);
        switch (columnIndex) {
            //column 0 - "#" - index, changed automatic
            case 1 -> { //"Name",
                p.setName((String) aValue);
            }
            case 2 -> { //"URI"
                p.setUri((String) aValue);
            }
            case 3 -> { //"Check"
                CheckType checkType = (CheckType) aValue;
                p.setCheckType(checkType);
                //VerifiedCount
                if (checkType.equals(CheckType.VERIFIED)) {
                    verifiedItemCount.incrementAndGet();
                }
                if (checkType.equals(CheckType.NOT_VERIFIED)) {
                    noVerifiedItemCount.incrementAndGet();
                }
            }
        }
        fireTableCellUpdated(rowIndex, columnIndex);
    }

    public CopyOnWriteArrayList<PlaylistData> getTableData() {
        return data;
    }

    public void loadTableData(CopyOnWriteArrayList<PlaylistData> playlistData) {
        data = playlistData;
        fireTableDataChanged();
    }

    public int getVerifiedValueCount() {
        return verifiedItemCount.intValue();
    }

    public int getNoVerifiedValueCount() {
        return noVerifiedItemCount.intValue();
    }

    //just visual trim
    private String trimTitle(String title) {
        if (title.contains("#EXTINF:-1,")) {
            int ind = title.lastIndexOf("#EXTINF:-1,") + 11;
            return title.substring(ind);
        }
        return title;
    }

}