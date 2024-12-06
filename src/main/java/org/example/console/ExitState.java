package org.example.console;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

class ExitState implements Serializable {
    private static final long serialVersionUID = 1L; // 为了确保版本兼容性
    List<String> editorList;
    String activeEditorPath;
    Map<String, Boolean> showidSettings;

    ExitState(List<String> editorList, String activeEditorPath, Map<String, Boolean> showidSettings) {
        this.editorList = editorList;
        this.activeEditorPath = activeEditorPath;
        this.showidSettings = showidSettings;
    }
}