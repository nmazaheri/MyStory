package logbook.controllers;

import logbook.manager.EntryManager;
import logbook.manager.FileManager;
import logbook.model.LogEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@SuppressWarnings("unused")
@Controller
public class EntryController {

    public static final String REDIRECT_TO_INDEX = "redirect:/";
    public static final String REDIRECT_TO_UPDATE = "redirect:/update";

    @Autowired
    private EntryManager entryManager;

    @Autowired
    private FileManager fileManager;

    private LogEntry entry = new LogEntry();

    @GetMapping("/")
    public String index(ModelMap map) {
        map.addAttribute("logEntries", entryManager.getMessages());
        return "index";
    }

    @GetMapping("/add")
    public String add(ModelMap map) {
        map.addAttribute("entry", new LogEntry());
        return "add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute LogEntry logEntry) {
        entryManager.addMessage(logEntry, true);
        return REDIRECT_TO_INDEX;
    }

    @GetMapping("/retrieve/{id}")
    public String retrieve(@PathVariable("id") String id, ModelMap map) {
        entry = entryManager.getMessage(id);
        map.addAttribute("entry", entry);
        return "update";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") String id) {
        entryManager.deleteMessage(id, true);
        return REDIRECT_TO_INDEX;
    }

    @GetMapping("/undoDelete")
    public String undoLastDelete() {
        entryManager.undoDelete();
        return REDIRECT_TO_INDEX;
    }

}
