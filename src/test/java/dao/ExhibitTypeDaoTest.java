package dao;

import domain.ExhibitType;
import jdbcUtilities.JDBCConnectionUtil;
import org.junit.Before;
import org.junit.Test;
import testUtilities.DBUtil;

public class ExhibitTypeDaoTest {

    private JDBCConnectionUtil jdbcConnectionUtil;
    private ExhibitTypeDao exhibitTypeDao;

    @Before
    public void init() {
        jdbcConnectionUtil = new JDBCConnectionUtil(
                "postgresql",
                "127.0.0.1",
                5432,
                "Museum",
                "postgres",
                "12439524");
        exhibitTypeDao = new ExhibitTypeDao(jdbcConnectionUtil);
        DBUtil.executeScript("./web_course_project.sql", jdbcConnectionUtil);
    }

    @Test
    public void save() {
        ExhibitType exhibitType = new ExhibitType("test");
        exhibitTypeDao.save(exhibitType);
    }

    @Test
    public void update() {
        ExhibitType exhibitType = new ExhibitType(1, "test");
        exhibitTypeDao.update(exhibitType);
    }

    @Test
    public void deleteById() {
        exhibitTypeDao.deleteById(2L);
    }

    @Test
    public void getAll() {
        Iterable<ExhibitType> exhibitTypes = exhibitTypeDao.getAll();
        for (ExhibitType exhibitType : exhibitTypes) {
            System.out.println(exhibitType);
        }
    }

    @Test
    public void getById() {
        System.out.println(exhibitTypeDao.getById(4L));
    }

}