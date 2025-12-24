package mipt.app.mapmaker.repository;

import java.util.List;
import mipt.app.mapmaker.entity.MapFurniture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MapFurnitureRepository extends JpaRepository<MapFurniture, Long> {
  void deleteByMap_Id(Long mapId);

  List<MapFurniture> findAllByMap_Id(Long mapId);
}
