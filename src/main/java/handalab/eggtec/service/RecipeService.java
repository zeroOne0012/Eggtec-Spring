package handalab.eggtec.service;

import handalab.eggtec.dto.response.recipe.RecipeDTO;
import handalab.eggtec.mapper.RecipeMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecipeService {
    private final RecipeMapper recipeMapper;
    public RecipeService(RecipeMapper recipeMapper) {
        this.recipeMapper = recipeMapper;
    }
    public List<RecipeDTO> getAllRecipes() {
        return recipeMapper.getRecipe();
    }
}
