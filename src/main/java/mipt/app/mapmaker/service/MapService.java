package mipt.app.mapmaker.service;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import mipt.app.mapmaker.dto.*;
import mipt.app.mapmaker.entity.*;
import mipt.app.mapmaker.entity.Session;
import mipt.app.mapmaker.entity.User;
import mipt.app.mapmaker.repository.MapFurnitureRepository;
import mipt.app.mapmaker.repository.MapRepository;
import mipt.app.mapmaker.repository.MapTileRepository;
import mipt.app.mapmaker.repository.SessionsRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class MapService {

  private final MapRepository mapRepository;
  private final MapTileRepository mapTileRepository;
  private final MapFurnitureRepository mapFurnitureRepository;
  private final SessionsRepository sessionsRepository;

  private User requireUser(String cookieValue) {
    Session s =
        sessionsRepository
            .findByCookie(cookieValue)
            .orElseThrow(
                () -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid session"));
    return s.getUser();
  }

  @Transactional
  public MapResponse createMap(String cookieValue, SaveMapRequest req) {
    User user = requireUser(cookieValue);

    Map map = new Map();
    map.setOwner(user);
    map.setName(req.getName() == null || req.getName().isBlank() ? "Untitled map" : req.getName());
    map.setVersion(1);

    map = mapRepository.save(map);

    overwriteTiles(map, req.getTiles());
    overwriteFurniture(map, req.getFurniture());

    return buildResponse(map);
  }

  @Transactional(readOnly = true)
  public MapResponse getMap(String cookieValue, Long mapId) {
    User user = requireUser(cookieValue);

    Map map =
        mapRepository
            .findByIdAndOwnerId(mapId, user.getId())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Map not found"));

    return buildResponse(map);
  }

  @Transactional
  public MapResponse saveOverwrite(String cookieValue, Long mapId, SaveMapRequest req) {
    User user = requireUser(cookieValue);

    Map map =
        mapRepository
            .findByIdAndOwnerId(mapId, user.getId())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Map not found"));

    if (req.getVersion() != map.getVersion()) {
      throw new ResponseStatusException(
          HttpStatus.CONFLICT,
          "Map version mismatch. Current=" + map.getVersion() + ", request=" + req.getVersion());
    }

    if (req.getName() != null && !req.getName().isBlank()) {
      map.setName(req.getName());
    }

    mapTileRepository.deleteByIdMapId(mapId);
    mapFurnitureRepository.deleteByMap_Id(mapId);
    overwriteTiles(map, req.getTiles());
    overwriteFurniture(map, req.getFurniture());
    map.setVersion(map.getVersion() + 1);
    map = mapRepository.save(map);

    return buildResponse(map);
  }

  @Transactional(readOnly = true)
  public List<MapListItemResponse> listMyMaps(String cookieValue) {
    User user = requireUser(cookieValue);

    return mapRepository.findAllByOwnerIdOrderByUpdatedAtDesc(user.getId()).stream()
        .map(m -> new MapListItemResponse(m.getId(), m.getName(), m.getVersion(), m.getUpdatedAt()))
        .toList();
  }

  private void overwriteTiles(Map map, List<TileDto> tiles) {
    if (tiles == null || tiles.isEmpty()) return;

    List<MapTile> entities = new ArrayList<>(tiles.size());
    for (TileDto t : tiles) {
      MapTile e = new MapTile();
      e.setMap(map);
      e.setId(new MapTileId(map.getId(), t.getX(), t.getY()));
      e.setTileType((short) 0);
      entities.add(e);
    }
    mapTileRepository.saveAll(entities);
  }

  private void overwriteFurniture(Map map, List<FurnitureDto> furniture) {
    if (furniture == null || furniture.isEmpty()) return;

    List<MapFurniture> entities = new ArrayList<>(furniture.size());
    for (FurnitureDto f : furniture) {
      MapFurniture e = new MapFurniture();
      e.setMap(map);
      e.setItemId(f.getItemId());
      e.setX(f.getX());
      e.setY(f.getY());
      e.setRotation((short) f.getRotation());
      entities.add(e);
    }
    mapFurnitureRepository.saveAll(entities);
  }

  private MapResponse buildResponse(Map map) {
    Long mapId = map.getId();

    List<TileDto> tiles =
        mapTileRepository.findAllByIdMapId(mapId).stream()
            .map(t -> new TileDto(t.getId().getX(), t.getId().getY(), "FLOOR"))
            .toList();

    List<FurnitureDto> furniture =
        mapFurnitureRepository.findAllByMap_Id(mapId).stream()
            .map(
                f ->
                    new FurnitureDto(f.getId(), f.getItemId(), f.getX(), f.getY(), f.getRotation()))
            .toList();

    return new MapResponse(
        map.getId(),
        map.getName(),
        map.getVersion(),
        map.getCreatedAt(),
        map.getUpdatedAt(),
        tiles,
        furniture);
  }
}
