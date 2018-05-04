package donation.model.validators;

import donation.model.DonorProfile;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.regex.Pattern;

public class ValidatorDonorProfile implements IValidator<DonorProfile> {
    @Override
    public void validate(DonorProfile entity) throws ValidationException {
        String errors ="";

        Pattern patternEmail = Pattern.compile("^[a-zA-Z][\\w0-9\\.]*@([a-zA-Z]+\\.)+[a-zA-Z]+$");
        Pattern patternCNPAndPhone = Pattern.compile("[^0-9]");
        Pattern patternName = Pattern.compile("[^a-zA-Z_\\.]");
        Pattern patternNationality = Pattern.compile("[^a-zA-Z]");

        if (entity.getCNP() == null || entity.getCNP().length() != 13 || patternCNPAndPhone.matcher(entity.getCNP()).find()) errors += "Invalid CNP\n";
        if (entity.getEmail() == null || entity.getEmail().equals("") || !patternEmail.matcher(entity.getEmail()).find()) errors += "Invalid Email\n";
        if (entity.getAddress() == null || entity.getAddress().equals("")) errors +="Invalid Address\n";
        if (entity.getResidence() == null || entity.getResidence().equals("")) errors += "Invalid Residence Address\n";
        if (entity.getBirthDate() == null || entity.getBirthDate().toString().equals("") || entity.getBirthDate().after(Date.valueOf(LocalDate.now()))) errors += "Invalid BirthDate\n";
        if (entity.getFirstName() == null || entity.getFirstName().equals("") || patternName.matcher(entity.getFirstName()).find()) errors += "Invalid FirstName\n";
        if (entity.getLastName() == null || entity.getLastName().equals("") || patternName.matcher(entity.getLastName()).find()) errors += "Invalid LastName\n";
        if (entity.getNationality() == null || entity.getNationality().equals("") || patternNationality.matcher(entity.getNationality()).find()) errors += "Invalid Nationality\n";
        if (entity.getHeight() <= 0) errors += "Invalid Height\n";
        if (entity.getWeight() <= 0) errors += "Invalid Weight\n";
        if (entity.getPhone() == null || entity.getPhone().equals("") || patternCNPAndPhone.matcher(entity.getPhone()).find()) errors += "Invalid Phone Number\n";

        if (!errors.equals("")) throw new ValidationException(errors);
    }
}
