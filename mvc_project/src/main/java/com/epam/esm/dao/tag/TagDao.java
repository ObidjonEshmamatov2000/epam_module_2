package com.epam.esm.dao.tag;

import com.epam.esm.dao.BaseDao;
import com.epam.esm.model.tag.Tag;

public interface TagDao extends BaseDao<Tag> {
    Tag getTagByName(String tagName);
}
