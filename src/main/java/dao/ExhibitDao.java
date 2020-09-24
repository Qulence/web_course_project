package dao;

import domain.Exhibit;
import domain.ExhibitType;
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
public class ExhibitDao implements Dao<Exhibit, Long> {

    private JDBCConnectionUtil jdbcConnectionUtil;

    @Override
    public void save(Exhibit exhibit) {
        try (Connection connection = jdbcConnectionUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "insert into exhibit (name, on_exposition, museum_id, exhibit_type_id) values (?,?,?,?)")) {
            preparedStatement.setString(1, exhibit.getName());
            preparedStatement.setBoolean(2, exhibit.isOnExposition());
            preparedStatement.setLong(3, exhibit.getMuseum().getId());
            preparedStatement.setLong(4, exhibit.getExhibitType().getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Exhibit exhibit) {
        try (Connection connection = jdbcConnectionUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "update exhibit set name = (?), on_exposition = (?), " +
                             "museum_id = (?),exhibit_type_id = (?) where id = (?)")) {
            preparedStatement.setString(1, exhibit.getName());
            preparedStatement.setBoolean(2, exhibit.isOnExposition());
            preparedStatement.setLong(3, exhibit.getMuseum().getId());
            preparedStatement.setLong(4, exhibit.getExhibitType().getId());
            preparedStatement.setLong(5, exhibit.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteById(Long aLong) {
        try (Connection connection = jdbcConnectionUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "delete from exhibit where id = (?)")) {
            preparedStatement.setLong(1, aLong);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Iterable<Exhibit> getAll() {
        List<Exhibit> result = new LinkedList<>();
        try (Connection connection = jdbcConnectionUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "select e.id, e.name, e.on_exposition, m.id, m.name, m.address, m.full_name_of_the_head, " +
                             "m.phone_number, et.id, et.name, mt.id, mt.name from exhibit e " +
                             "join museum m on e.museum_id = m.id " +
                             "join exhibit_type et on e.exhibit_type_id = et.id " +
                             "join museum_type mt on m.museum_type_id = mt.id");
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                long id = resultSet.getLong(1);
                String name = resultSet.getString(2);
                boolean onExposition = resultSet.getBoolean(3);
                long museumId = resultSet.getLong(4);
                String museumName = resultSet.getString(5);
                String museumAddress = resultSet.getString(6);
                String museumFullNameOfTheHead = resultSet.getString(7);
                String museumPhoneNumber = resultSet.getString(8);
                long exhibitTypeId = resultSet.getLong(9);
                String exhibitTypeName = resultSet.getString(10);
                long museumTypeId = resultSet.getLong(11);
                String museumTypeName = resultSet.getString(12);
                result.add(
                        new Exhibit(
                                id,
                                name,
                                onExposition,
                                new Museum(
                                        museumId,
                                        museumName,
                                        museumAddress,
                                        museumFullNameOfTheHead,
                                        museumPhoneNumber,
                                        new MuseumType(
                                                museumTypeId,
                                                museumTypeName)),
                                new ExhibitType(
                                        exhibitTypeId,
                                        exhibitTypeName)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Optional<Exhibit> getById(Long aLong) {
        try (Connection connection = jdbcConnectionUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "select e.id, e.name, e.on_exposition, m.id, m.name, m.address, m.full_name_of_the_head, " +
                             "m.phone_number, et.id, et.name, mt.id, mt.name from exhibit e " +
                             "join museum m on e.museum_id = m.id " +
                             "join exhibit_type et on e.exhibit_type_id = et.id " +
                             "join museum_type mt on m.museum_type_id = mt.id " +
                             "where e.id = " + aLong,
                     ResultSet.TYPE_SCROLL_SENSITIVE,
                     ResultSet.CONCUR_UPDATABLE);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
                long id = resultSet.getLong(1);
                String name = resultSet.getString(2);
                boolean onExposition = resultSet.getBoolean(3);
                long museumId = resultSet.getLong(4);
                String museumName = resultSet.getString(5);
                String museumAddress = resultSet.getString(6);
                String museumFullNameOfTheHead = resultSet.getString(7);
                String museumPhoneNumber = resultSet.getString(8);
                long exhibitTypeId = resultSet.getLong(9);
                String exhibitTypeName = resultSet.getString(10);
                long museumTypeId = resultSet.getLong(11);
                String museumTypeName = resultSet.getString(12);
                return Optional.of(new Exhibit(
                        id,
                        name,
                        onExposition,
                        new Museum(
                                museumId,
                                museumName,
                                museumAddress,
                                museumFullNameOfTheHead,
                                museumPhoneNumber,
                                new MuseumType(
                                        museumTypeId,
                                        museumTypeName)),
                        new ExhibitType(
                                exhibitTypeId,
                                exhibitTypeName)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

}