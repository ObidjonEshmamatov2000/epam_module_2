package com.epam.esm.dao.tag;

import com.epam.esm.domain.tag.Tag;
import com.epam.esm.domain.tag.TagMapper;
import com.epam.esm.exception.BaseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class TagDaoImpl implements TagDao{

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public TagDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Tag create(Tag tag) {
        try {
            jdbcTemplate.update("insert into tag(id, name) values (?, ?)", tag.getId(), tag.getName());
            return tag;
        } catch (Exception e) {
            throw new BaseException(500, "tag with name " + tag.getName() + " is already exist");
        }
    }

    @Override
    public Tag get(UUID id) {
        try {
            return jdbcTemplate.queryForObject("select id, name from tag where id = ?", new TagMapper(), id);
        } catch (Exception e) {
            throw new BaseException(400, "tag with id " + id + " is not found");
        }
    }

    @Override
    public List<Tag> getAll() {
        try {
            return jdbcTemplate.query("select id, name from tag", new TagMapper());
        } catch (Exception e) {
            throw new BaseException(500, e.getLocalizedMessage());
        }
    }

    @Override
    public int delete(UUID id) {
        try {
            return jdbcTemplate.update("delete from tag where id = ?", id);
        } catch (Exception e) {
            throw new BaseException(400, e.getLocalizedMessage());
        }
    }

    @Override
    public Tag getTagByName(String tagName) {
        try {
            return jdbcTemplate.queryForObject("select id, name from tag where name = ?", new TagMapper() ,tagName);
        } catch (Exception e) {
            throw new BaseException(500, e.getLocalizedMessage());
        }
    }

    @Override
    public List<Tag> getGiftCertificateWithTags(UUID id) {
        try {
            return jdbcTemplate.query("select * from get_tags_by_gift_certificate_id(?)", new TagMapper(), id);
        } catch (Exception e) {
            throw new BaseException(400, "tag with id " + id + " is not found");
        }
    }
}
