package com.change.client.repository.item;

import java.util.List;

public interface IItemDAO<T> {
    String insert(T item);
    List<T> getAll();
    boolean update(T item);
    boolean delete(String id);

    List<String> getErrors();
    List<String> getSuccess();
}
