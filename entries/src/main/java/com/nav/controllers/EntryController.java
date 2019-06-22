package com.nav.controllers;

import com.nav.manager.EntryManager;
import com.nav.model.LogEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Collection;

@SuppressWarnings("unused")
@Controller
public class EntryController {

    public static final String REDIRECT_TO_INDEX = "redirect:/";
    public static LogEntry EMPTY_LOG_ENTRY = new LogEntry("", "");

    @Autowired
    private EntryManager entryManager;

    private LogEntry lastEntry = EMPTY_LOG_ENTRY;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") String id) {
        entryManager.deleteMessage(id);
        return REDIRECT_TO_INDEX;
    }

    @GetMapping("/undoDelete")
    public String undoLastDelete() {
        entryManager.undoDelete();
        return REDIRECT_TO_INDEX;
    }

    @PostMapping("/add")
    public String add(@ModelAttribute LogEntry logEntry) {
        lastEntry = logEntry;
        entryManager.addMessage(logEntry);
        return REDIRECT_TO_INDEX;
    }

    @PostMapping("/update")
    public String update(@ModelAttribute LogEntry logEntry) {
        entryManager.addMessage(logEntry);
        return REDIRECT_TO_INDEX;
    }


    @ModelAttribute("logEntries")
    public Collection<LogEntry> getMessages() {
        return entryManager.getMessages();
    }

    @ModelAttribute("lastEntry")
    public LogEntry lastEntry() {
        return lastEntry;
    }

}
