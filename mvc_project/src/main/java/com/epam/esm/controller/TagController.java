package com.epam.esm.controller;

import com.epam.esm.model.tag.Tag;
import com.epam.esm.service.tag.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/tag")
public class TagController {
    private final TagService tagService;

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @RequestMapping(value = "/get_list", method = RequestMethod.GET, headers = "Accept=application/json")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(tagService.getAll());
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET, headers = "Accept=application/json")
    public ResponseEntity<?> get(@RequestParam UUID id) {
        return ResponseEntity.ok(tagService.get(id));
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<?> create(@RequestBody Tag tag) {
        return ResponseEntity.ok(tagService.create(tag));
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE, headers = "Accept=application/json")
    public ResponseEntity<?> deleteTag(@RequestParam UUID id) {
        return ResponseEntity.ok(tagService.delete(id));
    }
}
