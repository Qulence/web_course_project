package dao;

import domain.Exhibit;
import domain.ExhibitType;
import domain.Museum;
import domain.MuseumType;
import jdbcUtilities.JDBCConnectionUtil;
import org.junit.Before;
import org.junit.Test;
import testUtilities.DBUtil;

public class ExhibitDaoTest {

    private JDBCConnectionUtil jdbcConnectionUtil;
    private ExhibitDao exhibitDao;

    @Before
    public void init() {
        jdbcConnectionUtil = new JDBCConnectionUtil(
                "postgresql",
                "127.0.0.1",
                5432,
                "Museum",
                "postgres",
                "12439524");
        exhibitDao = new ExhibitDao(jdbcConnectionUtil);
        DBUtil.executeScript("./web_course_project.sql", jdbcConnectionUtil);
    }

    @Test
    public void save() {
        ExhibitType exhibitType = new ExhibitType(1L, "test");
        MuseumType museumType = new MuseumType(1L, "test");
        Museum museum = new Museum(
                1L, "test", "test", "test", "test", museumType);
        Exhibit exhibit = new Exhibit("test", false, museum, exhibitType);
        exhibitDao.save(exhibit);
    }

    @Test
    public void update() {
        ExhibitType exhibitType = new ExhibitType(2L, "");
        MuseumType museumType = new MuseumType(2L, "");
        Museum museum = new Museum(
                2L, "test", "test", "test", "test", museumType);
        Exhibit exhibit = new Exhibit(2L, "test", false, museum, exhibitType);
        exhibitDao.update(exhibit);
    }

    @Test
    public void deleteById() {
        exhibitDao.deleteById(1L);
    }

    @Test
    public void getAll() {
        Iterable<Exhibit> exhibits = exhibitDao.getAll();
        for (Exhibit exhibit : exhibits) {
            System.out.println(exhibit);
        }
    }

    @Test
    public void getById() {
        System.out.println(exhibitDao.getById(8L));
    }
}