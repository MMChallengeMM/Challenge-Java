package marmota_mobilidade.repositories;

import marmota_mobilidade.models.Failure;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class FailureRepo implements _CrudRepo<Failure> {
    private final List<Failure> failures = new ArrayList<>();

    @Override
    public void add(Failure object) {
        failures.add(object);
    }

    @Override
    public void remove(Failure object) {
        failures.stream()
                .filter(f -> f == object)
                .findFirst()
                .ifPresent(f -> f.setDeleted(true));
    }

    @Override
    public void removeById(String id) {
        failures.stream()
                .filter(f -> f.getId().equals(id))
                .findFirst()
                .ifPresent(f -> f.setDeleted(true));
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
