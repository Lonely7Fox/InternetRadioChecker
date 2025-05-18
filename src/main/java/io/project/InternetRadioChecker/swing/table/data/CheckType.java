package io.project.InternetRadioChecker.swing.table.data;

import javax.swing.*;

public enum CheckType {

    EMPTY("src/main/resources/icons/Paomedia-Small-N-Flat-Disc.16.png"),
    VERIFIED("src/main/resources/icons/Paomedia-Small-N-Flat-Sign-check.16.png"),
    NOT_VERIFIED("src/main/resources/icons/Paomedia-Small-N-Flat-Sign-error.16.png");

    private final String path;

    CheckType(String path) {
        this.path = path;
    }

    public ImageIcon getIcon() {
        return new ImageIcon(path);
    }
}
