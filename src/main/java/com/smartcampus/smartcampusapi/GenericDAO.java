package com.smartcampus.smartcampusapi;

import java.util.List;
/**
 * GenericDAO - Data Access Object
 * Adapted from Week 8 tutorial (GenericDAO.java)
 *
 * Modified from the tutorial version: changed ID type from int to String
 * to match the spec's requirements for IDs like "LIB-301" and "TEMP-001"
 * Auto-increment removed as IDs are now provided by the client.
 */
public class GenericDAO<T extends BaseModel> {

    private final List<T> items;

    public GenericDAO(List<T> items) {
        this.items = items;
    }

    public List<T> getAll() {
        return items;
    }

    public T getById(String id) {
        for (T item : items) {
            if (item != null && item.getId().equals(id)) {
                return item;
            }
        }
        return null;
    }

    public void add(T item) {
        items.add(item);
    }

    public void update(T updatedItem) {
        for (int i = 0; i < items.size(); i++) {
            T item = items.get(i);
            if (item.getId().equals(updatedItem.getId())) {
                items.set(i, updatedItem);
                return;
            }
        }
    }

    public void delete(String id) {
        items.removeIf(item -> item.getId().equals(id));
    }
}