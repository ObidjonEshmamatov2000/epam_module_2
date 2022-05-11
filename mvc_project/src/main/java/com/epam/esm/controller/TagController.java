package com.epam.esm.controller;

import com.epam.esm.domain.tag.Tag;
import com.epam.esm.dto.BaseResponseDto;
import com.epam.esm.service.tag.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/tags")
public class TagController {
    private final TagService tagService;

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @RequestMapping(value = "", method = RequestMethod.GET, headers = "Accept=application/json")
    public ResponseEntity<?> getAll() {
        BaseResponseDto<List<Tag>> all = tagService.getAll();
        return ResponseEntity
                .status(all.getStatus())
                .body(all);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
    public ResponseEntity<?> get(@PathVariable("id") UUID id) {
        BaseResponseDto<Tag> dto = tagService.get(id);
        return ResponseEntity
                .status(dto.getStatus())
                .body(dto);
    }

    @RequestMapping(value = "", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<?> create(@RequestBody Tag tag) {
        BaseResponseDto<Tag> dto = tagService.create(tag);
        return ResponseEntity
                .status(dto.getStatus())
                .body(dto);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
    public ResponseEntity<?> deleteTag(@PathVariable("id") UUID id) {
        BaseResponseDto<Tag> delete = tagService.delete(id);
        return ResponseEntity
                .status(delete.getStatus())
                .body(delete);
    }
}
