package com.epam.esm.controller;

import com.epam.esm.dao.giftCertificate.GiftCertificateDao;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.service.giftCertificate.GiftCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/gift_certificate")
public class GiftCertificateController {
    private final GiftCertificateService giftCertificateService;

    @Autowired
    public GiftCertificateController(GiftCertificateService giftCertificateService) {
        this.giftCertificateService = giftCertificateService;
    }

    @RequestMapping(value = "/get_list", method = RequestMethod.GET, headers = "Accept=application/json")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(giftCertificateService.getAll());
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET, headers = "Accept=application/json")
    public ResponseEntity<?> get(@RequestParam("id") UUID id) {
        return ResponseEntity.ok(giftCertificateService.get(id));
    }

    @RequestMapping(value = "/get_tag", method = RequestMethod.GET, headers = "Accept=application/json")
    public ResponseEntity<?> getByTagName(@RequestParam String name) {
        return ResponseEntity.ok(giftCertificateService.getByTagName(name));
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<?> create(@RequestBody GiftCertificateDto giftCertificateDto) {
        return ResponseEntity.ok(giftCertificateService.create(giftCertificateDto));
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE, headers = "Accept=application/json")
    public ResponseEntity<?> delete(@RequestParam UUID id) {
        return ResponseEntity.ok(giftCertificateService.delete(id));
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT, headers = "Accept=application/json")
    public ResponseEntity<?> update(@RequestBody GiftCertificateDto giftCertificateDto) {
        return ResponseEntity.ok(giftCertificateService.update(giftCertificateDto));
    }
}