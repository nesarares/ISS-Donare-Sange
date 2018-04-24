package donation.persistence.repository;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public interface IRepository <ID,T> {

    /**
     * @return the number of elements from repository
     */

    int size();

    /**
     * @param entity (add the entity into repository)
     * @throws Exception if @param already exists
     */

    void save(T entity) throws  Exception;


    /**
     * @param entity deletes the entity from repository
     * @throws Exception if the object does not exist
     */

    void delete(T entity) throws  Exception;


    /**
     *
     * @param entityId the id of the wanted entity
     * @return null if entity doesn't exist in repository
     */

    T findById(ID entityId);

    /**
     * @param oldId the id of replaced entity
     * @param newEntity the new entity with the same ID as oldID
     */

    void update(ID oldId,T newEntity);


    /**
     *
     * @return all entities from repository
     */

    List<T> getAll();

    /**
     *
     * @param filterCondition filter for the data from repository
     * @return the first element on which filterCondition is true
     */

    T find(Predicate <T> filterCondition);


    /**
     *
     * @param predicate filter predicate
     * @return a list with elements that satisfy the given @param
     */

    List<T> getAllFiltered(Predicate<T> predicate);

}
