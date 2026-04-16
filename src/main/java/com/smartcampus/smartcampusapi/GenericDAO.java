package com.smartcampus.smartcampusapi;

import java.util.List;

/**
 * Adapted from Tutorial Week 08 (GenericDAO.java)
 * Provides reusable CRUD operations for any model class that implements BaseModel.
 * Works directly on the shared static lists in MockDatabase, so changes
 * persist across requests for the lifetime of the server session.
 * Modified from the tutorial version:
 * - ID type changed from int to String to support IDs like "LIB-301" and "TEMP-001"
 * - Auto-increment removed as IDs are provided by the client in the request body
 * - Null check added to getById() to prevent NullPointerException on empty list entries
 */

public class GenericDAO<T extends BaseModel> {

    // Direct reference to the shared MockDatabase list
    // Changes made here are immediately reflected across all DAOs using the same list
    private final List<T> items;

    /**
     * Constructs a GenericDAO backed by the provided list.
     * @param items the shared MockDatabase list to operate on
     */
    
    public GenericDAO(List<T> items) {
        
        this.items = items;
        
    }

    /**
     * Returns all items in the list.
     */
    
    public List<T> getAll() {
        
        return items;
        
    }

    /**
     * Finds and returns an item by its String ID.
     * Returns null if no matching item is found.
     * Null check included to guard against empty list entries.
     * @param id the unique String identifier to search for
     */
    
    public T getById(String id) {
        
        for (T item : items) {
            
            if (item != null && item.getId().equals(id)) {
                
                return item;
                
            }
            
        }
        
        return null;
        
    }

    /**
     * Adds a new item to the list.
     * @param item the item to add
     */
    
    public void add(T item) {
        
        items.add(item);
        
    }

    /**
     * Replaces an existing item in the list with an updated version.
     * Matches by ID — if no match is found, no change is made.
     * @param updatedItem the updated item to replace the existing one
     */
    
    public void update(T updatedItem) {
        
        for (int i = 0; i < items.size(); i++) {
            
            T item = items.get(i);
            
            if (item.getId().equals(updatedItem.getId())) {
                
                items.set(i, updatedItem);
                return;
                
            }
            
        }
        
    }

    /**
     * Removes an item from the list by its String ID.
     * If no matching item is found, no change is made.
     * @param id the unique String identifier of the item to remove
     */
    
    public void delete(String id) {
        
        items.removeIf(item -> item.getId().equals(id));
        
    }
    
}