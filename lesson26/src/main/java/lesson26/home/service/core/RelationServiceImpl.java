package lesson26.home.service.core;

import lesson26.home.dao.RelationDao;
import lesson26.home.dao.schema.Ingredient;
import lesson26.home.dao.schema.Recipe;
import lesson26.home.dao.schema.Relation;
import lesson26.home.dao.schema.Unit;
import lesson26.home.service.RelationService;
import lesson26.home.service.schema.IngredientTdo;
import lesson26.home.service.schema.RelationTdo;
import lesson26.home.utils.BusinessException;
import lesson26.home.utils.ValidationFrom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
public class RelationServiceImpl implements RelationService {
    private RelationDao relationDao;

    @Autowired
    public RelationServiceImpl(RelationDao relationDao) {
        this.relationDao = relationDao;
    }

    @Override
    @Transactional
    public Long save(RelationTdo relation) {
        try {
            ValidationFrom form = new ValidationFrom(relation);
            if (!form.isValid()) {
                throw new BusinessException(form.getErrors());
            }
            Relation result = (Relation) relationDao.save(new Relation(
                    new Recipe(relation.getRecipeId()),
                    new Ingredient(relation.getIngredientId()),
                    relation.getAmount(),
                    new Unit(relation.getUnitId())
            ));
            return result.getId();
        } catch (DataIntegrityViolationException e) {
            throw new BusinessException("Этот ингредиент уже доабвлен.", e);
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            throw new BusinessException(e);
        }
    }

    @Override
    @Transactional
    public void delete(RelationTdo relationTdo) {
        try {
            Relation relation = (Relation) relationDao.getById(relationTdo.getId());
            Objects.requireNonNull(relation);
            relationDao.delete(relation);
        } catch (Exception e) {
            throw new BusinessException(e);
        }
    }

    @Override
    @Transactional
    public IngredientTdo get(Long id) {
        try {
            Relation relation = (Relation) relationDao.getById(id);
            Objects.requireNonNull(relation);

            return new IngredientTdo(
                    relation.getId(),
                    relation.getIngredient().getName()
                            + ": " + relation.getAmount()
                            + " " + relation.getUnit().getShortName()
            );
        } catch (Exception e) {
            throw new BusinessException(e);
        }
    }
}
