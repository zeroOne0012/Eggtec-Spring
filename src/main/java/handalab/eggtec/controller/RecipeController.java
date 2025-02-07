package handalab.eggtec.controller;

import handalab.eggtec.dto.response.recipe.RecipeDTO;
import handalab.eggtec.service.RecipeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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




}
