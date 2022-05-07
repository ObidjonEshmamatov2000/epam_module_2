package com.epam.esm.dao.tag;

import com.epam.esm.dao.BaseDao;
import com.epam.esm.model.tag.Tag;

import java.util.List;
import java.util.UUID;

public interface TagDao extends BaseDao<Tag> {
    Tag getTagByName(String tagName);

    List<Tag> getGiftCertificateWithTags(UUID id);
}
