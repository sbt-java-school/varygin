package lesson26.home.service.core;

import lesson26.home.dao.RelationDao;
import lesson26.home.dao.entities.Ingredient;
import lesson26.home.dao.entities.Recipe;
import lesson26.home.dao.entities.Relation;
import lesson26.home.dao.entities.Unit;
import lesson26.home.service.RelationService;
import lesson26.home.service.entities.IngredientDto;
import lesson26.home.service.entities.RelationDto;
import lesson26.home.utils.BusinessException;
import lesson26.home.utils.ValidationFrom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

import static lesson26.home.utils.Helper.wrap;

@Service
@Transactional
public class RelationServiceImpl implements RelationService {
    private RelationDao relationDao;

    @Autowired
    public RelationServiceImpl(RelationDao relationDao) {
        this.relationDao = relationDao;
    }

    @Override
    public IngredientDto save(RelationDto relation) {
        try {
            ValidationFrom form = new ValidationFrom(relation);
            if (!form.isValid()) {
                throw new BusinessException(form.getErrors());
            }

            Relation result = (Relation) relationDao.save(
                    new Relation(
                            new Recipe(relation.getRecipeId()),
                            new Ingredient(relation.getIngredientId()),
                            relation.getAmount(),
                            new Unit(relation.getUnitId())
                    )
            );
            return new IngredientDto(result);
        } catch (DataIntegrityViolationException e) {
            throw new BusinessException("Этот ингредиент уже доабвлен.", e);
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            throw new BusinessException(e);
        }
    }

    @Override
    public boolean delete(RelationDto relationDto) {
        return wrap(() -> {
            Relation relation = (Relation) relationDao.getById(relationDto.getId());
            Objects.requireNonNull(relation);
            relationDao.delete(relation);
            return true;
        });
    }
}
