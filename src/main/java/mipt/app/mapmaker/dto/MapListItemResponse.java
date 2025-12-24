package mipt.app.mapmaker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;

@Data
@AllArgsConstructor
public class MapListItemResponse {
    private Long id;
    private String name;
    private int version;
    private Instant updatedAt;
}
