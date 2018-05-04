package donation.persistence.repository;

import donation.model.User;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class UserRepository implements IRepository<User> {

    public UserRepository() {
    }

    public int size() {
        return Repository.getAll(User.class).size();
    }

    public void save(User entity) throws RepositoryException {
        if (Repository.exists(User.class,entity))
            throw new RepositoryException("There cannot be two instances of an object.");
        Repository.add(User.class, entity);
    }

    public void delete(User entity) throws RepositoryException {
        if (!Repository.exists(User.class,entity))
            throw new RepositoryException("You cannot delete an object that doesn't exist.");
        Repository.delete(User.class, entity);
    }

    public User findById(int entityId) {
        Optional<User> findResult = Repository.get(User.class,entityId);
        return findResult.orElse(null);
    }

    public void update(int oldId, User newEntity) throws RepositoryException {
        Optional<User> user = Repository.get(User.class, oldId);
        if (user.isPresent())
            Repository.update(User.class, user.get(), newEntity);
        else
            throw new RepositoryException("You cannot update an object that doesn't exist.");
    }

    public List<User> getAll() {
        return Repository.getAll(User.class);
    }

    public User find(Predicate<User> filterCondition) {
        Optional<User> findResult = Repository.findObj(User.class,filterCondition);
        return findResult.orElse(null);
    }

    public List<User> getAllFiltered(Predicate<User> predicate) {
        return Repository.filterAll(User.class,predicate);
    }
}
