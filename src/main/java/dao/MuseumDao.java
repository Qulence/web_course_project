package dao;

import domain.Museum;
import domain.MuseumType;
import jdbcUtilities.JDBCConnectionUtil;
import lombok.AllArgsConstructor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class MuseumDao implements Dao<Museum, Long> {

    private JDBCConnectionUtil jdbcConnectionUtil;

    @Override
    public void save(Museum museum) {
        try (Connection connection = jdbcConnectionUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "INSERT INTO museum(name, address, full_name_of_the_head, phone_number, museum_type_id) " +
                             "VALUES(?,?,?,?,?)")) {
            preparedStatement.setString(1, museum.getName());
            preparedStatement.setString(2, museum.getAddress());
            preparedStatement.setString(3, museum.getFullNameOfTheHead());
            preparedStatement.setString(4, museum.getPhoneNumber());
            preparedStatement.setLong(5, museum.getMuseumType().getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Museum museum) {
        try (Connection connection = jdbcConnectionUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "UPDATE museum SET name = (?), address = (?), full_name_of_the_head = (?)," +
                             " phone_number = (?), museum_type_id = (?) WHERE id = (?)")) {
            preparedStatement.setString(1, museum.getName());
            preparedStatement.setString(2, museum.getAddress());
            preparedStatement.setString(3, museum.getFullNameOfTheHead());
            preparedStatement.setString(4, museum.getPhoneNumber());
            preparedStatement.setLong(5, museum.getMuseumType().getId());
            preparedStatement.setLong(6, museum.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteById(Long aLong) {
        try (Connection connection = jdbcConnectionUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "DELETE FROM museum WHERE id = (?)")) {
            preparedStatement.setLong(1, aLong);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Iterable<Museum> getAll() {
        List<Museum> result = new LinkedList<>();
        try (Connection connection = jdbcConnectionUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT * FROM museum m JOIN museum_type mt ON m.museum_type_id = mt.id");
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                long id = resultSet.getLong(1);
                String name = resultSet.getString(2);
                String address = resultSet.getString(3);
                String fullNameOfTheHead = resultSet.getString(4);
                String phoneNumber = resultSet.getString(5);
                long museumTypeId = resultSet.getLong(6);
                String museumTypeName = resultSet.getString(8);
                result.add(new Museum(
                        id, name, address, fullNameOfTheHead, phoneNumber,
                        new MuseumType(museumTypeId, museumTypeName)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Optional<Museum> getById(Long aLong) {
        try (Connection connection = jdbcConnectionUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "select * from museum m join museum_type mt on m.museum_type_id = mt.id where m.id = " + aLong,
                     ResultSet.TYPE_SCROLL_SENSITIVE,
                     ResultSet.CONCUR_UPDATABLE);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
                long id = resultSet.getLong(1);
                String name = resultSet.getString(2);
                String address = resultSet.getString(3);
                String fullNameOfTheHead = resultSet.getString(4);
                String phoneNumber = resultSet.getString(5);
                long museumTypeId = resultSet.getLong(6);
                String museumTypeName = resultSet.getString(8);
                return Optional.of(new Museum(
                        id, name, address, fullNameOfTheHead, phoneNumber,
                        new MuseumType(museumTypeId, museumTypeName)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
}