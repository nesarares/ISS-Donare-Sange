package donation.persistence.repository;

import donation.model.BloodTransfusionCenterProfile;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class BloodTransfusionCenterProfileRepository implements IRepository<BloodTransfusionCenterProfile> {
    public int size() {
        return Repository.getAll(BloodTransfusionCenterProfile.class).size();
    }

    public void save(BloodTransfusionCenterProfile entity) throws RepositoryException {
        if (Repository.exists(BloodTransfusionCenterProfile.class,entity))
            throw new RepositoryException("There cannot be two instances of an object.");
        Repository.add(BloodTransfusionCenterProfile.class, entity);
    }

    public void delete(BloodTransfusionCenterProfile entity) throws RepositoryException {
        if (!Repository.exists(BloodTransfusionCenterProfile.class,entity))
            throw new RepositoryException("You cannot delete an object that doesn't exist.");
        Repository.delete(BloodTransfusionCenterProfile.class, entity);
    }

    public BloodTransfusionCenterProfile findById(int entityId) {
        Optional<BloodTransfusionCenterProfile> findResult = Repository.get(BloodTransfusionCenterProfile.class,entityId);
        return findResult.orElse(null);
    }

    public void update(int oldId, BloodTransfusionCenterProfile newEntity) throws RepositoryException {
        Optional<BloodTransfusionCenterProfile> bloodComponentQuantity = Repository.get(BloodTransfusionCenterProfile.class, oldId);
        if (bloodComponentQuantity.isPresent())
            Repository.update(BloodTransfusionCenterProfile.class, bloodComponentQuantity.get(), newEntity);
        else
            throw new RepositoryException("You cannot update an object that doesn't exist.");
    }

    public List<BloodTransfusionCenterProfile> getAll() {
        return Repository.getAll(BloodTransfusionCenterProfile.class);
    }

    public BloodTransfusionCenterProfile find(Predicate<BloodTransfusionCenterProfile> filterCondition) {
        Optional<BloodTransfusionCenterProfile> findResult = Repository.findObj(BloodTransfusionCenterProfile.class,filterCondition);
        return findResult.orElse(null);
    }

    public List<BloodTransfusionCenterProfile> getAllFiltered(Predicate<BloodTransfusionCenterProfile> predicate) {
        return Repository.filterAll(BloodTransfusionCenterProfile.class,predicate);
    }
}
