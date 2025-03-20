package marmota_mobilidade.repositories;

import marmota_mobilidade.infrastructure.DatabaseConfig;
import marmota_mobilidade.models.Admin;
import marmota_mobilidade.models.Operator;
import marmota_mobilidade.models.USER_SHIFT;
import marmota_mobilidade.models.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UserRepo implements _CrudRepo<User> {

    private static final Logger LOGGER = LogManager.getLogger(ReportRepo.class);
    
    public void add(User user) {

        String query = "INSERT INTO Usuarios (id, deleted, name, userShift, accessLevel, sector) VALUES (?, ?, ?, ?, ?, ?)";

        LOGGER.info("Criando usuário: {}", user.getId());

        try (var connection = DatabaseConfig.getConnection();
             var stmt = connection.prepareStatement(query)) {

            stmt.setString(1, user.getId().toString());
            stmt.setInt(2, user.isDeleted() ? 1 : 0);
            stmt.setString(3, user.getName());
            stmt.setInt(4, user.getUserShift().getNum());
            stmt.setInt(5, (user instanceof Admin) ? ((Admin) user).getAccessLevel() : 0);
            stmt.setString(6, (user instanceof Operator) ? ((Operator) user).getSector() : null);

            stmt.executeUpdate();
            
            LOGGER.info("Usuário adicionado com sucesso: {}", user.getId());

        } catch (SQLException e) {
            LOGGER.warn("Erro ao criar o usuário: {}", user.getId());
            System.out.println("Erro ao criar o usuário");
        }
    }

    
    public void remove(User user) {
        LOGGER.info("Removendo usuário: {}", user.getId());
        removeById(user.getId());
    }

    
    public void removeById(UUID id) {

        String query = "UPDATE Usuarios SET deleted = ? WHERE id = ?";

        LOGGER.info("Removendo usuário por id: {}", id);

        try (var connection = DatabaseConfig.getConnection();
             var stmt = connection.prepareStatement(query)) {

            stmt.setInt(1, 1);
            stmt.setString(2, id.toString());
            int result = stmt.executeUpdate();

            if (result == 1) {
                LOGGER.info("Usuário removido com sucesso: {}", id);
                System.out.println("Usuário removido com sucesso.");
            } else {
                LOGGER.warn("Usuário não encontrado para remoção: {}", id);
                System.out.println("Usuário não encontrado.");
            }
        } catch (SQLException e) {
            LOGGER.error("Erro ao remover usuário: {}", id);
            System.out.println("Erro ao remover o usuário");
        }
    }

    
    public void delete(User user) {
        LOGGER.info("Deletando usuário: {}", user.getId());
        deleteById(user.getId());
    }

    
    public void deleteById(UUID id) {

        String query = "DELETE FROM Usuarios WHERE id = ?";

        LOGGER.info("Deletando usuário por id: {}", id);

        try (var connection = DatabaseConfig.getConnection();
             var stmt = connection.prepareStatement(query)) {

            stmt.setString(1, id.toString());
            int result = stmt.executeUpdate();

            if (result == 1) {
                LOGGER.info("Usuário deletado com sucesso: {}", id);
                System.out.println("Usuário deletado com sucesso.");
            } else {
                LOGGER.warn("Usuário não encontrado para deleção: {}", id);
                System.out.println("Usuário não encontrado.");
            }
        } catch (SQLException e) {
            LOGGER.error("Erro ao deletar usuário: {}", id);
            System.out.println("Erro ao deletar o usuário.");
        }
    }

    
    public List<User> getAll() {

        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM Usuarios";
        
        LOGGER.info("Buscando todos os usuários no banco de dados");

        try (var connection = DatabaseConfig.getConnection();
             var stmt = connection.createStatement();
             var result = stmt.executeQuery(query)) {

            while (result.next()) {
                UUID id = UUID.fromString(result.getString("id"));
                boolean deleted = result.getInt("deleted") == 1;
                String name = result.getString("name");
                var userShift = USER_SHIFT.fromNumber(result.getInt("userShift"));
                int accessLevel = result.getInt("accessLevel");
                String sector = result.getString("sector");

                User user;
                if (sector != null) {
                    user = Operator.builder()
                            .id(id)
                            .deleted(deleted)
                            .name(name)
                            .userShift(userShift)
                            .sector(sector)
                            .build();
                } else {
                    user = Admin.builder()
                            .id(id)
                            .deleted(deleted)
                            .name(name)
                            .userShift(userShift)
                            .accessLevel(accessLevel)
                            .build();
                }
                users.add(user);

                LOGGER.info("Todas os usuários recuperados do banco de dados com sucesso");
            }
        } catch (SQLException e) {
            LOGGER.error("Erro ao recuperar usuários: {}", e.getMessage());
            System.out.println("Erro ao recuperar os usuários");
        }
        return users;
    }

    
    public List<User> get() {

        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM Usuarios WHERE deleted = 0";

        LOGGER.info("Buscando usuários não apagados no banco de dados.");

        try (var connection = DatabaseConfig.getConnection();
             var stmt = connection.createStatement();
             var result = stmt.executeQuery(query)) {

            while (result.next()) {
                UUID id = UUID.fromString(result.getString("id"));
                boolean deleted = result.getInt("deleted") == 1;
                String name = result.getString("name");
                USER_SHIFT userShift = USER_SHIFT.fromNumber(result.getInt("userShift"));
                int accessLevel = result.getInt("accessLevel");
                String sector = result.getString("sector");

                User user;
                if (sector != null) {
                    user = Operator.builder()
                            .id(id)
                            .deleted(deleted)
                            .name(name)
                            .userShift(userShift)
                            .sector(sector)
                            .build();
                } else {
                    user = Admin.builder()
                            .id(id)
                            .deleted(deleted)
                            .name(name)
                            .userShift(userShift)
                            .accessLevel(accessLevel)
                            .build();
                }
                
                LOGGER.info("Usuários não apagados encontrados.");
                users.add(user);
            }
        } catch (SQLException e) {
            LOGGER.error("Erro ao recuperar usuários ativos: {}", e.getMessage());
            System.out.println("Erro ao recuperar os usuários ativos.");
        }
        return users;
    }

    
    public User getById(UUID id) {

        String query = "SELECT * FROM Usuarios WHERE id = ?";

        LOGGER.info("Buscando usuário por id: {}", id);

        try (var connection = DatabaseConfig.getConnection();
             var stmt = connection.prepareStatement(query)) {

            stmt.setString(1, id.toString());
            var result = stmt.executeQuery();

            if (result.next()) {
                boolean deleted = result.getInt("deleted") == 1;
                String name = result.getString("name");
                USER_SHIFT userShift = USER_SHIFT.fromNumber(result.getInt("userShift"));
                int accessLevel = result.getInt("accessLevel");
                String sector = result.getString("sector");

                User user;
                if (sector != null) {
                    user = Operator.builder()
                            .id(id)
                            .deleted(deleted)
                            .name(name)
                            .userShift(userShift)
                            .sector(sector)
                            .build();
                } else {
                    user = Admin.builder()
                            .id(id)
                            .deleted(deleted)
                            .name(name)
                            .userShift(userShift)
                            .accessLevel(accessLevel)
                            .build();
                }
                
                LOGGER.info("Usuário encontrado por id: {}", id);
                return user;
            } else {
                LOGGER.warn("Usuário não encontrado por id: {}", id);
                System.out.println("Usuário não encontrado.");
            }
        } catch (SQLException e) {
            LOGGER.error("Erro ao recuperar usuário especifico: {}", e.getMessage());
            System.out.println("Erro ao recuperar usuário");
        }
        return null;
    }
}
