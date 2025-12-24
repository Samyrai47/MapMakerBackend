package mipt.app.mapmaker.repository;

import mipt.app.mapmaker.entity.Map;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MapRepository extends JpaRepository<Map, Long> {
  Optional<Map> findByIdAndOwnerId(Long id, Long ownerId);

  List<Map> findAllByOwnerIdOrderByUpdatedAtDesc(Long ownerId);
}
