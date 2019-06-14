package com.software.yapespots.core;

import java.util.Collection;

public interface BaseRepository<T, ID> {
    Collection<T> all();

    boolean create(T element);

    boolean update(T element, ID id);

    boolean delete(ID id);

    boolean find(ID id);
}
