package lesson24.db.sсhema;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Данная аннотация предназначена для обозначения полей
 * моделей, представляющих таблицы базы данных, которые являются
 * соответствующими полями в базе данных. Данное обозначение
 * наобходимо для создания общего интерфейса по взаимодействию
 * с БД по средствам sql запросов.
 */
@Target(ElementType.FIELD)
@Retention(value = RetentionPolicy.RUNTIME)
public @interface TableField {
}
