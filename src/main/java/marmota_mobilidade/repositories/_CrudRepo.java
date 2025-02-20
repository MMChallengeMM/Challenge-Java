package marmota_mobilidade.repositories;

import java.util.List;

public interface _CrudRepo<T> {
    /** Adiciona um objeto*/
    void add(T object);

    /** Remove um objeto de uma lista (sem apagar da lista)*/
    void remove(T object);

    /** Remove um objeto especifico (id) de uma lista (sem apagar da lista)*/
    void removeById(String id);

    /** Remove um objeto de uma lista (APAGANDO da lista)*/

    void delete(T object);

    /** Remove um objeto especifico (id) de uma lista (APAGANDO da lista)*/
    void deleteById(String id);

    /** Retorna a lista comlpleta (deletados e não deletados)*/
    List<T> getAll();

    /** Retorna a lista incompleta (não deletados)*/
    List<T> get();

    /** Retorna um objeto especifico (id)*/
    T getById(String id);
}
