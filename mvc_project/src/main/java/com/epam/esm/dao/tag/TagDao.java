package com.epam.esm.dao.tag;

import com.epam.esm.dao.BaseDao;
import com.epam.esm.domain.tag.Tag;

import java.util.List;
import java.util.UUID;

public interface TagDao extends BaseDao<Tag> {
    Tag getTagByName(String name);

    List<Tag> getGiftCertificateWithTags(UUID giftCertificateId);
}
