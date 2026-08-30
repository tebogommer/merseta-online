package za.co.merseta.nsdms.framework.configuration;

import java.io.File;
import java.nio.file.Paths;
import java.util.List;

import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.ReloadingFileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.convert.DefaultListDelimiterHandler;
import org.apache.commons.configuration2.event.EventListener;
import org.apache.commons.configuration2.reloading.ReloadingEvent;

import za.co.merseta.nsdms.framework.logging.NSDMSLogger;

/* This is the ONLY class allowed to use log4j2 directly outside of the NSDMSLogger 
 * We use it directly here to avoid cyclic dependency between this class and NSDMSLogger.
 * The NSDMSLogger uses this class to get the log4j2 filename.
*/

public class NSDMSConfiguration {
    private static final String NSDMS_DEFAULT_CONFIG_FILE = "/var/lib/tomcat8/conf/nsdms-configuration.properties";
    private static final String NSDMS_CONFIG_FILE_ENV_VARIABLE = "nsdms.config.file";
    private static volatile NSDMSConfiguration INSTANCE;
    private FileBasedConfiguration config;
    private static ReloadingFileBasedConfigurationBuilder<FileBasedConfiguration> builder;
    private static final String CATALINA_BASE_PROPERTY = "catalina.base";
    private String fileName;
    private static final NSDMSLogger logger = NSDMSLogger.getLogger(NSDMSConfiguration.class);

    private NSDMSConfiguration() {
        logger.info("NSDMSConfiguration() - {}", "START");
        fileName = System.getProperty(NSDMS_CONFIG_FILE_ENV_VARIABLE);

        if (fileName == null || fileName.trim().length() == 0) {
            if (System.getProperty(CATALINA_BASE_PROPERTY) != null
                    && System.getProperty(CATALINA_BASE_PROPERTY).length() > 0) {
                fileName = String.format("%s/conf/%s", System.getProperty(CATALINA_BASE_PROPERTY),
                        Paths.get(NSDMS_DEFAULT_CONFIG_FILE).getFileName());
            } else {
                fileName = NSDMS_DEFAULT_CONFIG_FILE;
            }
        }

        logger.debug("Configuration file name = {}", fileName);
        builder = new ReloadingFileBasedConfigurationBuilder<FileBasedConfiguration>(PropertiesConfiguration.class)
                .configure(new Parameters().fileBased().setFileName(fileName)
                        .setThrowExceptionOnMissing(true)
                        .setListDelimiterHandler(new DefaultListDelimiterHandler(',')));

        logger.debug("Starting the WatcherServiceReloadingTrigger service");
        WatcherServiceReloadingTrigger trigger = new WatcherServiceReloadingTrigger(builder.getReloadingController(),
                null,
                new File(fileName).toPath());
        trigger.start();

        try {
            config = builder.getConfiguration();
        } catch (Exception e) {
            logger.error("Failed to load config file", e);
            throw new RuntimeException(String.format("Failed to load NSDMS configuration file [%s] with error [%s:%s]",
                    fileName, e.getClass(), e.getMessage()),e);
        }

        builder.getReloadingController().addEventListener(ReloadingEvent.ANY, new EventListener<ReloadingEvent>() {
            @Override
            public void onEvent(ReloadingEvent event) {
                logger.debug("Received a reloading event of type [{}]", event.toString());
                if (builder.getReloadingController().checkForReloading(null)) {
                    logger.debug("File is ready for reload");
                    synchronized (NSDMSConfiguration.class) {
                        if (builder.getReloadingController().checkForReloading(null)) {
                            builder.getReloadingController().resetReloadingState();
                            try {
                                config = builder.getConfiguration();
                                logger.debug("File reloaded successfully");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        });

        logger.info("NSDMSConfiguration() - {}", "END");
    }

    private static NSDMSConfiguration getInstance() {
        if (INSTANCE == null) {
            synchronized (NSDMSConfiguration.class) {
                if (INSTANCE == null) {
                    INSTANCE = new NSDMSConfiguration();
                }
            }
        }
        return INSTANCE;
    }

    public static String getString(String name) {
        // return getInstance().builder.getConfiguration().getString(name);
        return getInstance().config.getString(name);
    }

    public static String getString(String name, String defaultValue) {
        return getInstance().config.getString(name, defaultValue);
    }

    public static int getInt(String name) {
        return getInstance().config.getInt(name);
    }

    public static int getInt(String name, int defaultValue) {
        return getInstance().config.getInt(name, defaultValue);
    }

    public static boolean getBoolean(String name) {
        return getInstance().config.getBoolean(name);
    }

    public static boolean getBoolean(String name, boolean defaultValue) {
        return getInstance().config.getBoolean(name, defaultValue);
    }

    public static long getLong(String name) {
        return getInstance().config.getLong(name);
    }

    public static long getLong(String name, long defaultValue) {
        return getInstance().config.getLong(name, defaultValue);
    }

    public static double getDouble(String name) {
        return getInstance().config.getDouble(name);
    }

    public static double getDouble(String name, Double defaultValue) {
        return getInstance().config.getDouble(name, defaultValue);
    }

    public static List<String> getList(String name) {
        return getInstance().config.getList(String.class, name);
    }
}