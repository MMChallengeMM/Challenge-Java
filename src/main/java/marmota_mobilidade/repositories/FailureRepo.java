package marmota_mobilidade.repositories;

import marmota_mobilidade.infrastructure.DatabaseConfig;
import marmota_mobilidade.models.Failure;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

public class FailureRepo implements _CrudRepo<Failure> {
    private final List<Failure> failures = new ArrayList<>();

    @Override
    public void add(Failure object) {
//        failures.add(object);
        var query = "Insert into FALHAS (id, failureType, failureDescription) values (?,?,?)";
        try(var connection = DatabaseConfig.getConnection()) {
            var stmt = connection.prepareStatement(query);
            stmt.setString(1, object.getId().toString());
            stmt.setInt(2, object.getFailureType().getNum());
            stmt.setString(3, object.getFailureDescription());
            stmt.executeUpdate();
        }
        catch(SQLException e) {
            System.out.println("Erro ao inserir coleção");
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
        var query = "Delete from Falhas where id = ?";
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

    @Override
    public void delete(Failure object) {
        failures.remove(object);
    }

    @Override
    public void deleteById(String id) {
        failures.removeIf(f -> f.getId().equals(id));
    }

    @Override
    public List<Failure> getAll() {
        return failures;
    }

    @Override
    public List<Failure> get() {
        return failures.stream()
                .filter(f -> !f.isDeleted())
                .toList();
    }

    @Override
    public Failure getById(String id) {
        return failures.stream()
                .filter(f -> f.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Falha não existe."));
    }
}
