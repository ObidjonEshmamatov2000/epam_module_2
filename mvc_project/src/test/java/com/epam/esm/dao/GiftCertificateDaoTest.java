package com.epam.esm.dao;

import com.epam.esm.config.TestConfig;
import com.epam.esm.dao.giftCertificate.GiftCertificateDaoImpl;
import com.epam.esm.domain.giftCertificate.GiftCertificate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(
        classes = { TestConfig.class },
        loader = AnnotationConfigContextLoader.class)
public class GiftCertificateDaoTest {

    @Autowired
    private GiftCertificateDaoImpl dao;

    @Resource
    private JdbcTemplate jdbcTemplate;


    @BeforeEach
    void setUp() {

    }


    @Test
    public void testCreateGiftCertificate() {
        createTestObjects();
        GiftCertificate gc =
                new GiftCertificate(UUID.randomUUID(), "test", "test desc", BigDecimal.valueOf(123.0), 12, LocalDateTime.now(), LocalDateTime.now(), null);
        GiftCertificate giftCertificate = dao.create(gc);

        Assertions.assertEquals("test", giftCertificate.getName());
    }

//    @Test
//    public void testGetGiftCertificateById() {
//
//        GiftCertificate giftCertificate = dao.get(UUID.fromString("c57e4db1-6ae4-4aee-b0d1-aaee00c26f77"));
//
//        Assertions.assertNotNull(giftCertificate);
//        Assertions.assertEquals("test3", giftCertificate.getName());
//        Assertions.assertEquals("test desc3", giftCertificate.getDescription());
//    }

//    @Test
//    public void testDeleteGiftCertificate() {
//        int delete = dao.delete(UUID.fromString("badc0e82-f873-491a-b2cc-5ba6580ac71f"));
//        Assertions.assertEquals(1, delete);
//    }


    private void createTestObjects(){
        jdbcTemplate.update(
                "create table gift_certificate (" +
                        "id uuid not null primary key, " +
                        "name character varying," +
                        "description character varying," +
                        "price numeric," +
                        "duration int," +
                        "created_date timestamp," +
                        "last_updated_date timestamp )");

        jdbcTemplate.update(
                "insert into gift_certificate values('64eeb184-972c-4bef-9879-c003d7352bd0', 'test1', 'test desc1', 123.0, 12, '2022-05-09 13:54:04.985000', '2022-05-09 13:54:04.985000');\n" +
                "insert into gift_certificate values('badc0e82-f873-491a-b2cc-5ba6580ac71f', 'test2', 'test desc2', 123.0, 12, '2022-05-09 13:54:04.985000', '2022-05-09 13:54:04.985000');\n" +
                "insert into gift_certificate values('c57e4db1-6ae4-4aee-b0d1-aaee00c26f77', 'test3', 'test desc3', 123.0, 12, '2022-05-09 13:54:04.985000', '2022-05-09 13:54:04.985000');\n" +
                "insert into gift_certificate values('fce5b289-6029-44cf-a870-5a0c30fd6d83', 'test4', 'test desc4', 123.0, 12, '2022-05-09 13:54:04.985000', '2022-05-09 13:54:04.985000');");
    }
}
