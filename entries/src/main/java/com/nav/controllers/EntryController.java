package com.nav.controllers;

import com.nav.manager.EntryManager;
import com.nav.manager.FileManager;
import com.nav.model.LogEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.UUID;

@SuppressWarnings("unused")
@Controller
public class EntryController {

    public static final String REDIRECT_TO_INDEX = "redirect:/";

    private static String getUniqueId() {
        return UUID.randomUUID().toString();
    }

    @Autowired
    private EntryManager entryManager;

    @Autowired
    private FileManager fileManager;

    private LogEntry entry;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") String id) {
        entryManager.deleteMessage(id);
        return REDIRECT_TO_INDEX;
    }

    @GetMapping("/retrieve/{id}")
    public String retrieve(@PathVariable("id") String id) {
        entry = entryManager.getMessage(id);
        return REDIRECT_TO_INDEX;
    }

    @GetMapping("/undoDelete")
    public String undoLastDelete() {
        entryManager.undoDelete();
        return REDIRECT_TO_INDEX;
    }

    @PostMapping("/add")
    public String add(@ModelAttribute LogEntry logEntry) {
        entryManager.addMessage(logEntry, true);
        generateLogEntry();
        return REDIRECT_TO_INDEX;
    }

    @ModelAttribute("logEntries")
    public Collection<LogEntry> getMessages() {
        return entryManager.getMessages();
    }

    @ModelAttribute("entry")
    public LogEntry lastEntry() {
        return entry;
    }

    @PostConstruct
    private void generateLogEntry() {
        entry = new LogEntry(getUniqueId(), "", "");
    }

}
