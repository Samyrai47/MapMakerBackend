package mipt.app.mapmaker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TileDto {
    private int x;
    private int y;
    private String type;
}
