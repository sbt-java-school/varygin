package lesson15.home.task2;


public interface ExecutionManager {
    Context execute(Runnable callback, Runnable... tasks);
}
