package dao;

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
public class MuseumTypeDao implements Dao<MuseumType, Long> {

    private JDBCConnectionUtil jdbcConnectionUtil;

    @Override
    public void save(MuseumType museumType) {
        try (Connection connection = jdbcConnectionUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "insert into museum_type(name) values (?)")) {
            preparedStatement.setString(1, museumType.getName());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(MuseumType museumType) {
        try (Connection connection = jdbcConnectionUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "update museum_type set name = (?) where id = (?)")) {
            preparedStatement.setString(1, museumType.getName());
            preparedStatement.setLong(2, museumType.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteById(Long aLong) {
        try (Connection connection = jdbcConnectionUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "delete from museum_type where id = (?)")) {
            preparedStatement.setLong(1, aLong);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Iterable<MuseumType> getAll() {
        List<MuseumType> result = new LinkedList<>();
        try (Connection connection = jdbcConnectionUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "select * from museum_type");
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                long id = resultSet.getLong(1);
                String name = resultSet.getString(2);
                result.add(new MuseumType(id, name));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Optional<MuseumType> getById(Long aLong) {
        try (Connection connection = jdbcConnectionUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "select * from museum_type where id = " + aLong,
                     ResultSet.TYPE_SCROLL_SENSITIVE,
                     ResultSet.CONCUR_UPDATABLE);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
                long id = resultSet.getLong(1);
                String name = resultSet.getString(2);
                return Optional.of(new MuseumType(id, name));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

}