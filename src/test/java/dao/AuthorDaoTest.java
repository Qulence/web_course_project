package dao;

import domain.Author;
import jdbcUtilities.JDBCConnectionUtil;
import org.junit.Before;
import org.junit.Test;
import testUtilities.DBUtil;

import java.util.Optional;

public class AuthorDaoTest {

    private JDBCConnectionUtil jdbcConnectionUtil;
    private AuthorDao authorDao;

    @Before
    public void init() {
        jdbcConnectionUtil = new JDBCConnectionUtil(
                "postgresql",
                "127.0.0.1",
                5432,
                "Museum",
                "postgres",
                "12439524");
        authorDao = new AuthorDao(jdbcConnectionUtil);
        DBUtil.executeScript("./web_course_project.sql", jdbcConnectionUtil);
    }

    @Test
    public void save() {
        Author author = new Author("test test test");
        authorDao.save(author);
    }

    @Test
    public void update() {
        Author author = new Author(11L, "test test test");
        authorDao.update(author);
    }

    @Test
    public void deleteById() {
        authorDao.deleteById(99L);
    }

    @Test
    public void getAll() {
        Iterable<Author> authors = authorDao.getAll();
        for (Author author : authors) {
            System.out.println(author);
        }
    }

    @Test
    public void getById() {
        Optional<Author> author = authorDao.getById(9L);
        System.out.println(author);
    }

}