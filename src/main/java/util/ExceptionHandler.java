package util;

import lombok.extern.log4j.Log4j;

@Log4j
public class ExceptionHandler {

  public static <T> T handleIntoRuntime(ExceptionHandleable<T> exceptionHandleable) {
    try {
      return exceptionHandleable.execute();
    } catch (Exception exception) {
      log.error(exception);
      throw new RuntimeException(exception);
    }
  }

}
