package lesson26.home.utils;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class ValidationFrom {
    private List<String> errors;
    private Object object;

    public ValidationFrom(Object object) {
        this.object = object;
    }

    public boolean isValid() {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();

        if (Objects.isNull(object)) {
            throw new BusinessException("Объект не найден");
        }
        Set<ConstraintViolation<Object>> constraintViolations = validator
                .validate(object);

        if (constraintViolations.size() == 0) {
            return true;
        }

        errors = constraintViolations.stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.toList());

        return false;
    }

    public List<String> getErrors() {
        return errors;
    }
}
