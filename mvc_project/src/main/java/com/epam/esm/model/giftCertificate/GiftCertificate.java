package com.epam.esm.model.giftCertificate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
}
