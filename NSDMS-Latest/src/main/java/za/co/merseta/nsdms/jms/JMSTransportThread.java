package za.co.merseta.nsdms.jms;

import java.util.List;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.RedeliveryPolicy;
import za.co.merseta.nsdms.framework.configuration.NSDMSConfiguration;
import za.co.merseta.nsdms.framework.logging.NSDMSLogger;

public class JMSTransportThread implements Runnable {
    private Session session;
    private Connection connection;
    private MessageConsumer consumer;
    private String username;
    private String password;
    private String url;
    private int initialRedeliveryDelay = 60000;
    private int maximumRedeliveries = 10;
    private int redeliveryDelay = 600000;
    private int backoffMultiplier = 2;
    private int maximumRedeliveryDelay = 600000;
    private boolean useExponentialBackoff = true;
    private int consumerCount = 1;
    private static final NSDMSLogger logger = NSDMSLogger.getLogger(JMSTransportThread.class);

    @Override
    public void run() {
        logger.info("run() - {}", "START");

        url = NSDMSConfiguration.getString(JMSConstants.NSDMS_JMS_BROKER_URL_PROPERTY, "vm://nsdmsbroker");
        logger.debug("JMS BROKER URL = {}", url);
        username = NSDMSConfiguration.getString(JMSConstants.NSDMS_JMS_BROKER_USERNAME_PROPERTY, "");
        logger.debug("JMS BROKER USERNAME = {}", username);
        password = NSDMSConfiguration.getString(JMSConstants.NSDMS_JMS_BROKER_PASSWORD_PROPERTY, "");

        List<String> queueList = NSDMSConfiguration.getList(JMSConstants.NSDMS_JMS_QUEUE_LIST_PROPERTY);
        logger.debug("JMS QUEUE LIST = {}", queueList);

        logger.debug("creating a queue connection factory");
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(username, password, url);
        connectionFactory.setTrustAllPackages(true);

        try {
            for (String queueName : queueList) {
                queueName = queueName.trim();

                logger.debug(String.format("Setting redelivery policy for queue [%s]", queueName));

                setDefaultReliveryProperties();
                overrideRedeliveryProperties(queueName);

                RedeliveryPolicy redeliveryPolicy = new RedeliveryPolicy();
                redeliveryPolicy.setInitialRedeliveryDelay(initialRedeliveryDelay);
                redeliveryPolicy.setUseExponentialBackOff(useExponentialBackoff);
                redeliveryPolicy.setMaximumRedeliveries(maximumRedeliveries);
                redeliveryPolicy.setMaximumRedeliveryDelay(maximumRedeliveryDelay);
                redeliveryPolicy.setBackOffMultiplier(backoffMultiplier);
                redeliveryPolicy.setRedeliveryDelay(redeliveryDelay);
                connectionFactory.setRedeliveryPolicy(redeliveryPolicy);

                logger.debug("creating a queue connection");
                this.connection = connectionFactory.createConnection();
                logger.debug("starting the queue connection");
                this.connection.start();
                logger.debug("creating a queue session");
                this.session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);

                logger.debug("creating a queue : {}", queueName);
                Destination queue = this.session.createQueue(queueName);

                for (int i = 0; i < consumerCount; i++) {
                    logger.debug("creating a queue session");
                    this.session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);

                    logger.debug("creating a consumer");
                    this.consumer = this.session.createConsumer(queue);
                    
                    String queueListenerClass = NSDMSConfiguration
                            .getString(String.format("nsdms.jms.queue.%s.listener", queueName));
                    logger.debug(String.format("starting the message listener [%s] for queue [%s]", queueListenerClass,
                            queueName));
                    this.consumer.setMessageListener(createQueueListener(queueListenerClass, maximumRedeliveries));
                }
            }

        } catch (Exception e) {
            logger.fatal("FAILED TO JMS BROKER...NO JMS FUNCTIONALITY WILL WORK", e);
        }
        logger.info("run() - {}", "END");
    }

    public void stop() {
        logger.info("stop() - {}", "START");
        try {
            this.consumer.close();
        } catch (Exception e) {
            logger.fatal("Broker not closed, redeploys wont work", e);
        }

        try {
            this.session.close();
        } catch (Exception e) {
            logger.fatal("Broker not closed, redeploys wont work", e);
        }

        try {
            this.connection.stop();
        } catch (Exception e) {
            logger.fatal("Broker not closed, redeploys wont work", e);
        }

        try {
            this.connection.close();
        } catch (Exception e) {
            logger.fatal("Broker not closed, redeploys wont work", e);
        }
        logger.info("stop() - {}", "END");
    }

    private void setDefaultReliveryProperties() {
        logger.info("setDefaultReliveryProperties() - {}", "START");
        initialRedeliveryDelay = 60000;
        maximumRedeliveries = 10;
        redeliveryDelay = 600000;
        backoffMultiplier = 2;
        maximumRedeliveryDelay = 600000;
        useExponentialBackoff = true;
        consumerCount = 1;

        initialRedeliveryDelay = NSDMSConfiguration
                .getInt(JMSConstants.NSDMS_JMS_QUEUE_DEFAULT_INITIALREDELIVERYDELAY_PROPERTY, initialRedeliveryDelay);
        logger.debug("initialRedeliveryDelay= {}", initialRedeliveryDelay);
        maximumRedeliveries = NSDMSConfiguration
                .getInt(JMSConstants.NSDMS_JMS_QUEUE_DEFAULT_MAXIMUMREDELIVERIES_PROPERTY, maximumRedeliveries);
        logger.debug("maximumRedeliveries= {}", maximumRedeliveries);
        redeliveryDelay = NSDMSConfiguration.getInt(JMSConstants.NSDMS_JMS_QUEUE_DEFAULT_REDELIVERYDELAY_PROPERTY,
                redeliveryDelay);
        logger.debug("redeliveryDelay= {}", redeliveryDelay);
        backoffMultiplier = NSDMSConfiguration.getInt(JMSConstants.NSDMS_JMS_QUEUE_DEFAULT_BACKOFFMULTIPLIER_PROPERTY,
                backoffMultiplier);
        logger.debug("backoffMultiplier= {}", backoffMultiplier);
        maximumRedeliveryDelay = NSDMSConfiguration
                .getInt(JMSConstants.NSDMS_JMS_QUEUE_DEFAULT_MAXIMUMREDELIVERYDELAY_PROPERTY, maximumRedeliveryDelay);
        logger.debug("maximumRedeliveryDelay = {}", maximumRedeliveryDelay);
        useExponentialBackoff = NSDMSConfiguration
                .getBoolean(JMSConstants.NSDMS_JMS_QUEUE_DEFAULT_USEEXPONENTIALBACKOFF_PROPERTY, useExponentialBackoff);
        logger.debug("useExponentialBackoff = {}", useExponentialBackoff);
        consumerCount = NSDMSConfiguration.getInt(JMSConstants.NSDMS_JMS_QUEUE_DEFAULT_CONSUMERCOUNT_PROPERTY, 1);
        logger.debug("consumerCount = {}", useExponentialBackoff);
        logger.info("setDefaultReliveryProperties() - {}", "END");
    }

    private void overrideRedeliveryProperties(String queueName) {
        logger.info("overrideRedeliveryProperties() - {}", "START");

        try {
            initialRedeliveryDelay = NSDMSConfiguration
                    .getInt(String.format("nsdms.jms.queue.%s.initialredeliverydelay", queueName));
            logger.debug("initialRedeliveryDelay= {}", initialRedeliveryDelay);
        } catch (Exception e) {

        }

        try {
            maximumRedeliveries = NSDMSConfiguration
                    .getInt(String.format("nsdms.jms.queue.%s.maximumredeliveries", queueName));
            logger.debug("maximumRedeliveries= {}", maximumRedeliveries);
        } catch (Exception e) {

        }

        try {
            redeliveryDelay = NSDMSConfiguration
                    .getInt(String.format("nsdms.jms.queue.%s.redeliverydelay", queueName));
            logger.debug("redeliveryDelay= {}", redeliveryDelay);
        } catch (Exception e) {

        }

        try {
            backoffMultiplier = NSDMSConfiguration
                    .getInt(String.format("nsdms.jms.queue.%s.backoffmultiplier", queueName));
            logger.debug("backoffMultiplier= {}", backoffMultiplier);
        } catch (Exception e) {

        }

        try {
            maximumRedeliveryDelay = NSDMSConfiguration
                    .getInt(String.format("nsdms.jms.queue.%s.maximumredeliverydelay", queueName));
            logger.debug("maximumRedeliveryDelay = {}", maximumRedeliveryDelay);
        } catch (Exception e) {
        }

        try {
            useExponentialBackoff = NSDMSConfiguration.getBoolean(
                    String.format("nsdms.jms.queue.%s.useexponentialbackoff", queueName));
            logger.debug("useExponentialBackoff = {}", useExponentialBackoff);
        } catch (Exception e) {

        }

        try {
            consumerCount = NSDMSConfiguration.getInt(
                    String.format("nsdms.jms.queue.%s.consumercount", queueName));
            logger.debug("useExponentialBackoff = {}", consumerCount);
        } catch (Exception e) {

        }

        logger.info("overrideRedeliveryProperties() - {}", "END");
    }

    private NSDMSJMSListener createQueueListener(String listenerClass, int redeliveryCount) {
        logger.info("createQueueListener() - {}", "START");
        logger.debug("Received Parameters \n \t listenerClass= {}", listenerClass);

        NSDMSJMSListener listener = null;

        try {
            listener = (NSDMSJMSListener) Class.forName(listenerClass).getConstructor(Session.class, Integer.TYPE)
                    .newInstance(this.session, redeliveryCount);
        } catch (Exception e) {
            logger.fatal(String.format("Failed to create an instance of class [%s]", listenerClass), e);
        }

        logger.info("createQueueListener() - {}", "END");
        return listener;
    }
}