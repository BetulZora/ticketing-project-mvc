package com.cydeo.service;

import java.util.List;

public interface CrudService<T,ID> {

    T save(T object);
    List<T> findAll();
    T findById(ID id);

    // This method was removed due to limitted use case.
    //void delete(T object);
    void deleteById(ID id);

    void update(T object);

}
