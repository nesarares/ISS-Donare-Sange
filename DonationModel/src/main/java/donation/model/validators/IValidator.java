package donation.model.validators;

public interface IValidator<T> {

    void validate(T entity) throws ValidationException;
}
