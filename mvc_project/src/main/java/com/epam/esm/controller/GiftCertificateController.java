package com.epam.esm.controller;

import com.epam.esm.domain.giftCertificate.GiftCertificate;
import com.epam.esm.dto.BaseResponseDto;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.service.giftCertificate.GiftCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/gift_certificate")
public class GiftCertificateController {
    private final GiftCertificateService giftCertificateService;

    @Autowired
    public GiftCertificateController(GiftCertificateService giftCertificateService) {
        this.giftCertificateService = giftCertificateService;
    }

    @RequestMapping(value = "/get_filter", method = RequestMethod.GET, headers = "Accept=application/json")
    public ResponseEntity<?> getFilteredGifts(
            @RequestParam(required = false) String searchWord,
            @RequestParam(required = false) String byTagName,
            @RequestParam(required = false) boolean doNameSort,
            @RequestParam(required = false) boolean doDateSort,
            @RequestParam(required = false) boolean isDescending
    ){
        BaseResponseDto<List<GiftCertificate>> filteredGifts =
                giftCertificateService.getFilteredGifts(searchWord, byTagName, doNameSort, doDateSort, isDescending);
        return ResponseEntity
                .status(filteredGifts.getStatus())
                .body(filteredGifts);

    }

    @RequestMapping(value = "/get_list", method = RequestMethod.GET, headers = "Accept=application/json")
    public ResponseEntity<?> getAll() {
        BaseResponseDto<List<GiftCertificate>> all = giftCertificateService.getAll();
        return ResponseEntity
                .status(all.getStatus())
                .body(all);
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET, headers = "Accept=application/json")
    public ResponseEntity<?> get(@RequestParam("id") UUID id) {
        BaseResponseDto<GiftCertificate> dto = giftCertificateService.get(id);
        return ResponseEntity
                .status(dto.getStatus())
                .body(dto);
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<?> create(@RequestBody GiftCertificateDto giftCertificateDto) {
        BaseResponseDto<GiftCertificate> dto = giftCertificateService.create(giftCertificateDto);
        return ResponseEntity
                .status(dto.getStatus())
                .body(dto);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE, headers = "Accept=application/json")
    public ResponseEntity<?> delete(@RequestParam UUID id) {
        BaseResponseDto<GiftCertificate> delete = giftCertificateService.delete(id);
        return ResponseEntity
                .status(delete.getStatus())
                .body(delete);
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT, headers = "Accept=application/json")
    public ResponseEntity<?> update(@RequestBody GiftCertificateDto giftCertificateDto) {
        BaseResponseDto<GiftCertificate> update = giftCertificateService.update(giftCertificateDto);
        return ResponseEntity
                .status(update.getStatus())
                .body(update);
    }
}
