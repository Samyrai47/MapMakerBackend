package mipt.app.mapmaker.repository;

import java.util.List;
import mipt.app.mapmaker.entity.MapTile;
import mipt.app.mapmaker.entity.MapTileId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MapTileRepository extends JpaRepository<MapTile, MapTileId> {
  void deleteByIdMapId(Long mapId);

  List<MapTile> findAllByIdMapId(Long mapId);
}
