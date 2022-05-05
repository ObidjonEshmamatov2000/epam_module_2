package com.epam.esm.dao.giftCertificate;


import com.epam.esm.dao.BaseDao;
import com.epam.esm.model.giftCertificate.GiftCertificate;

import java.util.List;

public interface GiftCertificateDao extends BaseDao<GiftCertificate> {
    int update(GiftCertificate updateCertificate);

    List<GiftCertificate> getByTagName(String tagName);
}
