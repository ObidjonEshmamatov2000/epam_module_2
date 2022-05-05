package com.epam.esm.model.giftCertificate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GiftCertificate {

    private UUID id;
    private String name;
    private String description;
    private double price;
    private int duration;
    private String createDate;
    private String lastUpdateDate;

    @JsonIgnore
    public String getCurrentTimeInIso8601() {
        return ZonedDateTime.now( ZoneOffset.UTC ).format( DateTimeFormatter.ISO_INSTANT );
    }
}
