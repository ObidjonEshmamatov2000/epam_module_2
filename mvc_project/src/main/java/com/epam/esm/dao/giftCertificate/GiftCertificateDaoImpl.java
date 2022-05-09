package com.epam.esm.dao.giftCertificate;

import com.epam.esm.domain.giftCertificate.GiftCertificate;
import com.epam.esm.domain.giftCertificate.GiftCertificateMapper;
import com.epam.esm.domain.tag.Tag;
import com.epam.esm.domain.tag.TagMapper;
import com.epam.esm.exception.BaseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Repository
public class GiftCertificateDaoImpl implements GiftCertificateDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public GiftCertificateDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public GiftCertificate create(GiftCertificate gc) {
        String query =
                "insert into gift_certificate(id, name, description, price, duration, " +
                "created_date, last_updated_date) values(?, ?, ?, ?, ?, ?, ?)";

        try {
            jdbcTemplate.update(
                    query,
                    gc.getId(),
                    gc.getName(),
                    gc.getDescription(),
                    gc.getPrice(),
                    gc.getDuration(),
                    gc.getCreateDate(),
                    gc.getLastUpdateDate());

            return gc;
        } catch (Exception e) {
            throw new BaseException(400, "gift certificate with name " + gc.getName() + " is already exist");
        }
    }

    @Override
    public GiftCertificate get(UUID id) {
        String query = "select * from gift_certificate where id = ?";
        try {
            return jdbcTemplate.queryForObject(query, new GiftCertificateMapper(), id);
        } catch (Exception e) {
            throw new BaseException(400, "gift certificate with id " + id + " is not found");
        }
    }

    @Override
    public List<GiftCertificate> getAll() {
        String query = "select * from gift_certificate";
        try {
            return jdbcTemplate.query(query, new GiftCertificateMapper());
        } catch (Exception e) {
            throw new BaseException(500, e.getLocalizedMessage());
        }
    }

    @Override
    public int delete(UUID id) {
        jdbcTemplate.update("delete from gift_certificate_tag where gift_certificate_id = ?", id);
        try {
            return jdbcTemplate.update("delete from gift_certificate where id = ?", id);
        } catch (Exception e) {
            throw new BaseException(400, "gift certificate with id " + id + " is not found");
        }
    }

    @Override
    public int update(GiftCertificate gc) {
        String query =
                "update gift_certificate set name = ?, description = ?, price = ?, " +
                "duration = ?, last_updated_date = ? where id = ?; ";

        try {
            return jdbcTemplate.update(
                    query,
                    gc.getName(),
                    gc.getDescription(),
                    gc.getPrice(),
                    gc.getDuration(),
                    gc.getLastUpdateDate(),
                    gc.getId());
        } catch (Exception e) {
            throw new BaseException(400, "gift certificate with id " + gc.getId() + " is not found");
        }
    }

    @Override
    @Transactional
    public void createTagsWithGiftCertificate(UUID certificateId, List<Tag> tags) {
        String QUERY_CREATE_TAG = "insert into tag (id, name) values(?, ?);";
        String QUERY_CREATE_CONNECTION
                = "insert into gift_certificate_tag (tag_id, gift_certificate_id) values (?, ?);";

        tags.forEach(tag -> {
            Tag byName;
            try {
                byName = jdbcTemplate.queryForObject("select * from tag where name = ?", new TagMapper(), tag.getName());
            } catch (Exception e) {
                byName = null;
            }

            if(byName == null){
                tag.setId(UUID.randomUUID());
                jdbcTemplate.update(QUERY_CREATE_TAG, tag.getId(), tag.getName());
            } else
                tag.setId(byName.getId());
            jdbcTemplate.update(QUERY_CREATE_CONNECTION, tag.getId(), certificateId);
        });
    }

    @Override
    public List<GiftCertificate> getFilteredGifts(
            String searchWord,
            String tagName,
            boolean doNameSort,
            boolean doDateSort,
            boolean isDescending
    ) {
        try {
            return jdbcTemplate.query("select * from get_filtered_gifts(?, ?, ?, ?, ?)",
                    new GiftCertificateMapper(),
                    searchWord, tagName, doNameSort, doDateSort, isDescending);
        } catch (Exception e) {
            throw new BaseException(500, e.getLocalizedMessage());
        }

    }
}
