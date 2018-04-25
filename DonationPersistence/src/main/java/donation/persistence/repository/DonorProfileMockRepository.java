package donation.persistence.repository;

import donation.model.DonorProfile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class DonorProfileMockRepository implements IRepository<DonorProfile> {

    private List<DonorProfile> storage;

    private void populate() {
        DonorProfile donorProfile1 = new DonorProfile();
        donorProfile1.setID(1);
        DonorProfile donorProfile2 = new DonorProfile();
        donorProfile2.setID(2);
        DonorProfile donorProfile3 = new DonorProfile();
        donorProfile3.setID(3);
        DonorProfile donorProfile4 = new DonorProfile();
        donorProfile4.setID(4);
        DonorProfile donorProfile5 = new DonorProfile();
        donorProfile5.setID(5);

        this.storage.add(donorProfile1);
        this.storage.add(donorProfile2);
        this.storage.add(donorProfile3);
        this.storage.add(donorProfile4);
        this.storage.add(donorProfile5);
    }

    public DonorProfileMockRepository() {
        this.storage = new ArrayList<>();
        this.populate();
    }

    @Override
    public int size() {
        return this.storage.size();
    }

    @Override
    public void save(DonorProfile entity) throws RepositoryException {
        this.storage.add(entity);
    }

    @Override
    public void delete(DonorProfile entity) throws RepositoryException {
        this.storage.remove(entity);
    }

    @Override
    public DonorProfile findById(int entityId) {
        Optional<DonorProfile> result = this.storage.stream().filter(dp -> dp.getID() == entityId).findFirst();
        return result.orElse(null);
    }

    @Override
    public void update(int oldId, DonorProfile newEntity) throws RepositoryException {
        for (DonorProfile donorProfile : this.storage) {
            if (donorProfile.getID() == oldId) {
                //donorProfile.setID(newEntity.getID());
                donorProfile.setFirstName(newEntity.getFirstName());
                donorProfile.setLastName(newEntity.getLastName());
                donorProfile.setBirthDate(newEntity.getBirthDate());
                donorProfile.setAddress(newEntity.getAddress());
                donorProfile.setNationality(newEntity.getNationality());
                donorProfile.setEmail(newEntity.getEmail());
                donorProfile.setPhone(newEntity.getPhone());
                donorProfile.setIdUser(newEntity.getIdUser());
                donorProfile.setWeight(newEntity.getWeight());
                donorProfile.setHeight(newEntity.getHeight());
                donorProfile.setCNP(newEntity.getCNP());
                donorProfile.setResidence(newEntity.getResidence());
                donorProfile.setRhBloodGroup(newEntity.getRhBloodGroup());
                donorProfile.setAboBloodGroup(newEntity.getAboBloodGroup());
                return;
            }
        }
        throw new RepositoryException("The donor profile does not exist");
    }

    @Override
    public List<DonorProfile> getAll() {
        return this.storage;
    }

    @Override
    public DonorProfile find(Predicate<DonorProfile> filterCondition) {
        Optional<DonorProfile> result = this.storage.stream().filter(filterCondition).findFirst();
        return result.orElse(null);
    }

    @Override
    public List<DonorProfile> getAllFiltered(Predicate<DonorProfile> predicate) {
        return this.storage.stream().filter(predicate).collect(Collectors.toList());
    }
}
