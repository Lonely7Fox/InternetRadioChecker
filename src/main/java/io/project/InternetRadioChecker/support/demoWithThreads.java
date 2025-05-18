package io.project.InternetRadioChecker.support;

import io.project.InternetRadioChecker.exceptions.TimeoutException;
import io.project.InternetRadioChecker.support.*;
import io.project.InternetRadioChecker.support.thread.*;
import io.project.InternetRadioChecker.swing.table.*;
import io.project.InternetRadioChecker.swing.table.data.*;

import java.net.*;
import java.net.http.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.util.regex.*;

public class demoWithThreads {

    private final ProgressTableModel model;
    private final TooltipCache cache;
    private AtomicBoolean isEnabled = new AtomicBoolean(false); //todo
    private final QueryPool queryPool;

    public demoWithThreads(ProgressTableModel model, TooltipCache cache, QueryPool pool) {
        this.model = model;
        this.cache = cache;
        this.queryPool = pool;
    }

    private void filterData() {
        CopyOnWriteArrayList<PlaylistData> playlistData = model.getTableData();

    }

    public void preInit() {
        int colURI = 2;//"URI"
        int colCheck = 3;//"Check"
        for (int i = 0; i < model.getRowCount(); i++) {
            Object value = null;
            try {
                value = model.getValueAt(i, colURI);
            } catch (IndexOutOfBoundsException ignored) {}
            if (value != null) {
                String finalValue = value.toString();
                int finalI = i;
                CompletableFuture.runAsync(() -> afterValidate(finalValue, finalI, colCheck), queryPool.getThreadPoolExecutor());
            }
        }
    }

    private void afterValidate(String uri, int rowIndex, int colCheckIndex) {
        try {
            FFprobeUtils.ProbeResult result = FFprobeUtils.getProbeWithTimeout(uri);
            cache.add(rowIndex, "YES");
            model.setValueAt(CheckType.VERIFIED, rowIndex, colCheckIndex);
        } catch (TimeoutException ex) {
            cache.add(rowIndex, "Timeout Exception");
            model.setValueAt(CheckType.NOT_VERIFIED, rowIndex, colCheckIndex);
        }
    }
}
