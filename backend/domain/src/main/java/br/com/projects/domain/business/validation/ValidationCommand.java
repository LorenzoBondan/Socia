package br.com.projects.domain.business.validation;

public class ValidationCommand<T> {

    private final NamedValidator<T> namedValidator;
    private final T value;

    public ValidationCommand(NamedValidator<T> namedValidator, T value) {
        this.namedValidator = namedValidator;
        this.value = value;
    }

    ValidationResult evaluate(){
        return namedValidator.validate(value);
    }
}
