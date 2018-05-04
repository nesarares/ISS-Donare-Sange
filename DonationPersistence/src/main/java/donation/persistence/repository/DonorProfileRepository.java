package donation.persistence.repository;

import donation.model.DonorProfile;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class DonorProfileRepository implements IRepository<DonorProfile> {
    public DonorProfileRepository() {
    }

    public int size() {
        return Repository.getAll(DonorProfile.class).size();
    }

    public void save(DonorProfile entity) throws RepositoryException {
        if (Repository.exists(DonorProfile.class,entity))
            throw new RepositoryException("There cannot be two instances of an object.");
        Repository.add(DonorProfile.class, entity);
    }

    public void delete(DonorProfile entity) throws RepositoryException {
        if (!Repository.exists(DonorProfile.class,entity))
            throw new RepositoryException("You cannot delete an object that doesn't exist.");
        Repository.delete(DonorProfile.class, entity);
    }

    public DonorProfile findById(int entityId) {
        Optional<DonorProfile> findResult = Repository.get(DonorProfile.class,entityId);
        return findResult.orElse(null);
    }

    public void update(int oldId, DonorProfile newEntity) throws RepositoryException {
        Optional<DonorProfile> donorProfile = Repository.get(DonorProfile.class, oldId);
        if (donorProfile.isPresent())
            Repository.update(DonorProfile.class, donorProfile.get(), newEntity);
        else
            throw new RepositoryException("You cannot update an object that doesn't exist.");
    }

    public List<DonorProfile> getAll() {
        return Repository.getAll(DonorProfile.class);
    }

    public DonorProfile find(Predicate<DonorProfile> filterCondition) {
        Optional<DonorProfile> findResult = Repository.findObj(DonorProfile.class,filterCondition);
        return findResult.orElse(null);
    }

    public List<DonorProfile> getAllFiltered(Predicate<DonorProfile> predicate) {
        return Repository.filterAll(DonorProfile.class,predicate);
    }
}
