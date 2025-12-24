package mipt.app.mapmaker.entity;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "sessions")
@NoArgsConstructor(access = PROTECTED)
@Getter
@Setter
public class Session {
  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Long id;

  @Column(name = "cookie")
  private String cookie;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", referencedColumnName = "id")
  private User user;

  public Session(String cookie, User user) {
    this.cookie = cookie;
    this.user = user;
  }
}
