package handalab.eggtec.mapper;

import handalab.eggtec.dto.response.recipe.RecipeDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RecipeMapper {
    List<RecipeDTO> getAllRecipes();

    RecipeDTO getRecipe(Integer id);

    RecipeDTO postRecipe(@Param("recipe") RecipeDTO recipeDTO);

    RecipeDTO updateRecipe(@Param("id")Integer id,@Param("r") RecipeDTO recipeDTO);
}
