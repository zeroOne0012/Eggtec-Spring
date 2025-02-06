package handalab.eggtec.dto.response.history;

import lombok.Data;

@Data
public class FormattedSummaryDTO { // list
    private Integer recipeNo;
    private String nickname;
    private String type;
    private String weight;
    private Double ngPercentage;
    private String formattedDate;

    public FormattedSummaryDTO(SummaryDTO dto, String formattedDate) {
        this.recipeNo = dto.getRecipeNo();
        this.nickname = dto.getNickname();
        this.type = dto.getType();
        this.weight = dto.getWeight();
        this.ngPercentage = dto.getNgPercentage();
        this.formattedDate = formattedDate;
    }
}