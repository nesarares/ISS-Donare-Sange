package donation.persistence.repository;

import donation.model.MedicalQuestionnaire;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class MedicalQuestionnaireRepository {
    public MedicalQuestionnaireRepository() {
    }

    public int size() {
        return Repository.getAll(MedicalQuestionnaire.class).size();
    }

    public void save(MedicalQuestionnaire entity) throws RepositoryException {
        if (Repository.exists(MedicalQuestionnaire.class,entity))
            throw new RepositoryException("There cannot be two instances of an object.");
        Repository.add(MedicalQuestionnaire.class, entity);
    }

    public void delete(MedicalQuestionnaire entity) throws RepositoryException {
        if (!Repository.exists(MedicalQuestionnaire.class,entity))
            throw new RepositoryException("You cannot delete an object that doesn't exist.");
        Repository.delete(MedicalQuestionnaire.class, entity);
    }

    public MedicalQuestionnaire findById(int entityId) {
        Optional<MedicalQuestionnaire> findResult = Repository.get(MedicalQuestionnaire.class,entityId);
        return findResult.orElse(null);
    }

    public void update(int oldId, MedicalQuestionnaire newEntity) throws RepositoryException {
        Optional<MedicalQuestionnaire> medicalQuestionnaire = Repository.get(MedicalQuestionnaire.class, oldId);
        if (medicalQuestionnaire.isPresent())
            Repository.update(MedicalQuestionnaire.class, medicalQuestionnaire.get(), newEntity);
        else
            throw new RepositoryException("You cannot update an object that doesn't exist.");
    }

    public List<MedicalQuestionnaire> getAll() {
        return Repository.getAll(MedicalQuestionnaire.class);
    }

    public MedicalQuestionnaire find(Predicate<MedicalQuestionnaire> filterCondition) {
        Optional<MedicalQuestionnaire> findResult = Repository.findObj(MedicalQuestionnaire.class,filterCondition);
        return findResult.orElse(null);
    }

    public List<MedicalQuestionnaire> getAllFiltered(Predicate<MedicalQuestionnaire> predicate) {
        return Repository.filterAll(MedicalQuestionnaire.class,predicate);
    }
}
