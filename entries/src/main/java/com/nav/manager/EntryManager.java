package com.nav.manager;

import com.nav.model.LogEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;
import java.util.Stack;

@Service
public class EntryManager {

    @Autowired
    private FileManager fileManager;

    private Stack<LogEntry> deleted = new Stack<>();
    private Map<String, LogEntry> map;

    public EntryManager() {
        map = fileManager.loadFile();
    }

    public Collection<LogEntry> getMessages() {
//        LogEntry logEntry = new LogEntry("my first day", "test");
//        map.put(logEntry.getId(), logEntry);
        return getValues();
    }

    public void addMessage(LogEntry logEntry) {
        map.put(logEntry.getId(), logEntry);
        fileManager.saveFile(getValues());
    }

    private Collection<LogEntry> getValues() {
        return map.values();
    }

    public void undoDelete() {
        if (deleted.isEmpty()) {
            return;
        }
        addMessage(deleted.pop());
    }

    public void deleteMessage(String id) {
        if (!map.containsKey(id)) {
            return;
        }
        deleted.add(map.get(id));
        map.remove(id);
        fileManager.saveFile(getValues());
    }
}
