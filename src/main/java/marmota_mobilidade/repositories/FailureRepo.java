package marmota_mobilidade.repositories;

import marmota_mobilidade.infrastructure.DatabaseConfig;
import marmota_mobilidade.models.FAILURE_STATUS;
import marmota_mobilidade.models.FAILURE_TYPE;
import marmota_mobilidade.models.Failure;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

public class FailureRepo implements _CrudRepo<Failure> {

    @Override
    public void add(Failure object) {
//        failures.add(object);
        var query = "Insert into FALHAS (id, failureType, failureDescription) values (?,?,?)";
        try (var connection = DatabaseConfig.getConnection()) {
            var stmt = connection.prepareStatement(query);
            stmt.setString(1, object.getId().toString());
            stmt.setInt(2, object.getFailureType().getNum());
            stmt.setString(3, object.getFailureDescription());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erro ao inserir falha");
        }
    }

    @Override
    public void remove(Failure object) {
//        failures.stream()
//                .filter(f -> f == object)
//                .findFirst()
//                .ifPresent(f -> f.setDeleted(true));
        var id = object.getId();
        removeById(id);
    }

    @Override
    public void removeById(UUID id) {
//        failures.stream()
//                .filter(f -> f.getId().equals(id))
//                .findFirst()
//                .ifPresent(f -> f.setDeleted(true));
        var query = "Update FALHAS set deleted = ? where id = ?";
        try (var connection = DatabaseConfig.getConnection()) {
            var stmt = connection.prepareStatement(query);
            stmt.setInt(1, 1);
            stmt.setString(2, id.toString());
            var result = stmt.executeUpdate();

            if (result == 1) {
                System.out.println("Falha removida");
            } else {
                System.out.println("Falha não encontrada");
            }
        } catch (SQLException e) {
            System.out.println("Errinho sql");
        }
    }

    @Override
    public void delete(Failure object) {
//        failures.remove(object);
        var id = object.getId();
        deleteById(id);
    }

    @Override
    public void deleteById(UUID id) {
//        failures.removeIf(f -> f.getId().equals(id));
        var query = "Delete from FALHAS where id = ?";
        try (var connection = DatabaseConfig.getConnection()) {
            var stmt = connection.prepareStatement(query);
            stmt.setString(1, id.toString());
            var result = stmt.executeUpdate();

            if (result == 1) {
                System.out.println("Falha deletada");
            } else {
                System.out.println("Falha não encontrada");
            }
        } catch (SQLException e) {
            System.out.println("Errinho sql");
        }
    }

    public List<Failure> getAll() {
//        return failures;

        var failureList = new ArrayList<Failure>();
        var query = "SELECT * FROM FALHAS";
        try (var connection = DatabaseConfig.getConnection()) {
            var stmt = connection.createStatement();
            var result = stmt.executeQuery(query);

            while (result.next()) {
                var falha = Failure.builder().id(UUID.fromString(result.getString("id"))).deleted(result.getBoolean("deleted")).registrationDate(result.getTimestamp("registrationDate").toLocalDateTime()).failureType(FAILURE_TYPE.fromNumber(result.getInt("failureType"))).failureDescription(result.getString("failureDescription")).failureStatus(FAILURE_STATUS.fromNumber(result.getInt("failureStatus"))).onGeneralReport(result.getBoolean("onGeneralReport")).build();
                failureList.add(falha);
            }
        } catch (SQLException e) {
            System.out.println("WLELEE");
        }
        return failureList;
    }

    public List<Failure> get() {
//        return failures.stream().filter(f -> !f.isDeleted()).toList();
        var failureList = new ArrayList<Failure>();
        var query = "SELECT * FROM FALHAS WHERE deleted = 0";
        try (var connection = DatabaseConfig.getConnection()) {
            var stmt = connection.createStatement();
            var result = stmt.executeQuery(query);

            while (result.next()) {
                var falha = Failure.builder().id(UUID.fromString(result.getString("id"))).deleted(result.getBoolean("deleted")).registrationDate(result.getTimestamp("registrationDate").toLocalDateTime()).failureType(FAILURE_TYPE.fromNumber(result.getInt("failureType"))).failureDescription(result.getString("failureDescription")).failureStatus(FAILURE_STATUS.fromNumber(result.getInt("failureStatus"))).onGeneralReport(result.getBoolean("onGeneralReport")).build();
                failureList.add(falha);
            }
        } catch (SQLException e) {
            System.out.println("WLELEE");
        }
        return failureList;
    }

    public Failure getById(UUID id) {
        var query = "SELECT * FROM FALHAS WHERE id = ?";

        try (var connection = DatabaseConfig.getConnection();
             var stmt = connection.prepareStatement(query)) {

            stmt.setString(1, id.toString());
            var result = stmt.executeQuery();

            if (result.next()) {
                return Failure.builder()
                        .id(UUID.fromString(result.getString("id")))
                        .deleted(result.getBoolean("deleted"))
                        .registrationDate(result.getTimestamp("registrationDate").toLocalDateTime())
                        .failureType(FAILURE_TYPE.fromNumber(result.getInt("failureType")))
                        .failureDescription(result.getString("failureDescription"))
                        .failureStatus(FAILURE_STATUS.fromNumber(result.getInt("failureStatus")))
                        .onGeneralReport(result.getBoolean("onGeneralReport"))
                        .build();
            } else {
                throw new NoSuchElementException("Falha não existe.");
            }

        } catch (SQLException e) {
            e.printStackTrace(); // OU use um sistema de logging como Log4j
            throw new RuntimeException("Erro ao buscar falha no banco de dados.", e);
        }
    }
}

