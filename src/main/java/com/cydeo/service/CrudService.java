package com.cydeo.service;

import java.util.List;

public interface CrudService<T,ID> {

    T save(T object);
    List<T> findAll();
    T findById(ID id);
    void delete(T object);
    void deleteById(ID id);

}
