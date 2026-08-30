package za.co.merseta.nsdms.jms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import za.co.merseta.nsdms.framework.configuration.NSDMSConfiguration;
import za.co.merseta.nsdms.framework.logging.NSDMSLogger;

public class JMSConstants {
    public static final String NSDMS_JMS_BROKER_URL_PROPERTY = "nsdms.jms.broker.url";
    public static final String NSDMS_JMS_BROKER_USERNAME_PROPERTY = "nsdms.jms.broker.username";
    public static final String NSDMS_JMS_BROKER_PASSWORD_PROPERTY = "nsdms.jms.broker.password";
    public static final String NSDMS_JMS_QUEUE_LIST_PROPERTY = "nsdms.jms.queue.list";
    public static final String NSDMS_JMS_QUEUE_DEFAULT_CONSUMERCOUNT_PROPERTY = "nsdms.jms.queue.default.consumercount";
    public static final String NSDMS_JMS_QUEUE_DEFAULT_INITIALREDELIVERYDELAY_PROPERTY = "nsdms.jms.queue.default.initialredeliverydelay";
    public static final String NSDMS_JMS_QUEUE_DEFAULT_USEEXPONENTIALBACKOFF_PROPERTY = "nsdms.jms.queue.default.useexponentialbackoff";
    public static final String NSDMS_JMS_QUEUE_DEFAULT_MAXIMUMREDELIVERIES_PROPERTY = "nsdms.jms.queue.default.maximumredeliveries";
    public static final String NSDMS_JMS_QUEUE_DEFAULT_MAXIMUMREDELIVERYDELAY_PROPERTY = "nsdms.jms.queue.default.maximumredeliverydelay";
    public static final String NSDMS_JMS_QUEUE_DEFAULT_BACKOFFMULTIPLIER_PROPERTY = "nsdms.jms.queue.default.backoffmultiplier";
    public static final String NSDMS_JMS_QUEUE_DEFAULT_REDELIVERYDELAY_PROPERTY = "nsdms.jms.queue.default.redeliverydelay";
    private static final String NSDMS_JMS_TRUSTED_PACKAGES = "nsdms.jms.broker.trustedpackages";
    private static final NSDMSLogger logger = NSDMSLogger.getLogger(JMSConstants.class);
    
    public static List<String> getTrustedPackages() {
        logger.info("getTrustedPackages() - {}", "START");
        List<String> list = new ArrayList<String>(Arrays.asList(
                "java.lang,javax.security,java.util,org.apache.activemq,org.fusesource.hawtbuf,com.thoughtworks.xstream.mapper,java.sql"
                        .split(",")));
        try{
            list.addAll(NSDMSConfiguration.getList(NSDMS_JMS_TRUSTED_PACKAGES));
        }catch(Exception e){
            logger.error("Exception occured while adding custom packages to the ActiveMQ trusted packages list",e);
            logger.error("NO CUSTOM CLASSES CAN BE USED IN OBJECT MESSAGES FOR ANY QUEUE");
        }
        logger.info("getTrustedPackages() - {}", "END");
        return list;
    }
}
