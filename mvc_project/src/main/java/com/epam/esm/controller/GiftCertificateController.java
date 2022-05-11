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
@RequestMapping("/api/gift_certificates")
public class GiftCertificateController {
    private final GiftCertificateService giftCertificateService;

    @Autowired
    public GiftCertificateController(GiftCertificateService giftCertificateService) {
        this.giftCertificateService = giftCertificateService;
    }

    @RequestMapping(value = "/filter", method = RequestMethod.GET, headers = "Accept=application/json")
    public ResponseEntity<?> getFilteredGifts(
            @RequestParam(required = false) String searchWord,
            @RequestParam(required = false) String byTagName,
            @RequestParam(required = false) boolean doNameSort,
            @RequestParam(required = false) boolean doDateSort,
            @RequestParam(required = false) boolean isDescending
    ){
        BaseResponseDto<List<GiftCertificateDto>> filteredGifts =
                giftCertificateService.getFilteredGifts(searchWord, byTagName, doNameSort, doDateSort, isDescending);
        return ResponseEntity
                .status(filteredGifts.getStatus())
                .body(filteredGifts);

    }

    @RequestMapping(value = "", method = RequestMethod.GET, headers = "Accept=application/json")
    public ResponseEntity<?> getAll() {
        BaseResponseDto<List<GiftCertificateDto>> all = giftCertificateService.getAll();
        return ResponseEntity
                .status(all.getStatus())
                .body(all);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
    public ResponseEntity<?> get(@PathVariable("id") UUID id) {
        BaseResponseDto<GiftCertificateDto> dto = giftCertificateService.get(id);
        return ResponseEntity
                .status(dto.getStatus())
                .body(dto);
    }

    @RequestMapping(value = "", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<?> create(@RequestBody GiftCertificateDto giftCertificateDto) {
        BaseResponseDto<GiftCertificateDto> dto = giftCertificateService.create(giftCertificateDto);
        return ResponseEntity
                .status(dto.getStatus())
                .body(dto);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        BaseResponseDto<GiftCertificateDto> delete = giftCertificateService.delete(id);
        return ResponseEntity
                .status(delete.getStatus())
                .body(delete);
    }

    @RequestMapping(value = "", method = RequestMethod.PUT, headers = "Accept=application/json")
    public ResponseEntity<?> update(@RequestBody GiftCertificateDto giftCertificateDto) {
        BaseResponseDto<GiftCertificateDto> update = giftCertificateService.update(giftCertificateDto);
        return ResponseEntity
                .status(update.getStatus())
                .body(update);
    }
}
