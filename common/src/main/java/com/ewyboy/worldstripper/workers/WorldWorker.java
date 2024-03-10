package com.ewyboy.worldstripper.workers;

import dev.architectury.event.events.common.TickEvent;

import java.util.ArrayList;
import java.util.List;

public class WorldWorker {

    private static final List<IWorker> workers = new ArrayList<>();
    private static long startTime = -1;
    private static int index = 0;

    public static void init() {
        TickEvent.ServerLevelTick.SERVER_PRE.register(serverTick -> tick(true));
        TickEvent.ServerLevelTick.SERVER_POST.register(serverTick -> tick(false));
        // TickEvent.ServerLevelTick.SERVER_LEVEL_PRE.register(serverTick -> tick(true));
        // TickEvent.ServerLevelTick.SERVER_LEVEL_POST.register(serverTick -> tick(false));
    }

    public static void tick(boolean start) {
        if (start) {
            startTime = System.currentTimeMillis();
            return;
        }

        index = 0;

        IWorker task = getNext();
        if (task == null) {
            return;
        }

        long time = 50 - (System.currentTimeMillis() - startTime);

        if (time < 10) {
            time = 10; // If ticks are lagging, give us at least 10ms to do something.
        }

        time += System.currentTimeMillis();

        while (System.currentTimeMillis() < time && task != null) {
            boolean again = task.doWork();

            if (!task.hasWork()) {
                remove(task);
                task = getNext();
            } else if (!again) {
                task = getNext();
            }
        }
    }

    public static synchronized void addWorker(IWorker worker) {
        workers.add(worker);
    }

    private static synchronized IWorker getNext() {
        return workers.size() > index ? workers.get(index++) : null;
    }

    private static synchronized void remove(IWorker worker) {
        workers.remove(worker);
        index--;
    }

    public static synchronized void clear() {
        workers.clear();
    }

    public interface IWorker {
        boolean hasWork();
        boolean doWork();
    }

}
