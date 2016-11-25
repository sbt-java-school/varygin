package lesson26.home.service.core;

import lesson26.home.dao.RecipeDao;
import lesson26.home.dao.RelationDao;
import lesson26.home.dao.entities.Recipe;
import lesson26.home.dao.entities.Relation;
import lesson26.home.utils.Page;
import lesson26.home.utils.Request;
import lesson26.home.service.ImageLoader;
import lesson26.home.service.RecipeService;
import lesson26.home.service.entities.RecipeDto;
import lesson26.home.utils.BusinessException;
import lesson26.home.utils.ValidationFrom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;

import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toList;
import static lesson26.home.utils.Helper.wrap;
import static org.apache.commons.lang.StringUtils.isBlank;
import static org.apache.commons.lang.StringUtils.isNotBlank;

@Service
public class RecipeServiceImpl implements RecipeService {
    private static final Integer MAX_RECIPES_COUNT = 6;

    private RecipeDao recipeDao;
    private RelationDao relationDao;

    private ImageLoader imageLoader;

    @Autowired
    public RecipeServiceImpl(RecipeDao recipeDao, RelationDao relationDao,
                             ImageLoader imageLoader) {
        this.recipeDao = recipeDao;
        this.relationDao = relationDao;
        this.imageLoader = imageLoader;
    }

    @Override
    @Transactional
    public Long save(RecipeDto recipe) {
        return wrap(() -> {
            if (recipe.getId() != null && recipe.getImage() == null) {
                RecipeDto oldRecipe = get(recipe.getId());
                Objects.requireNonNull(oldRecipe);

                recipe.setImage(oldRecipe.getImage());
            }
            recipe.setName(isBlank(recipe.getName())
                    ? null
                    : recipe.getName());
            recipe.setDescription(isBlank(recipe.getDescription())
                    ? null
                    : recipe.getDescription());
            ValidationFrom form = new ValidationFrom(recipe);

            if (!form.isValid()) {
                throw new BusinessException(form.getErrors());
            }

            Recipe result = (Recipe) recipeDao.save(
                    new Recipe(
                            recipe.getId(),
                            recipe.getName(),
                            recipe.getDescription(),
                            recipe.getImage()
                    )
            );
            return result.getId();
        });
    }

    @Override
    @Transactional
    public RecipeDto get(Long id) {
        return wrap(() -> {
            Recipe recipe = (Recipe) recipeDao.getById(id);
            requireNonNull(recipe);

            return new RecipeDto(recipe, recipe.getRelations());
        });
    }

    @Override
    @Transactional
    public Page<RecipeDto> getList(String name, Integer page) {
        return wrap(() -> {
            Request request = new Request(page, MAX_RECIPES_COUNT);
            Page<Object> result = recipeDao.getList(name, request);

            List<RecipeDto> list = result.getContents().stream()
                    .map(item -> new RecipeDto((Recipe) item)).collect(toList());

            Long pages = request.getPages(result.getTotalCount());
            return new Page<>(list, pages);
        });
    }

    @Override
    @Transactional
    public boolean delete(RecipeDto recipeDto) {
        return wrap(() -> {
            Recipe recipe = (Recipe) recipeDao.getById(recipeDto.getId());
            requireNonNull(recipe);

            removeImage(recipe.getImage());

            List<Relation> relations = recipe.getRelations();
            if (!relations.isEmpty()) {
                relations.forEach(relationDao::delete);
            }

            recipeDao.delete(recipe);
            return true;
        });
    }

    @Override
    @Transactional
    public void changeImage(Long recipeId, MultipartFile image) {
        wrap(() -> {
            RecipeDto recipeDto = requireNonNull(get(recipeId));
            String oldImage = recipeDto.getImage();

            if (image != null) {
                String imagePath = imageLoader.getPath(image);
                recipeDto.setImage(imagePath);
                save(recipeDto);
                imageLoader.saveImage(image, imagePath);
            } else {
                recipeDto.setImage("");
                save(recipeDto);
            }

            removeImage(oldImage);
            return true;
        });
    }

    /**
     * Метод удаления картинки с сервера
     *
     * @param image наименование картинки в файловой системе
     */
    private void removeImage(String image) {
        wrap(() -> isNotBlank(image) && imageLoader.remove(image));
    }
}
