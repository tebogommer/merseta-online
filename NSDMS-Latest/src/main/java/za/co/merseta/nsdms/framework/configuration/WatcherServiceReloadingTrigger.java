package za.co.merseta.nsdms.framework.configuration;

import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.commons.configuration2.reloading.ReloadingController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class WatcherServiceReloadingTrigger {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final ExecutorService executorService;
    private final WatchService watchService;

    private final ReloadingController controller;
    private final Object controllerParameter;

    private final Path configFilePath;

    private Future<?> execution;

    WatcherServiceReloadingTrigger(ReloadingController controller, Object controllerParameter, Path configFilePath) {
        Objects.requireNonNull(controller, "Reloading controller must not be null");
        Objects.requireNonNull(configFilePath, "The path for the configuration file must not be null");
        this.configFilePath = configFilePath.toAbsolutePath();
        this.controller = controller;
        this.controllerParameter = controllerParameter;
        try {
            watchService = FileSystems.getDefault().newWatchService();
            this.configFilePath.getParent().register(watchService, ENTRY_MODIFY);
        } catch (IOException e) {
            throw new IllegalStateException("Could not set up WatcherService for config file reloads", e);
        }
        executorService = Executors.newSingleThreadExecutor();
    }

    public synchronized void start() {
        if (executorService.isShutdown()) {
            throw new IllegalStateException("Already shut down");
        }
        if (execution == null || execution.isCancelled()) {
            execution = executorService.submit(this::watchForFileChanges);
            logger.info("Execution started. Watching for file changes of {}", configFilePath);
        }
    }

    public synchronized void stop() {
        if (execution != null && !execution.isCancelled()) {
            execution.cancel(true);
            execution = null;
            logger.info("Execution stopped. No longer watching for file changes of {}", configFilePath);
        }
    }

    private void watchForFileChanges() {
        try {
            for (WatchKey watchKey = watchService.take(); watchKey != null; watchKey = watchService.take()) {
                for (WatchEvent<?> event : watchKey.pollEvents()) {
                    if (ENTRY_MODIFY.equals(event.kind())) {
                        final Path filename = Path.class.cast(event.context());
                        if (filename.equals(configFilePath.getFileName())) {
                            controller.checkForReloading(controllerParameter);
                        }
                    }
                }
                watchKey.reset();
            }
        } catch (InterruptedException e) {
            // nothing to do
        }
    }
    
    public synchronized void shutdown() {
        try {
            stop();
        } catch (RuntimeException e) {
            logger.warn("Exception while shutting down", e);
        }
        try {
            executorService.shutdown();
        } catch (RuntimeException e) {
            logger.warn("Exception while shutting down executorService", e);
        }
        try {
            watchService.close();
        } catch (IOException e) {
            logger.warn("Exception while shutting down watchService", e);
        }
    }
}