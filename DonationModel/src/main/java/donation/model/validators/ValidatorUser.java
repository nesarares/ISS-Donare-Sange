package donation.model.validators;

import donation.model.User;

public class ValidatorUser implements IValidator<User> {

    @Override
    public void validate(User entity) throws ValidationException {
        String errors ="";

        if(entity.getUsername().equals("")) errors += "Username can`t be empty\n";
        if(entity.getPassHash().equals("")) errors += "Password can`t be empty\n";

        if (!errors.equals("")) throw new ValidationException(errors);
    }
}
