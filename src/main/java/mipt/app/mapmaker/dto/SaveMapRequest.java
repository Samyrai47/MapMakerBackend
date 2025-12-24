package mipt.app.mapmaker.dto;

import lombok.Data;

import java.util.List;

@Data
public class SaveMapRequest {
    private String name;
    private int version;
    private List<TileDto> tiles;
    private List<FurnitureDto> furniture;
}
