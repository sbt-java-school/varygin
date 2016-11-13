package lesson26.home.service;

import lesson26.home.service.schema.RecipeTdo;

import java.util.List;

public interface RecipeService {
    Long save(RecipeTdo recipe);

    RecipeTdo get(Long id);

    List<RecipeTdo> getList();

    List<RecipeTdo> getByName(String name);

    void delete(RecipeTdo recipeTdo);
}
