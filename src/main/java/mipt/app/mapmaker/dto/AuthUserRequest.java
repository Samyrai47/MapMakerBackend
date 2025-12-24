package mipt.app.mapmaker.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AuthUserRequest {
  private String email;
  private String password;
}
