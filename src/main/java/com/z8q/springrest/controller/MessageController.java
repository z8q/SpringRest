package com.z8q.springrest.controller;

import com.z8q.springrest.exceptions.NotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("message")
public class MessageController {

    private int counter = 4;
    public List<Map<String, String>> messages = new ArrayList<Map<String, String>>() {{
        add(new HashMap<String, String>() {{ put("id", "1"); put("text", "First message"); }});
        add(new HashMap<String, String>() {{ put("id", "2"); put("text", "Second message"); }});
        add(new HashMap<String, String>() {{ put("id", "3"); put("text", "Third message"); }});
    }};

    @GetMapping
    public List<Map<String, String>> list() {
        return messages;
    }

    @GetMapping("{id}")
    public Map<String, String> getOne(@PathVariable String id) {
        return getMessage(id);
    }

    private Map<String, String> getMessage(@PathVariable String id) {
//        for (Map<String, String> message : messages) {
//            if (message.get("id").equals(id)) {
//                return message;
//            }
//            // предположительно надо сюда перенести эмпти мапу в рамках else statement
//        }
//        Map<String, String> empty = null;
//        throw new NotFoundException();

        return messages.stream()
                .filter(message -> message.get("id").equals(id))
                .findFirst()
                .orElseThrow(NotFoundException::new);
    }

    @PostMapping
    public Map<String, String> create(@RequestBody Map<String, String> message) {
        message.put("id", String.valueOf(counter++));

        messages.add(message);
        return message;
    }

    @PutMapping("{id}")
    public Map<String, String> update(@PathVariable String id, @RequestBody Map<String, String> message) {
        Map<String, String> messageFromDb = getMessage(id);
        messageFromDb.putAll(message);
        messageFromDb.put("id", id);

        return messageFromDb;
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable String id) {
    Map<String, String> message = getMessage(id);
    messages.remove(message);
    }
}
