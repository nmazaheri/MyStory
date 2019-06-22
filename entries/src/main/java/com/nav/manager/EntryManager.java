package com.nav.manager;

import com.nav.model.LogEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.Map;
import java.util.Stack;

@Service
public class EntryManager {

    @Autowired
    private FileManager fileManager;
    private Stack<LogEntry> deleted = new Stack<>();
    private Map<String, LogEntry> map;

    @PostConstruct
    public void init() {
        map = fileManager.loadFile();
    }

    public Collection<LogEntry> getMessages() {
        return getValues();
    }

    public LogEntry getMessage(String id) {
        return map.get(id);
    }

    public void addMessage(LogEntry logEntry, boolean allowOverwrite) {
        String key = logEntry.getId();
        if (allowOverwrite && map.containsKey(key)) {
            deleteMessage(key);
        }
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
        addMessage(deleted.pop(), false);
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
