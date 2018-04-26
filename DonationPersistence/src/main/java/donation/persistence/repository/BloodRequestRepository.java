package donation.persistence.repository;

import donation.model.BloodRequest;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class BloodRequestRepository {
    public BloodRequestRepository() {
    }

    public int size() {
        return Repository.getAll(BloodRequest.class).size();
    }

    public void save(BloodRequest entity) throws RepositoryException {
        if (Repository.exists(BloodRequest.class,entity))
            throw new RepositoryException("There cannot be two instances of an object.");
        Repository.add(BloodRequest.class, entity);
    }

    public void delete(BloodRequest entity) throws RepositoryException {
        if (!Repository.exists(BloodRequest.class,entity))
            throw new RepositoryException("You cannot delete an object that doesn't exist.");
        Repository.delete(BloodRequest.class, entity);
    }

    public BloodRequest findById(int entityId) {
        Optional<BloodRequest> findResult = Repository.get(BloodRequest.class,entityId);
        return findResult.orElse(null);
    }

    public void update(int oldId, BloodRequest newEntity) throws RepositoryException {
        Optional<BloodRequest> bloodRequest = Repository.get(BloodRequest.class, oldId);
        if (bloodRequest.isPresent())
            Repository.update(BloodRequest.class, bloodRequest.get(), newEntity);
        else
            throw new RepositoryException("You cannot update an object that doesn't exist.");
    }

    public List<BloodRequest> getAll() {
        return Repository.getAll(BloodRequest.class);
    }

    public BloodRequest find(Predicate<BloodRequest> filterCondition) {
        Optional<BloodRequest> findResult = Repository.findObj(BloodRequest.class,filterCondition);
        return findResult.orElse(null);
    }

    public List<BloodRequest> getAllFiltered(Predicate<BloodRequest> predicate) {
        return Repository.filterAll(BloodRequest.class,predicate);
    }
}
