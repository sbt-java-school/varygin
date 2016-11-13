package lesson26.home.service;

import lesson26.home.service.schema.IngredientTdo;
import lesson26.home.service.schema.RelationTdo;

public interface RelationService {
    Long save(RelationTdo recipe);

    void delete(RelationTdo relationTdo);

    IngredientTdo get(Long id);
}
