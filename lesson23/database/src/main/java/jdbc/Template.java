package jdbc;

public interface Template {
    <T> T execute(Action<T> action);
}
