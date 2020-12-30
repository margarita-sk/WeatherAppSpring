package util;

@FunctionalInterface
public interface ExceptionHandleable<T> {
  T execute() throws Exception;
}
