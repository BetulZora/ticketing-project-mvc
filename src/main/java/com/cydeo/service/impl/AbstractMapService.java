package com.cydeo.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractMapService <T,ID> {

    // This will be part of our data generator
    // We prefer the use of HashMaps to store information

    protected Map<ID,T> map = new HashMap<>();

    T save(ID id, T object){
        map.put(id,object);
        return object;
    }

    List<T> findAll(){
        return new ArrayList<>(map.values());
    }

    T findById(ID id){
        return map.get(id);
    }

    void deleteById(ID id){
        map.remove(id);
    }




}
