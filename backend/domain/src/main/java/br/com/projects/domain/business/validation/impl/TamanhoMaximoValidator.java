package br.com.projects.domain.business.validation.impl;

import br.com.projects.domain.business.validation.ValidationResult;
import br.com.projects.domain.business.validation.Validator;

public class TamanhoMaximoValidator implements Validator<String> {

    private final int maxLength;

    public TamanhoMaximoValidator(int maxLength) {
        this.maxLength = maxLength;
    }

    @Override
    public ValidationResult validate(String s) {
        String msg = null;

        if (s != null) {
            int length = s.length();
            msg = (length > maxLength)
                    ? String.format("Não pode ter mais que %d caracteres.", maxLength)
                    : null;
        }
        return new ValidationResult(msg == null, msg);
    }
}

