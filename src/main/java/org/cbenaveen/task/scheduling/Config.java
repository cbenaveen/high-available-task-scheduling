package org.cbenaveen.task.scheduling;

public class Config {
    public static final String APPLICATION_ID = "task-schedule-streaming-app";

    public static final String BOOTSTRAP_SERVERS = "localhost:9092";
    public static final String SCHEMA_REGISTRY_SERVER = "http://localhost:8081";

    public static final String TASK_SCHEDULING_INPUT_TOPIC = "task_schedules";

    public static final String EVERY_MILLISECONDS_TASK_STORE = "every-ms-tasks-store";
    public static final String EVERY_SECONDS_TASK_STORE = "every-sec-tasks-store";
    public static final String EVERY_MIN_TASK_STORE = "every-min-tasks-store";
    public static final String EVERY_15MIN_TASK_STORE = "evey-15min-tasks-store";
    public static final String EVERY_1HR_TASK_STORE = "every-1hr-tasks-store";
    public static final String EVERY_24HR_TASK_STORE = "every-24hr-tasks-store";

    public static final String[] TASK_STORES = {EVERY_MILLISECONDS_TASK_STORE, EVERY_SECONDS_TASK_STORE,
            EVERY_MIN_TASK_STORE, EVERY_15MIN_TASK_STORE, EVERY_1HR_TASK_STORE,
            EVERY_24HR_TASK_STORE};
}
