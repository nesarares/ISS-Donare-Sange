package donation.persistence.repository;

import donation.model.BloodComponentQuantity;
import donation.model.BloodComponentQuantity;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class BloodComponentQuantityRepository implements IRepository<BloodComponentQuantity> {
    public BloodComponentQuantityRepository() {
    }

    public int size() {
        return Repository.getAll(BloodComponentQuantity.class).size();
    }

    public void save(BloodComponentQuantity entity) throws RepositoryException {
        if (Repository.exists(BloodComponentQuantity.class, entity))
            throw new RepositoryException("There cannot be two instances of an object.");
        Repository.add(BloodComponentQuantity.class, entity);
    }

    public void delete(BloodComponentQuantity entity) throws RepositoryException {
        if (!Repository.exists(BloodComponentQuantity.class, entity))
            throw new RepositoryException("You cannot delete an object that doesn't exist.");
        Repository.delete(BloodComponentQuantity.class, entity);
    }

    public BloodComponentQuantity findById(int entityId) {
        Optional<BloodComponentQuantity> findResult = Repository.get(BloodComponentQuantity.class, entityId);
        return findResult.orElse(null);
    }

    public void update(int oldId, BloodComponentQuantity newEntity) throws RepositoryException {
        newEntity.setID(oldId);
        Optional<BloodComponentQuantity> bloodComponentQuantity = Repository.get(BloodComponentQuantity.class, oldId);
        if (bloodComponentQuantity.isPresent())
            Repository.update(BloodComponentQuantity.class, bloodComponentQuantity.get(), newEntity);
        else
            throw new RepositoryException("You cannot update an object that doesn't exist.");
    }

    public List<BloodComponentQuantity> getAll() {
        return Repository.getAll(BloodComponentQuantity.class);
    }

    public BloodComponentQuantity find(Predicate<BloodComponentQuantity> filterCondition) {
        Optional<BloodComponentQuantity> findResult = Repository.findObj(BloodComponentQuantity.class, filterCondition);
        return findResult.orElse(null);
    }

    public List<BloodComponentQuantity> getAllFiltered(Predicate<BloodComponentQuantity> predicate) {
        return Repository.filterAll(BloodComponentQuantity.class, predicate);
    }
}
