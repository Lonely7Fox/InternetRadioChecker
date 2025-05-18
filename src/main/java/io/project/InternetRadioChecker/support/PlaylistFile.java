package io.project.InternetRadioChecker.support;

import java.util.StringJoiner;

public class PlaylistFile {

    private final StringJoiner text;

    public PlaylistFile() {
        text = new StringJoiner("\n");
        addLine("#EXTM3U");
    }

    public void addRadioStation(String title, String uri) {
        addLine(title);
        addLine(uri);
    }

    private void addLine(String line) {
        text.add(line);
    }

    public String complete() {
        return text.toString();
    }
}
