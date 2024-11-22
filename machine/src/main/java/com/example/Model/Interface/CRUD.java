package com.example.Model.Interface;

import java.util.List;

public interface CRUD {
    Object create(Object object);

    List<Object> getAll(int page, int size);

    List<Object> getAll();

}
