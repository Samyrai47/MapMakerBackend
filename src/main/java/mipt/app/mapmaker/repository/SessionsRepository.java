package mipt.app.mapmaker.repository;

import mipt.app.mapmaker.entity.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SessionsRepository extends JpaRepository<Session, Long> {
    Optional<Session> findByUserId(Long id);

    Optional<Session> findByCookie(String cookieValue);
}
