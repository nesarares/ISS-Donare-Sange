package donation.persistence.repository;

import donation.model.DoctorProfile;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class DoctorProfileRepository {
    public DoctorProfileRepository() {
    }

    public int size() {
        return Repository.getAll(DoctorProfile.class).size();
    }

    public void save(DoctorProfile entity) throws RepositoryException {
        if (Repository.exists(DoctorProfile.class,entity))
            throw new RepositoryException("There cannot be two instances of an object.");
        Repository.add(DoctorProfile.class, entity);
    }

    public void delete(DoctorProfile entity) throws RepositoryException {
        if (!Repository.exists(DoctorProfile.class,entity))
            throw new RepositoryException("You cannot delete an object that doesn't exist.");
        Repository.delete(DoctorProfile.class, entity);
    }

    public DoctorProfile findById(int entityId) {
        Optional<DoctorProfile> findResult = Repository.get(DoctorProfile.class,entityId);
        return findResult.orElse(null);
    }

    public void update(int oldId, DoctorProfile newEntity) throws RepositoryException {
        Optional<DoctorProfile> doctorProfile = Repository.get(DoctorProfile.class, oldId);
        if (doctorProfile.isPresent())
            Repository.update(DoctorProfile.class, doctorProfile.get(), newEntity);
        else
            throw new RepositoryException("You cannot update an object that doesn't exist.");
    }

    public List<DoctorProfile> getAll() {
        return Repository.getAll(DoctorProfile.class);
    }

    public DoctorProfile find(Predicate<DoctorProfile> filterCondition) {
        Optional<DoctorProfile> findResult = Repository.findObj(DoctorProfile.class,filterCondition);
        return findResult.orElse(null);
    }

    public List<DoctorProfile> getAllFiltered(Predicate<DoctorProfile> predicate) {
        return Repository.filterAll(DoctorProfile.class,predicate);
    }
}
