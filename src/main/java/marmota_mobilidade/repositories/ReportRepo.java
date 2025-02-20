package marmota_mobilidade.repositories;

import marmota_mobilidade.models.Report;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class ReportRepo implements _CrudRepo<Report> {
    private final List<Report> reports = new ArrayList<>();

    @Override
    public void add(Report object) {
        reports.add(object);
    }

    @Override
    public void remove(Report object) {
        reports.stream()
                .filter(r -> r == object)
                .findFirst()
                .ifPresent(r -> r.setDeleted(true));
    }

    @Override
    public void removeById(String id) {
        reports.stream()
                .filter(r -> r.getId().equals(id))
                .findFirst()
                .ifPresent(r -> r.setDeleted(true));
    }

    @Override
    public void delete(Report object) {
        reports.remove(object);
    }

    @Override
    public void deleteById(String id) {
        reports.removeIf(r -> r.getId().equals(id));
    }

    @Override
    public List<Report> getAll() {
        return reports;
    }

    @Override
    public List<Report> get() {
        return reports.stream()
                .filter(r -> !r.isDeleted())
                .toList();
    }

    @Override
    public Report getById(String id) {
        return reports.stream()
                .filter(r -> r.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Falha não existe."));
    }
}
