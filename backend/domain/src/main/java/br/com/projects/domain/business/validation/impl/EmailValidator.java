package br.com.projects.domain.business.validation.impl;

import br.com.projects.domain.business.validation.ValidationResult;
import br.com.projects.domain.business.validation.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailValidator implements Validator<String> {

    @Override
    public ValidationResult validate(String email) {
        ValidationResult validationResult = new ValidationResult(true);

        if (email != null && !isValidEmailFormat(email)) {
            validationResult = new ValidationResult(false, "Formato inválido de e-mail");
        }

        return validationResult;
    }

    private boolean isValidEmailFormat(String email) {
        // Expressão regular para validar o formato do e-mail
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);

        return matcher.matches();
    }
}
