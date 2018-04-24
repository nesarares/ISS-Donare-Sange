package donation.persistence.repository;

import donation.model.User;
import donation.model.UserType;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class MockUserRepository implements  IRepository<Integer,User> {

    private List<User> users = new ArrayList<>();


    private void populate(){
        users.add(new User(1,"user1","pass1", UserType.Admin));
        users.add(new User(2,"user2","pass2", UserType.Admin));
        users.add(new User(3,"user3","pass3", UserType.Admin));
        users.add(new User(4,"user4","pass4", UserType.Admin));
    }

    public  MockUserRepository(){
        populate();
    }

    @Override
    public int size() {
        return users.size();
    }

    @Override
    public void save(User entity) throws Exception {
        users.add(entity);
    }

    @Override
    public void delete(User entity) throws Exception {
        users.remove(entity);
    }

    @Override
    public User findById(Integer entityId) {
        Optional <User> findResult = users.stream().filter(x->x.getId() == entityId).findFirst();
        return findResult.orElse(null);
    }

    @Override
    public void update(Integer oldId, User newEntity) {

    }

    @Override
    public List<User> getAll() {
        return users;
    }

    @Override
    public User find(Predicate<User> filterCondition) {
        Optional <User> findResult = users.stream().filter(filterCondition).findFirst();
        return findResult.orElse(null);
    }

    @Override
    public List<User> getAllFiltered(Predicate<User> predicate) {
        return  users.stream().filter(predicate).collect(Collectors.toList());
    }
}
