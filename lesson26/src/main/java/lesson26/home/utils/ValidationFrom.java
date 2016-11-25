package lesson26.home.utils;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Класс валидации объектов
 */
public class ValidationFrom {
    /** Список возникших в процессе валидации ошибок */
    private List<String> errors;
    /** Экземпляр валидируемого объекта */
    private Object object;

    public ValidationFrom(Object object) {
        this.object = object;
    }

    /**
     * Метод запуска процесса валидации
     * @return true - в случае отсутствия ошибок при валидации объекта, false - иначе
     */
    public boolean isValid() {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();

        if (Objects.isNull(object)) {
            throw new BusinessException("Объект не найден");
        }
        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(object);
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
