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
//        reports.add(object);

        var query1 = "INSERT INTO RELATORIOS (id, reportType, generationDate, numberOfFailures, reportData) VALUES (?,?,?,?,?)";

        try (var connection = DatabaseConfig.getConnection()) {
            connection.setAutoCommit(false);

            try (var stmt1 = connection.prepareStatement(query1)) {

                // Inserção na tabela RELATORIOS
                stmt1.setString(1, object.getId().toString());
                stmt1.setInt(2, object.getReportType().getNum());
                stmt1.setTimestamp(3, Timestamp.valueOf(object.getGenerationDate()));
                stmt1.setInt(4, object.getNumberOfFailures());
                stmt1.setString(5, object.getReportData());
                stmt1.executeUpdate();
            }

            connection.commit();
        } catch (SQLException e) {
            LOGGER.error("Erro ao adicionar relatório: {}", e.getMessage());
            System.out.println("Erro ao inserir relatório");
        }
    }

    public void remove(Report object) {
//        reports.stream()
//                .filter(r -> r == object)
//                .findFirst()
//                .ifPresent(r -> r.setDeleted(true));
        var id = object.getId();
        removeById(id);
    }

    public void removeById(UUID id) {
//        reports.stream()
//                .filter(r -> r.getId().equals(id))
//                .findFirst()
//                .ifPresent(r -> r.setDeleted(true));
        var query = "Update Relatorios set deleted = ? where id = ?";
        try (var connection = DatabaseConfig.getConnection()) {
            var stmt = connection.prepareStatement(query);
            stmt.setInt(1, 1);
            stmt.setString(2, id.toString());
            var result = stmt.executeUpdate();

            if (result == 1) {
                System.out.println("Relatorio removido");
            } else {
                System.out.println("Relatorio não encontrado");
            }
        } catch (SQLException e) {
            LOGGER.error("Erro ao remover relatório: {}", e.getMessage());
            System.out.println("Erro ao remover relatório");
        }
    }

    public void delete(Report object) {
//        reports.remove(object);
        var id = object.getId();
        deleteById(id);
    }

    public void deleteById(UUID id) {
//        reports.removeIf(r -> r.getId().equals(id));
        var query = "Delete from Realtorios where id = ?";
        try (var connection = DatabaseConfig.getConnection()) {
            var stmt = connection.prepareStatement(query);
            stmt.setString(1, id.toString());
            var result = stmt.executeUpdate();

            if (result == 1) {
                System.out.println("Relatorio deletado");
            } else {
                System.out.println("Realtorio não encontrado");
            }
        } catch (SQLException e) {
            LOGGER.error("Erro ao deletar relatório: {}", e.getMessage());
            System.out.println("Erro ao deletar relatório");
        }
    }

    @Override
    public List<Report> getAll() {
        var reportList = new ArrayList<Report>();
        var query = "SELECT * FROM relatorios";
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
        } catch (SQLException e) {
            LOGGER.error("Erro ao recuperar relatórios: {}", e.getMessage());
            System.out.println("Erro ao recuperar relatórios");
        }
        return reportList;
    }

    @Override
    public List<Report> get() {
//        return reports.stream()
//                .filter(r -> !r.isDeleted())
//                .toList();

        var reportList = new ArrayList<Report>();
        var query = "SELECT * FROM relatorios WHERE DELETED = 0";
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
        } catch (SQLException e) {
            LOGGER.error("Erro ao recuperar relatório: {}", e.getMessage());
            System.out.println("Erro ao recuperar relatório");
        }
        return reportList;
    }

    public Report getById(UUID id) {
//        return reports.stream()
//                .filter(r -> r.getId().equals(id))
//                .findFirst()
//                .orElseThrow(() -> new NoSuchElementException("Falha não existe."));
        var query = "SELECT * FROM Relatorios WHERE id = ? AND deleted = 0";
        try (var connection = DatabaseConfig.getConnection();
             var stmt = connection.prepareStatement(query)) {

            stmt.setString(1, id.toString());

            var result = stmt.executeQuery();

            if (result.next()) {
                return Report.builder()
                        .id(UUID.fromString(result.getString("id")))
                        .deleted(result.getInt("deleted") == 1)
                        .reportType(REPORT_TYPE.fromNumber(result.getInt("reportType")))
                        .generationDate(result.getTimestamp("generationDate").toLocalDateTime())
                        .numberOfFailures(result.getInt("numberOfFailures"))
                        .reportData(result.getString("reportData"))
                        .build();
            } else {
                System.out.println("Relatório n]ao encontrado.");
            }

        } catch (SQLException e) {
            LOGGER.error("Erro ao recuperar relatório especifico: {}", e.getMessage());
            System.out.println("Erro ao recuperar relatório");
        }
        return null;
    }
}
