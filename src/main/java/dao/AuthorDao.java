package dao;

import domain.*;
import jdbcUtilities.JDBCConnectionUtil;
import lombok.AllArgsConstructor;

import java.sql.*;
import java.util.*;

@AllArgsConstructor
public class AuthorDao implements Dao<Author, Long> {

    private JDBCConnectionUtil jdbcConnectionUtil;

    @Override
    public void save(Author author) {
        try (Connection connection = jdbcConnectionUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "insert into author(full_name) values (?)")) {
            preparedStatement.setString(1, author.getFullName());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Author author) {
        try (Connection connection = jdbcConnectionUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "update author set full_name = (?) where id = (?)")) {
            preparedStatement.setString(1, author.getFullName());
            preparedStatement.setLong(2, author.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteById(Long aLong) {
        try (Connection connection = jdbcConnectionUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "delete from author where id = (?)")) {
            preparedStatement.setLong(1, aLong);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Iterable<Author> getAll() {
        List<Author> result = new LinkedList<>();
        try (Connection connection = jdbcConnectionUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "select * from author");
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                long id = resultSet.getLong(1);
                String fullName = resultSet.getString(2);
                result.add(new Author(id, fullName));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Optional<Author> getById(Long aLong) {
        try (Connection connection = jdbcConnectionUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "select * from author where id = " + aLong,
                     ResultSet.TYPE_SCROLL_SENSITIVE,
                     ResultSet.CONCUR_UPDATABLE);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
                long id = resultSet.getLong(1);
                String fullName = resultSet.getString(2);
                return Optional.of(new Author(id, fullName));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public void attachExhibitToAuthor(Long authorId, Long exhibitId) {
        try (Connection connection = jdbcConnectionUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "insert into author_exhibit(author_id, exhibit_id) values (?, ?)")) {
            preparedStatement.setLong(1, authorId);
            preparedStatement.setLong(2, exhibitId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void detachExhibitFromAuthor(long authorId, long exhibitId) {
        try (Connection connection = jdbcConnectionUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "delete from author_exhibit where author_id = (?) and exhibit_id = (?)")) {
            preparedStatement.setLong(1, authorId);
            preparedStatement.setLong(2, exhibitId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Iterable<AuthorExhibit> getAllAuthorExhibits() {
        List<AuthorExhibit> result = new LinkedList<>();
        try (Connection connection = jdbcConnectionUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "select a.id, a.full_name, e.id, e.name, e.on_exposition, et.id, et.name, m.id, m.name, " +
                             "m.address, m.full_name_of_the_head, m.phone_number, mt.id, mt.name " +
                             "from author_exhibit ae " +
                             "join author a on ae.author_id = a.id " +
                             "join exhibit e on ae.exhibit_id = e.id " +
                             "join exhibit_type et on e.exhibit_type_id = et.id " +
                             "join museum m on e.museum_id = m.id " +
                             "join museum_type mt on m.museum_type_id = mt.id");
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                long authorId = resultSet.getLong(1);
                String authorFullName = resultSet.getString(2);

                long exhibitId = resultSet.getLong(3);
                String exhibitName = resultSet.getString(4);
                boolean exhibitOnExposition = resultSet.getBoolean(5);

                long exhibitTypeId = resultSet.getLong(6);
                String exhibitTypeName = resultSet.getString(7);

                long museumId = resultSet.getLong(8);
                String museumName = resultSet.getString(9);
                String museumAddress = resultSet.getString(10);
                String museumFullNameOfTheHead = resultSet.getString(11);
                String museumPhoneNumber = resultSet.getString(12);

                long museumTypeId = resultSet.getLong(13);
                String museumTypeName = resultSet.getString(14);
                result.add(
                        new AuthorExhibit(
                                new Author(
                                        authorId,
                                        authorFullName),
                                new Exhibit(
                                        exhibitId,
                                        exhibitName,
                                        exhibitOnExposition,
                                        new Museum(
                                                museumId,
                                                museumName,
                                                museumAddress,
                                                museumFullNameOfTheHead,
                                                museumPhoneNumber,
                                                new MuseumType(
                                                        museumTypeId,
                                                        museumTypeName
                                                )
                                        ),
                                        new ExhibitType(
                                                exhibitTypeId,
                                                exhibitTypeName
                                        ))));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}