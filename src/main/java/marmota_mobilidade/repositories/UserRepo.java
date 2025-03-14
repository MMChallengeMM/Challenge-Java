package marmota_mobilidade.repositories;

import marmota_mobilidade.infrastructure.DatabaseConfig;
import marmota_mobilidade.models.Admin;
import marmota_mobilidade.models.Operator;
import marmota_mobilidade.models.USER_SHIFT;
import marmota_mobilidade.models.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

public class UserRepo implements _CrudRepo<User> {

    @Override
    public void add(User user) {
//        users.add(object);
        String query = "INSERT INTO Usuarios (id, deleted, name, userShift, accessLevel, sector) VALUES (?, ?, ?, ?, ?, ?)";
        try (var connection = DatabaseConfig.getConnection();
             var stmt = connection.prepareStatement(query)) {

            stmt.setString(1, user.getId().toString());
            stmt.setInt(2, user.isDeleted() ? 1 : 0);
            stmt.setString(3, user.getName());
            stmt.setInt(4, user.getUserShift().getNum());
            stmt.setInt(5, (user instanceof Admin) ? ((Admin) user).getAccessLevel() : 0);
            stmt.setString(6, (user instanceof Operator) ? ((Operator) user).getSector() : null);

            stmt.executeUpdate();
            System.out.println("Usuário adicionado com sucesso.");
        } catch (SQLException e) {
            System.out.println("Erro ao inserir o usuário: " + e.getMessage());
        }
    }

    @Override
    public void remove(User user) {
//        users.stream()
//                .filter(u -> u == object)
//                .findFirst()
//                .ifPresent(u -> u.setDeleted(true));
        removeById(user.getId());
    }

    @Override
    public void removeById(UUID id) {
//        users.stream()
//                .filter(u -> u.getId().equals(id))
//                .findFirst()
//                .ifPresent(u -> u.setDeleted(true));
        String query = "UPDATE Usuarios SET deleted = ? WHERE id = ?";
        try (var connection = DatabaseConfig.getConnection();
             var stmt = connection.prepareStatement(query)) {

            stmt.setInt(1, 1); // Marca como deletado
            stmt.setString(2, id.toString());
            int result = stmt.executeUpdate();

            if (result == 1) {
                System.out.println("Usuário removido com sucesso.");
            } else {
                System.out.println("Usuário não encontrado.");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao remover o usuário: " + e.getMessage());
        }
    }

    @Override
    public void delete(User user) {
//        users.remove(object);
        deleteById(user.getId());
    }

    @Override
    public void deleteById(UUID id) {
//        users.removeIf(u -> u.getId().equals(id));
//                .stream()
//                .filter(u -> u.getIdUser().equals(id))
//                .findFirst()
//                .ifPresent(users::remove);
        String query = "DELETE FROM Usuarios WHERE id = ?";
        try (var connection = DatabaseConfig.getConnection();
             var stmt = connection.prepareStatement(query)) {

            stmt.setString(1, id.toString());
            int result = stmt.executeUpdate();

            if (result == 1) {
                System.out.println("Usuário deletado com sucesso.");
            } else {
                System.out.println("Usuário não encontrado.");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao deletar o usuário: " + e.getMessage());
        }
    }

    @Override
    public List<User> getAll() {
//        return users;

        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM Usuarios";
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
            }
        } catch (SQLException e) {
            System.out.println("Erro ao recuperar os usuários: " + e.getMessage());
        }
        return users;
    }

    @Override
    public List<User> get() {
//        return users.stream()
//                .filter(u -> !u.isDeleted())
//                .toList();
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM Usuarios WHERE deleted = 0";
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
                users.add(user);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao recuperar os usuários ativos: " + e.getMessage());
        }
        return users;
    }

    @Override
    public User getById(UUID id) {
//        return users.stream()
//                .filter(u -> u.getId().equals(id))
//                .findFirst()
//                .orElseThrow(() -> new NoSuchElementException("Usuário não existe."));
        String query = "SELECT * FROM Usuarios WHERE id = ?";
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
                return user;
            } else {
                throw new NoSuchElementException("Usuário não encontrado.");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao recuperar o usuário: " + e.getMessage());
            return null;
        }
    }
}
