import java.util.List;

public interface Manageable<T> {
    void add(T item);
    List<T> getAll();
    void update(int index, T item);
    void delete(T item);
}