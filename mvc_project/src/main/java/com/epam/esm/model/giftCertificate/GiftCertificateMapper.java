package com.epam.esm.model.giftCertificate;


import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class GiftCertificateMapper implements RowMapper<GiftCertificate> {
    @Override
    public GiftCertificate mapRow(ResultSet rs, int rowNum) throws SQLException {

        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setId(UUID.fromString(rs.getString("id")));
        giftCertificate.setName(rs.getString("name"));
        giftCertificate.setDescription(rs.getString("description"));
        giftCertificate.setPrice(rs.getDouble("price"));
        giftCertificate.setDuration(rs.getInt("duration"));
        giftCertificate.setCreateDate(rs.getString("created_date"));
        giftCertificate.setLastUpdateDate(rs.getString("last_updated_date"));

        return giftCertificate;
    }
}
