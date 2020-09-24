package dao;

import domain.ExhibitType;
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
public class ExhibitTypeDao implements Dao<ExhibitType, Long> {

    private JDBCConnectionUtil jdbcConnectionUtil;

    @Override
    public void save(ExhibitType exhibitType) {
        try (Connection connection = jdbcConnectionUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "insert into exhibit_type(name) values (?)")) {
            preparedStatement.setString(1, exhibitType.getName());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(ExhibitType exhibitType) {
        try (Connection connection = jdbcConnectionUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "update exhibit_type set name = (?) where id = (?)")) {
            preparedStatement.setString(1, exhibitType.getName());
            preparedStatement.setLong(2, exhibitType.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteById(Long aLong) {
        try (Connection connection = jdbcConnectionUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "delete from exhibit_type where id = (?)")) {
            preparedStatement.setLong(1, aLong);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Iterable<ExhibitType> getAll() {
        List<ExhibitType> result = new LinkedList<>();
        try (Connection connection = jdbcConnectionUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "select * from exhibit_type");
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                long id = resultSet.getLong(1);
                String name = resultSet.getString(2);
                result.add(new ExhibitType(id, name));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Optional<ExhibitType> getById(Long aLong) {
        try (Connection connection = jdbcConnectionUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "select * from exhibit_type where id = " + aLong,
                     ResultSet.TYPE_SCROLL_SENSITIVE,
                     ResultSet.CONCUR_UPDATABLE);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
                long id = resultSet.getLong(1);
                String name = resultSet.getString(2);
                return Optional.of(new ExhibitType(id, name));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

}