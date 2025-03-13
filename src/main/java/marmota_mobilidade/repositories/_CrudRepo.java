package marmota_mobilidade.repositories;

import java.util.List;
import java.util.UUID;

public interface _CrudRepo<T> {
    /**
     * Adiciona um objeto
     */
    void add(T object);

    /**
     * Remove um objeto de uma lista (sem apagar da lista)
     */
    void remove(T object);

    /**
     * Remove um objeto especifico (id) de uma lista (sem apagar da lista)
     */
    void removeById(UUID id);

    /**
     * Remove um objeto de uma lista (APAGANDO da lista)
     */

    void delete(T object);

    /**
     * Remove um objeto especifico (id) de uma lista (APAGANDO da lista)
     */
    void deleteById(UUID id);

    /**
     * Retorna a lista comlpleta (deletados e não deletados)
     */
    List<T> getAll();

    /**
     * Retorna a lista incompleta (não deletados)
     */
    List<T> get();

    /**
     * Retorna um objeto especifico (id)
     */
    T getById(UUID id);
}
