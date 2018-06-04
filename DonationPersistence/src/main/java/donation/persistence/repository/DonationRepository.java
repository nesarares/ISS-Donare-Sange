package donation.persistence.repository;

import donation.model.Donation;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class DonationRepository implements IRepository<Donation> {

    public DonationRepository() {
    }

    public int size() {
        return Repository.getAll(Donation.class).size();
    }

    public void save(Donation entity) throws RepositoryException {
        if (Repository.exists(Donation.class, entity))
            throw new RepositoryException("There cannot be two instances of an object.");
        Repository.add(Donation.class, entity);
    }

    public void delete(Donation entity) throws RepositoryException {
        if (!Repository.exists(Donation.class, entity))
            throw new RepositoryException("You cannot delete an object that doesn't exist.");
        Repository.delete(Donation.class, entity);
    }

    public Donation findById(int entityId) {
        Optional<Donation> findResult = Repository.get(Donation.class, entityId);
        return findResult.orElse(null);
    }

    public void update(int oldId, Donation newEntity) throws RepositoryException {
        newEntity.setID(oldId);
        Optional<Donation> donation = Repository.get(Donation.class, oldId);
        if (donation.isPresent())
            Repository.update(Donation.class, donation.get(), newEntity);
        else
            throw new RepositoryException("You cannot update an object that doesn't exist.");
    }

    public List<Donation> getAll() {
        return Repository.getAll(Donation.class);
    }

    public Donation find(Predicate<Donation> filterCondition) {
        Optional<Donation> findResult = Repository.findObj(Donation.class, filterCondition);
        return findResult.orElse(null);
    }

    public List<Donation> getAllFiltered(Predicate<Donation> predicate) {
        return Repository.filterAll(Donation.class, predicate);
    }
}
