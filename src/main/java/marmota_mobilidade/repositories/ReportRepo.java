package marmota_mobilidade.repositories;

import marmota_mobilidade.infrastructure.DatabaseConfig;
import marmota_mobilidade.models.REPORT_TYPE;
import marmota_mobilidade.models.Report;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ReportRepo implements _CrudRepo<Report> {

    private static final Logger LOGGER = LogManager.getLogger(ReportRepo.class);

    public void add(Report object) {

        LOGGER.info("Criando relatório {}", object.getId());

        var query = "INSERT INTO RELATORIOS (id, reportType, generationDate, numberOfFailures, reportData) VALUES (?,?,?,?,?)";

        try (var connection = DatabaseConfig.getConnection()) {

            var stmt = connection.prepareStatement(query);

            stmt.setString(1, object.getId().toString());
            stmt.setInt(2, object.getReportType().getNum());
            stmt.setTimestamp(3, Timestamp.valueOf(object.getGenerationDate()));
            stmt.setInt(4, object.getNumberOfFailures());
            stmt.setString(5, object.getReportData());
            stmt.executeUpdate();

            LOGGER.info("Relatório adicionado com sucesso: {}", object.getId());

        } catch (SQLException e) {
            LOGGER.error("Erro ao adicionar relatório: {}", e.getMessage());
            System.out.println("Erro ao inserir relatório");
        }
    }

    public void remove(Report object) {
        LOGGER.info("Removendo relatório: {}", object.getId());
        var id = object.getId();
        removeById(id);
    }

    public void removeById(UUID id) {

        var query = "Update Relatorios set deleted = ? where id = ?";

        LOGGER.info("Removendo relatório pelo id: {}", id);

        try (var connection = DatabaseConfig.getConnection()) {
            var stmt = connection.prepareStatement(query);
            stmt.setInt(1, 1);
            stmt.setString(2, id.toString());
            var result = stmt.executeUpdate();

            if (result == 1) {
                LOGGER.info("Relatório removido com sucesso: {}", id);
                System.out.println("Relatorio removido.");
            } else {
                LOGGER.warn("Relatório não encontrado para remoção: {}", id);
                System.out.println("Relatorio não encontrado");
            }
        } catch (SQLException e) {
            LOGGER.error("Erro ao remover relatório: {}", e.getMessage());
            System.out.println("Erro ao remover relatório");
        }
    }

    public void delete(Report object) {
        LOGGER.info("Deletando relatório: {}", object.getId());
        var id = object.getId();
        deleteById(id);
    }

    public void deleteById(UUID id) {
        var query = "Delete from Relatorios where id = ?";

        LOGGER.info("Deletando relatório pelo id: {}", id);

        try (var connection = DatabaseConfig.getConnection()) {
            var stmt = connection.prepareStatement(query);
            stmt.setString(1, id.toString());
            var result = stmt.executeUpdate();

            if (result == 1) {
                LOGGER.info("Relatório deletado com sucesso: {}", id);
                System.out.println("Relatorio deletado");
            } else {
                LOGGER.warn("Relatório não encontrado para a deleção: {}", id);
                System.out.println("Relatorio não encontrado");
            }
        } catch (SQLException e) {
            LOGGER.error("Erro ao deletar relatório: {}", e.getMessage());
            System.out.println("Erro ao deletar relatório");
        }
    }

    public List<Report> getAll() {
        var reportList = new ArrayList<Report>();
        var query = "SELECT * FROM relatorios";

        LOGGER.info("Buscando todos os relatórios no banco de dados");

        try (var connection = DatabaseConfig.getConnection()) {
            var stmt = connection.createStatement();
            var result = stmt.executeQuery(query);

            while (result.next()) {
                var report = Report.builder()
                        .id(UUID.fromString(result.getString("id")))
                        .deleted(result.getBoolean("deleted"))
                        .reportData(result.getString("reporData"))
                        .reportType(REPORT_TYPE.fromNumber(result.getInt("reportType")))
                        .generationDate(result.getTimestamp("generationDate").toLocalDateTime())
                        .numberOfFailures(result.getInt("numberOfFailures"))
                        .build();
                reportList.add(report);
            }
            
            LOGGER.info("Todas os relatórios recuperados do banco de dados com sucesso");

        } catch (SQLException e) {
            LOGGER.error("Erro ao recuperar relatórios: {}", e.getMessage());
            System.out.println("Erro ao recuperar relatórios");
        }
        return reportList;
    }

    public List<Report> get() {

        var reportList = new ArrayList<Report>();
        var query = "SELECT * FROM relatorios WHERE DELETED = 0";

        LOGGER.info("Buscando relatórios não apagados no banco de dados.");

        try (var connection = DatabaseConfig.getConnection()) {
            var stmt = connection.createStatement();
            var result = stmt.executeQuery(query);

            while (result.next()) {
                var report = Report.builder()
                        .id(UUID.fromString(result.getString("id")))
                        .deleted(result.getBoolean("deleted"))
                        .reportData(result.getString("reportData"))
                        .reportType(REPORT_TYPE.fromNumber(result.getInt("reportType")))
                        .generationDate(result.getTimestamp("generationDate").toLocalDateTime())
                        .numberOfFailures(result.getInt("numberOfFailures"))
                        .build();
                reportList.add(report);
            }

            LOGGER.info("Relatórios não apagados encontrados.");

        } catch (SQLException e) {
            LOGGER.error("Erro ao recuperar relatório: {}", e.getMessage());
            System.out.println("Erro ao recuperar relatório");
        }
        return reportList;
    }

    public Report getById(UUID id) {

        var query = "SELECT * FROM Relatorios WHERE id = ? AND deleted = 0";

        LOGGER.info("Buscando relatório por id: {}", id);

        try (var connection = DatabaseConfig.getConnection();
             var stmt = connection.prepareStatement(query)) {

            stmt.setString(1, id.toString());

            var result = stmt.executeQuery();

            if (result.next()) {
                var report = Report.builder()
                        .id(UUID.fromString(result.getString("id")))
                        .deleted(result.getInt("deleted") == 1)
                        .reportType(REPORT_TYPE.fromNumber(result.getInt("reportType")))
                        .generationDate(result.getTimestamp("generationDate").toLocalDateTime())
                        .numberOfFailures(result.getInt("numberOfFailures"))
                        .reportData(result.getString("reportData"))
                        .build();
                        
                LOGGER.info("Relatório por id encontrado: {}", id);
                return report;
            } else {
                LOGGER.warn("Relatório não encontrado: {}", id);
                System.out.println("Relatório não encontrado.");
            }

        } catch (SQLException e) {
            LOGGER.error("Erro ao recuperar relatório especifico: {}", e.getMessage());
            System.out.println("Erro ao recuperar relatório");
        }
        return null;
    }
}
