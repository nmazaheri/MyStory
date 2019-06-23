package logbook.manager;

import logbook.model.LogEntry;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FileManager {

    private static final String FILENAME = "story.dat";

    public Map<String, LogEntry> loadFile() {
        return readObject().stream().collect(Collectors.toMap(LogEntry::getId, entry -> entry, (a, b) -> b, LinkedHashMap::new));
    }

    private LinkedList<LogEntry> readObject() {
        try {
            File file = new File(FILENAME);
            if (file.exists()) {
                ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
                return (LinkedList<LogEntry>) in.readObject();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new LinkedList<>();
    }

    public void saveFile(Collection<LogEntry> entries) {
        LinkedList<LogEntry> list = new LinkedList<>(entries);
        try {
            File file = new File(FILENAME);
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
            out.writeObject(list);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
