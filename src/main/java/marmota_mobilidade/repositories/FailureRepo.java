package marmota_mobilidade.repositories;

import marmota_mobilidade.infrastructure.DatabaseConfig;
import marmota_mobilidade.models.FAILURE_STATUS;
import marmota_mobilidade.models.FAILURE_TYPE;
import marmota_mobilidade.models.Failure;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FailureRepo implements _CrudRepo<Failure> {

    private static final Logger LOGGER = LogManager.getLogger(FailureRepo.class);
    
    public void add(Failure object) {

        var query = "Insert into FALHAS (id, failureType, failureDescription, registrationDate) values (?,?,?,?)";
        
        LOGGER.info("Criando falha: {}", object.getId());

        try (var connection = DatabaseConfig.getConnection()) {
            var stmt = connection.prepareStatement(query);
            stmt.setString(1, object.getId().toString());
            stmt.setInt(2, object.getFailureType().getNum());
            stmt.setString(3, object.getFailureDescription());
            stmt.setTimestamp(4, Timestamp.valueOf(object.getRegistrationDate()));
            stmt.executeUpdate();

            LOGGER.info("Falha adicionada com sucesso: {}", object.getId());

        } catch (SQLException e) {
            LOGGER.error("Erro ao adicionar falha: {}", e.getMessage());
            System.out.println("Erro ao inserir falha");
        }
    }

    public void remove(Failure object) {
        LOGGER.info("Removendo falha: {}", object.getId());
        var id = object.getId();
        removeById(id);
    }

    public void removeById(UUID id) {

        var query = "Update FALHAS set deleted = ? where id = ?";
        
        LOGGER.info("Removendo falha pelo id: {}", id);

        try (var connection = DatabaseConfig.getConnection()) {
            var stmt = connection.prepareStatement(query);
            stmt.setInt(1, 1);
            stmt.setString(2, id.toString());
            var result = stmt.executeUpdate();

            if (result == 1) {
                LOGGER.info("Falha removida com sucesso: {}", id);
                System.out.println("Falha removida");
            } else {
                LOGGER.warn("Falha não encontrada para remoção: {}", id);
                System.out.println("Falha não encontrada");
            }
        } catch (SQLException e) {
            LOGGER.error("Erro ao remover falha: {}", e.getMessage());
            System.out.println("Erro ao remover falha");
        }
    }

    public void delete(Failure object) {
        LOGGER.info("Deletando falha: {}", object.getId());
        var id = object.getId();
        deleteById(id);
    }

    public void deleteById(UUID id) {

        var query = "Delete from FALHAS where id = ?";
        
        LOGGER.info("Deletando falha pelo id: {}", id);

        try (var connection = DatabaseConfig.getConnection()) {
            var stmt = connection.prepareStatement(query);
            stmt.setString(1, id.toString());
            var result = stmt.executeUpdate();

            if (result == 1) {
                LOGGER.info("Falha deletada com sucesso: {}", id);
                System.out.println("Falha deletada");
            } else {
                LOGGER.warn("Falha não encontrada: {}", id);
                System.out.println("Falha não encontrada");
            }
        } catch (SQLException e) {
            LOGGER.error("Erro ao deletar falha: {}", e.getMessage());
            System.out.println("Erro ao deletar falha");
        }
    }

    public List<Failure> getAll() {

        var failureList = new ArrayList<Failure>();
        var query = "SELECT * FROM FALHAS";
        
        LOGGER.info("Buscando todos as falhas no banco de dados");

        try (var connection = DatabaseConfig.getConnection()) {
            var stmt = connection.createStatement();
            var result = stmt.executeQuery(query);

            while (result.next()) {
                var falha = Failure.builder().id(UUID.fromString(result.getString("id"))).deleted(result.getBoolean("deleted")).registrationDate(result.getTimestamp("registrationDate").toLocalDateTime()).failureType(FAILURE_TYPE.fromNumber(result.getInt("failureType"))).failureDescription(result.getString("failureDescription")).failureStatus(FAILURE_STATUS.fromNumber(result.getInt("failureStatus"))).onGeneralReport(result.getBoolean("onGeneralReport")).build();
                failureList.add(falha);
            }
            
            LOGGER.info("Todas as falhas recuperadas do banco de dados com sucesso");

        } catch (SQLException e) {
            LOGGER.error("Erro ao recuperar falhas: {}", e.getMessage());
            System.out.println("Erro ao recuperar falhas");
        }
        return failureList;
    }

    public List<Failure> get() {

        var failureList = new ArrayList<Failure>();
        var query = "SELECT * FROM FALHAS WHERE deleted = 0";
        
        LOGGER.info("Buscando falhas não apagadas no banco de dados.");

        try (var connection = DatabaseConfig.getConnection()) {
            var stmt = connection.createStatement();
            var result = stmt.executeQuery(query);

            while (result.next()) {
                var falha = Failure.builder().id(UUID.fromString(result.getString("id"))).deleted(result.getBoolean("deleted")).registrationDate(result.getTimestamp("registrationDate").toLocalDateTime()).failureType(FAILURE_TYPE.fromNumber(result.getInt("failureType"))).failureDescription(result.getString("failureDescription")).failureStatus(FAILURE_STATUS.fromNumber(result.getInt("failureStatus"))).onGeneralReport(result.getBoolean("onGeneralReport")).build();
                failureList.add(falha);
            }
            
            LOGGER.info("Falhas não apagados encontrados.");

        } catch (SQLException e) {
            LOGGER.error("Erro ao recuperar falhas: {}", e.getMessage());
            System.out.println("Erro ao recuperar falhas");
        }
        return failureList;
    }

    public Failure getById(UUID id) {
        var query = "SELECT * FROM FALHAS WHERE id = ?";
        
        LOGGER.info("Buscando falha por id: {}", id);

        try (var connection = DatabaseConfig.getConnection();
             var stmt = connection.prepareStatement(query)) {

            stmt.setString(1, id.toString());
            var result = stmt.executeQuery();

            if (result.next()) {
                var failure = Failure.builder()
                        .id(UUID.fromString(result.getString("id")))
                        .deleted(result.getBoolean("deleted"))
                        .registrationDate(result.getTimestamp("registrationDate").toLocalDateTime())
                        .failureType(FAILURE_TYPE.fromNumber(result.getInt("failureType")))
                        .failureDescription(result.getString("failureDescription"))
                        .failureStatus(FAILURE_STATUS.fromNumber(result.getInt("failureStatus")))
                        .onGeneralReport(result.getBoolean("onGeneralReport"))
                        .build();
                        
                LOGGER.info("Falha por id encontrado: {}", id);

                return failure;
            } else {
                LOGGER.warn("Falha não encontrado: {}", id);
                System.out.println("Falha não encontrado.");
            }

        } catch (SQLException e) {
            LOGGER.error("Erro ao recuperar falha especifico: {}", e.getMessage());
            System.out.println("Erro ao recuperar falha");
        }
        return null;
    }
}

