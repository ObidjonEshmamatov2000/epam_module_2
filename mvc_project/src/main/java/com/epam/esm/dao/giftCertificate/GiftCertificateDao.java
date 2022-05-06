package com.epam.esm.dao.giftCertificate;


import com.epam.esm.dao.BaseDao;
import com.epam.esm.model.giftCertificate.GiftCertificate;
import com.epam.esm.model.tag.Tag;

import java.util.List;
import java.util.UUID;

public interface GiftCertificateDao extends BaseDao<GiftCertificate> {
    int update(GiftCertificate updateCertificate);

    List<GiftCertificate> getByTagName(String tagName);

    List<GiftCertificate> searchByNameOrDescription(String name);

    List<GiftCertificate> searchAndGetByTagName(String searchWord, String tagName);

    void createTagsWithGiftCertificate(UUID certificateId, List<Tag> tags);
}
