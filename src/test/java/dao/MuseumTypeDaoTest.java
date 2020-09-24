package dao;

import domain.MuseumType;
import jdbcUtilities.JDBCConnectionUtil;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.junit.*;
import testUtilities.DBUtil;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

public class MuseumTypeDaoTest {

    private JDBCConnectionUtil jdbcConnectionUtil;
    private MuseumTypeDao museumTypeDao;

    @Before
    public void init() {
        jdbcConnectionUtil = new JDBCConnectionUtil(
                "postgresql",
                "127.0.0.1",
                5432,
                "Museum",
                "postgres",
                "12439524");
        museumTypeDao = new MuseumTypeDao(jdbcConnectionUtil);
        DBUtil.executeScript("./web_course_project.sql", jdbcConnectionUtil);
    }

    @Test
    public void save() {
        MuseumType museumType = new MuseumType("test");
        museumTypeDao.save(museumType);
    }

    @Test
    public void update() {
        MuseumType museumType = new MuseumType("test1");
        museumTypeDao.update(museumType);
    }

    @Test
    public void deleteById() {
        museumTypeDao.deleteById(1L);
    }

    @Test
    public void getAll() {
        Iterable<MuseumType> result = museumTypeDao.getAll();
        for (MuseumType museumType : result) {
            System.out.println(museumType + "\n");
        }
    }

    @Test
    public void getById() {
        Optional<MuseumType> museumType = museumTypeDao.getById(1L);
        System.out.println(museumType);
    }

}