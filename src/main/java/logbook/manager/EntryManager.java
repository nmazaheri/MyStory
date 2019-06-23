package logbook.manager;

import logbook.model.LogEntry;
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
    private final Stack<LogEntry> deleted = new Stack<>();
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

    public void addMessage(LogEntry currentEntry, boolean allowOverwrite) {
        if (currentEntry.isEmpty()) {
            // skipping
            return;
        }
        String key = currentEntry.getId();
        if (allowOverwrite && map.containsKey(key)) {
            LogEntry oldEntry = deleteMessage(key);
            oldEntry.update(currentEntry);
            currentEntry = oldEntry;
        }
        map.put(currentEntry.getId(), currentEntry);
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

    public LogEntry deleteMessage(String id) {
        if (!map.containsKey(id)) {
            return null;
        }
        LogEntry entry = map.remove(id);
        deleted.add(entry);
        fileManager.saveFile(getValues());
        return LogEntry.clone(entry);
    }
}
