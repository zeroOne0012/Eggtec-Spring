package handalab.eggtec.service;

import handalab.eggtec.dto.recipe.RecipeDTO;
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
    @Value("./net") // .env -> NET_DIR = C:\\\\Users\\\\handalab\\\\Desktop\\\\net
    private String netDir; // netDir=./net

    private final RecipeMapper recipeMapper;
    public RecipeService(RecipeMapper recipeMapper) {
        this.recipeMapper = recipeMapper;
    }

    // GET /
    public List<RecipeDTO> getAllRecipes() {
        return recipeMapper.getAllRecipes();
    }

    // GET /model
    public List<String> getModels() {
        // "C:\\Users\\handalab\\Desktop\\net";
        File dir = new File(netDir);
        if (!(dir.exists() && dir.isDirectory())) {
            return null;
        }
        String[] files = dir.list();

        return Arrays.asList(Objects.requireNonNull(files));
    }

    // GET /{id}
    public RecipeDTO getRecipe(Integer id) {
        return recipeMapper.getRecipe(id);
    }

    // POST /
    public RecipeDTO postRecipe(RecipeDTO recipeDTO) {
        return recipeMapper.postRecipe(recipeDTO);
    }

    // PATCH /{id}
    public RecipeDTO updateRecipe(Integer id, RecipeDTO recipeDTO) {
        return recipeMapper.updateRecipe(id, recipeDTO);
    }
    
    // DELETE /{id}
    public RecipeDTO deleteRecipe(Integer id) {
        return recipeMapper.deleteRecipe(id);
    }
}
