package handalab.eggtec.service;

import handalab.eggtec.dto.response.recipe.RecipeDTO;
import handalab.eggtec.mapper.RecipeMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class RecipeService {
    @Value("${NET_DIR}")
    private String netDir;

    private final RecipeMapper recipeMapper;
    public RecipeService(RecipeMapper recipeMapper) {
        this.recipeMapper = recipeMapper;
    }
    public List<RecipeDTO> getAllRecipes() {
        return recipeMapper.getAllRecipes();
    }

    public List<String> getModels() {
        // System.out.println(netDir);
        // "C:\\Users\\handalab\\Desktop\\netnet";
        File dir = new File(netDir);
        if (!(dir.exists() && dir.isDirectory())) {
            return null;
        }
        String[] files = dir.list();

        return Arrays.asList(Objects.requireNonNull(files));
    }


    public RecipeDTO getRecipe(Integer id) {
        return recipeMapper.getRecipe(id);
    }

    public RecipeDTO postRecipe(RecipeDTO recipeDTO) {
        return recipeMapper.postRecipe(recipeDTO);
    }

    public RecipeDTO updateRecipe(Integer id, RecipeDTO recipeDTO) {
        return recipeMapper.updateRecipe(id, recipeDTO);
    }

    public RecipeDTO deleteRecipe(Integer id) {
        return recipeMapper.deleteRecipe(id);
    }
}
