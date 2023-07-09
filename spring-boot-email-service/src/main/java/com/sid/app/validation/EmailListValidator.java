package com.sid.app.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

/**
 * @author Siddhant Patni
 */
public class EmailListValidator implements ConstraintValidator<EmailList, List<String>> {

    @Override
    public boolean isValid(List<String> emailList, ConstraintValidatorContext context) {

        if (emailList == null) {
            return true;
        }
        for (String email : emailList) {
            if (!isValidEmailAddress(email)) {
                return false;
            }
        }
        return true;
    }

    private boolean isValidEmailAddress(String email) {

        String emailRegex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        return email.matches(emailRegex);
    }

}