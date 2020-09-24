package dao;

import domain.Museum;
import domain.MuseumType;
import jdbcUtilities.JDBCConnectionUtil;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import testUtilities.DBUtil;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

public class MuseumDaoTest {

    private JDBCConnectionUtil jdbcConnectionUtil;
    private MuseumDao museumDao;

    @Before
    public void init() {
        jdbcConnectionUtil = new JDBCConnectionUtil(
                "postgresql",
                "127.0.0.1",
                5432,
                "Museum",
                "postgres",
                "12439524");
        museumDao = new MuseumDao(jdbcConnectionUtil);
        DBUtil.executeScript("./web_course_project.sql", jdbcConnectionUtil);
    }

    @Test
    public void save() {
        MuseumType museumType = new MuseumType(6, "test");
        Museum museum = new Museum(
                "test", "test", "test", "test", museumType);
        museumDao.save(museum);
    }

    @Test
    public void update() {
        MuseumType museumType = new MuseumType(2, "test");
        Museum museum = new Museum(1,
                "test", "test", "test", "test", museumType);
        museumDao.update(museum);
    }

    @Test
    public void deleteById() {
        museumDao.deleteById(3L);
    }

    @Test
    public void getAll() {
        Iterable<Museum> museums = museumDao.getAll();
        for (Museum museum : museums) {
            System.out.println(museum);
        }
    }

    @Test
    public void getById() {
        Optional<Museum> museum = museumDao.getById(3L);
        System.out.println(museum);
    }

}