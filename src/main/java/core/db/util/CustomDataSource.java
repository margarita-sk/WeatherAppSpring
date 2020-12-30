package core.db.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CustomDataSource {

  private String driverClassName;
  private String url;
  private String username;
  private char[] password;
  private int maxIdle;
  public int maxWaitTime;


}
