package lesson14.home.utils;

import java.util.Queue;

public interface JobDao {
    Queue<Task> getQueue();
}
