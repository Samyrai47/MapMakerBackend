package mipt.app.mapmaker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
@AllArgsConstructor
public class MapResponse {
    private Long id;
    private String name;
    private int version;
    private Instant createdAt;
    private Instant updatedAt;
    private List<TileDto> tiles;
    private List<FurnitureDto> furniture;
}
