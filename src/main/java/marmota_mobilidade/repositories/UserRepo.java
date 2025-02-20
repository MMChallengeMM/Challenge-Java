package marmota_mobilidade.repositories;

import marmota_mobilidade.models.User;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class UserRepo implements _CrudRepo<User> {
    private final List<User> users = new ArrayList<>();

    @Override
    public void add(User object) {
        users.add(object);
    }

    @Override
    public void remove(User object) {
        users.stream()
                .filter(u -> u == object)
                .findFirst()
                .ifPresent(u -> u.setDeleted(true));
    }

    @Override
    public void removeById(String id) {
        users.stream()
                .filter(u -> u.getId().equals(id))
                .findFirst()
                .ifPresent(u -> u.setDeleted(true));
    }

    @Override
    public void delete(User object) {
        users.remove(object);
    }

    @Override
    public void deleteById(String id) {
        users.removeIf(u -> u.getId().equals(id));
//                .stream()
//                .filter(u -> u.getIdUser().equals(id))
//                .findFirst()
//                .ifPresent(users::remove);
    }

    @Override
    public List<User> getAll() {
        return users;
    }

    @Override
    public List<User> get() {
        return users.stream()
                .filter(u -> !u.isDeleted())
                .toList();
    }

    @Override
    public User getById(String id) {
        return users.stream()
                .filter(u -> u.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Usuário não existe."));
    }
}
