package io.project.InternetRadioChecker.swing.table.data;

public class PlaylistData {

    private String name;
    private String uri;
    private CheckType checkType;

    public PlaylistData() {

    }

    public String getName() {
        return name;
    }

    public PlaylistData setName(String name) {
        this.name = name;
        return this;
    }

    public String getUri() {
        return uri;
    }

    public PlaylistData setUri(String uri) {
        this.uri = uri;
        return this;
    }

    public CheckType getCheckType() {
        return checkType;
    }

    public PlaylistData setCheckType(CheckType checkType) {
        this.checkType = checkType;
        return this;
    }
}
