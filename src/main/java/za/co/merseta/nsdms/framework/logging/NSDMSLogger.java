package za.co.merseta.nsdms.framework.logging;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;
import org.apache.logging.log4j.util.Supplier;
import za.co.merseta.nsdms.framework.configuration.NSDMSConfiguration;

public class NSDMSLogger {
    private Logger log4jLogger = null;

    static{
        Configurator.initialize(null,NSDMSConfiguration.getString("nsdms.log4j2.filename"));
    }

    private NSDMSLogger(Logger logger) {
        this.log4jLogger = logger; 
    }

    public static NSDMSLogger getLogger(final Class<?> className) {
        return new NSDMSLogger(LogManager.getLogger(className));
    }

    // This is only done for backwards compatibility with the current NSDMS useless logging
    public void fatal(Throwable exception) {
        if (log4jLogger.isFatalEnabled()) {
            log4jLogger.fatal("A fatal exception occured", exception);
        }
    }

    public void fatal(String message, Throwable exception) {
        if (log4jLogger.isFatalEnabled()) {
            log4jLogger.fatal(message, exception);
        }
    }

    public void fatal(String parameterizedMessage, Object... parameters) {
        if (log4jLogger.isFatalEnabled()) {
            log4jLogger.fatal(parameterizedMessage, parameters);
        }
    }

    public void fatal(String parameterizedMessage, Supplier<?>... parameters) {
        if (log4jLogger.isFatalEnabled()) {
            log4jLogger.fatal(parameterizedMessage, parameters);
        }
    }

    public void error(String message,Throwable exception) {
        if (log4jLogger.isErrorEnabled()) {
            log4jLogger.error(message, exception);
        }
    }

    public void error(String parameterizedMessage, Supplier<?>... parameters) {
        if (log4jLogger.isErrorEnabled()) {
            log4jLogger.error(parameterizedMessage, parameters);
        }
    }

    public void error(String parameterizedMessage, Object... parameters) {
        if (log4jLogger.isErrorEnabled()) {
            log4jLogger.error(parameterizedMessage, parameters);
        }
    }    

    public void warn(String parameterizedMessage, Supplier<?>... parameters) {
        if (log4jLogger.isWarnEnabled()) {
            log4jLogger.warn(parameterizedMessage, parameters);
        }
    }

    public void warn(String parameterizedMessage, Object... parameters) {
        if (log4jLogger.isWarnEnabled()) {
            log4jLogger.warn(parameterizedMessage, parameters);
        }
    }    

    public void info(String parameterizedMessage, Supplier<?>... parameters) {
        if (log4jLogger.isInfoEnabled()) {
            log4jLogger.info(parameterizedMessage, parameters);
        }
    }

    public void info(String parameterizedMessage, Object... parameters) {
        if (log4jLogger.isInfoEnabled()) {
            log4jLogger.info(parameterizedMessage, parameters);
        }
    }

    public void debug(String parameterizedMessage, Supplier<?>... parameters) {
        if (log4jLogger.isDebugEnabled()) {
            log4jLogger.debug(parameterizedMessage, parameters);
        }
    }
    
    public void debug(String parameterizedMessage, Object... parameters) {
        if (log4jLogger.isDebugEnabled()) {
            log4jLogger.debug(parameterizedMessage, parameters);
        }
    }

    public void trace(String parameterizedMessage, Supplier<?>... parameters) {
        if (log4jLogger.isTraceEnabled()) {
            log4jLogger.trace(parameterizedMessage, parameters);
        }
    }

    public void trace(String parameterizedMessage, Object... parameters) {
        if (log4jLogger.isTraceEnabled()) {
            log4jLogger.trace(parameterizedMessage, parameters);
        }
    }    
}
