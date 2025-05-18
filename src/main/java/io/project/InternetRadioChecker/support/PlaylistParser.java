package io.project.InternetRadioChecker.support;

import io.project.InternetRadioChecker.swing.table.data.CheckType;
import io.project.InternetRadioChecker.swing.table.data.PlaylistData;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class PlaylistParser {

    public static CopyOnWriteArrayList<PlaylistData> parsePlaylist(Path path) {
        String title = "";
        CopyOnWriteArrayList<PlaylistData> list = new CopyOnWriteArrayList<>();
        for (String line : getLines(path)) {
            if (!line.isEmpty()) {
                //#EXTM3U - skip
                if (line.startsWith("#EXTINF")) {
                    title = line; //save title and wait next URI
                    continue;
                }
                if (!title.isEmpty()) {
                    list.add(new PlaylistData().setName(title).setUri(line).setCheckType(CheckType.EMPTY));
                }
//                if (RadioChecker.check(line)) {
//                    Object[] data = {}
//                    controller.getTable().getModel().addRow();
//                }
            }
        }
        return list;
    }

    private static List<String> getLines(Path path) {
        try {
            return Files.readAllLines(path, Charset.forName("Windows-1251"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
