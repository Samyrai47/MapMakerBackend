package mipt.app.mapmaker.controller.map;

import java.util.List;
import lombok.RequiredArgsConstructor;
import mipt.app.mapmaker.dto.MapListItemResponse;
import mipt.app.mapmaker.dto.MapResponse;
import mipt.app.mapmaker.dto.SaveMapRequest;
import mipt.app.mapmaker.service.MapService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/maps")
public class MapsController {

  private final MapService mapService;

  @PostMapping
  public ResponseEntity<MapResponse> create(
      @CookieValue("token") String cookie, @RequestBody SaveMapRequest req) {
    return ResponseEntity.status(201).body(mapService.createMap(cookie, req));
  }

  @GetMapping("/{id}")
  public ResponseEntity<MapResponse> get(
      @CookieValue("token") String cookie, @PathVariable Long id) {
    return ResponseEntity.ok(mapService.getMap(cookie, id));
  }

  @PutMapping("/{id}")
  public ResponseEntity<MapResponse> saveOverwrite(
      @CookieValue("token") String cookie, @PathVariable Long id, @RequestBody SaveMapRequest req) {
    return ResponseEntity.ok(mapService.saveOverwrite(cookie, id, req));
  }

  @GetMapping
  public ResponseEntity<List<MapListItemResponse>> listMine(@CookieValue("token") String cookie) {
    return ResponseEntity.ok(mapService.listMyMaps(cookie));
  }
}
