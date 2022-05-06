package com.epam.esm.dao.tag;

import com.epam.esm.model.tag.Tag;
import com.epam.esm.model.tag.TagMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.UUID;

@Repository
public class TagDaoImpl implements TagDao{

    private final JdbcTemplate jdbcTemplate;

    public TagDaoImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public int create(Tag tag) {
        return jdbcTemplate.update("insert into tag(id, name) values (?, ?)", tag.getId(), tag.getName());
    }

    @Override
    public Tag get(UUID id) {
        return jdbcTemplate.queryForObject("select id, name from tag where id = ?", new TagMapper(), id);
    }

    @Override
    public List<Tag> getAll() {
        return jdbcTemplate.query("select id, name from tag", new TagMapper());
    }

    @Override
    public int delete(UUID id) {
        return jdbcTemplate.update("delete from tag where id = ?", id);
    }

    @Override
    public Tag getTagByName(String tagName) {
        return jdbcTemplate.queryForObject("select id, name from tag where name = ?", new TagMapper() ,tagName);
    }
}
