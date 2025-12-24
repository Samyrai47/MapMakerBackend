package mipt.app.mapmaker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FurnitureDto {
    private Long id;
    private String itemId;
    private int x;
    private int y;
    private int rotation;
}
