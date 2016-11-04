package lesson24.view.views;

/**
 * Интерфейс для установки дополнительных
 * полей контроллера нового окна
 */
@FunctionalInterface
public interface Callback {
    void call(Control instance);
}
