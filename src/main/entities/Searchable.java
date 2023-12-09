package main.entities;

import main.entities.users.User;

/**
 * Interface for entities that can be searched and selected.
 */
public interface Searchable {

    /**
     * Check if the entity matches the filter and its parameter.
     *
     * @param filter    the search filter.
     * @param parameter the filter's parameter.
     * @return true if the entity matches the search.
     */
    boolean matchFilter(String filter, String parameter);

    /**
     * Get the name of the search result.
     */
    String getName();

    /**
     * Select this search result.
     *
     * @param user the user that selected the entity.
     */
    void selectResultBy(User user);
}
