package com.epam.esm.dao.giftCertificate;


import com.epam.esm.dao.BaseDao;
import com.epam.esm.model.giftCertificate.GiftCertificate;
import com.epam.esm.model.tag.Tag;

import java.util.List;
import java.util.UUID;

public interface GiftCertificateDao extends BaseDao<GiftCertificate> {
    int update(GiftCertificate updateCertificate);

    void createTagsWithGiftCertificate(UUID certificateId, List<Tag> tags);

    List<GiftCertificate> getFilteredGifts(
            String searchWord, String tagName, boolean doNameSort, boolean doDateSort, boolean isDescending
    );
}
