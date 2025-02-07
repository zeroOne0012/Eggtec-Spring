package handalab.eggtec.mapper;

import handalab.eggtec.dto.response.recipe.RecipeDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RecipeMapper {
    List<RecipeDTO> getRecipe();
}
