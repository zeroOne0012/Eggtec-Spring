package handalab.eggtec.controller;

import handalab.eggtec.dto.MessageDTO;
import handalab.eggtec.dto.response.recipe.RecipeDTO;
import handalab.eggtec.service.RecipeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/apis/recipe")
@RestController
public class RecipeController {
    private final RecipeService recipeService;
    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("/")
    public ResponseEntity<List<RecipeDTO>> getAllRecipes() {
        List<RecipeDTO> result = recipeService.getAllRecipes();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/model")
    public ResponseEntity<List<String>> getModels() {
        List<String> result = recipeService.getModels();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecipeDTO> getRecipe(@PathVariable(name="id") Integer id) {
        RecipeDTO result = recipeService.getRecipe(id);
        return result!=null?ResponseEntity.ok(result):ResponseEntity.notFound().build();
    }

    @PostMapping("/")
    public ResponseEntity<MessageDTO> createRecipe(@RequestBody RecipeDTO recipeDTO) {
        RecipeDTO result = recipeService.postRecipe(recipeDTO);
        return result!=null
                ? ResponseEntity.status(HttpStatus.CREATED).body(new MessageDTO("Recipe created successfully", "recipe", result))
                :ResponseEntity.badRequest().body(new MessageDTO("Failed to create recipe"));
    }

}
