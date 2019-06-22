package com.nav.manager;

import com.nav.model.LogEntry;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public class FileManager {

    public Map<String, LogEntry> loadFile() {
        return new LinkedHashMap<>();
    }

    public void saveFile(Collection<LogEntry> entries) {
        try (
                FileOutputStream fout = new FileOutputStream("address.ser", true);
                ObjectOutputStream oos = new ObjectOutputStream(fout);
        ) {
            oos.writeObject(entries);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
