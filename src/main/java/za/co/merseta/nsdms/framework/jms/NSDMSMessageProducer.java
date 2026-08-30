package za.co.merseta.nsdms.framework.jms;

import java.io.Serializable;
import java.util.Map;
import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.TextMessage;
import org.apache.activemq.ActiveMQConnectionFactory;
import za.co.merseta.nsdms.framework.configuration.NSDMSConfiguration;
import za.co.merseta.nsdms.framework.exception.TechnicalException;
import za.co.merseta.nsdms.framework.logging.NSDMSLogger;
import za.co.merseta.nsdms.jms.JMSConstants;

public class NSDMSMessageProducer {
    private static final NSDMSLogger logger = NSDMSLogger.getLogger(NSDMSMessageProducer.class);
    private ActiveMQConnectionFactory connectionFactory = null;
    private Connection connection = null;
    private Session session = null;
    private Destination destination = null;
    private MessageProducer producer = null;

    public static void sendTextMessage(String queueName, Map<String, Object> jmsProperties)
            throws TechnicalException {
        logger.info("sendTextMessage() - {}", "START");
        sendTextMessage(queueName, jmsProperties, null);
        logger.info("sendTextMessage() - {}", "END");
    }

    public static void sendTextMessage(String queueName, Map<String, Object> jmsProperties, String message)
            throws TechnicalException {
        logger.info("sendTextMessage() - {}", "START");
        NSDMSMessageProducer nsdmsMessageProducer = new NSDMSMessageProducer();
        try {
            logger.debug("creating a message");
            nsdmsMessageProducer.createMessageProducer(queueName);
            TextMessage jmsMessage = nsdmsMessageProducer.session.createTextMessage();
            if (message != null && message.trim().length() > 0) {
                jmsMessage.setText(message);
            }
            for (String propertyName : jmsProperties.keySet()) {
                jmsMessage.setObjectProperty(propertyName, jmsProperties.get(propertyName));
            }
            logger.debug("sending the message to a queue");
            nsdmsMessageProducer.producer.send(jmsMessage);
        } catch (TechnicalException e) {
            throw e;
        } catch (Exception e) {
            String errorMessage = String.format("An exception occured while enqueuing a message [%s:%s]", e.getClass(),
                    e.getMessage());
            logger.error(errorMessage, e);
            throw new TechnicalException(errorMessage, e);
        } finally {
            try {
                nsdmsMessageProducer.producer.close();
            } catch (Exception e) {
            }
            try {
                nsdmsMessageProducer.session.close();
            } catch (Exception e) {
            }
            try {
                nsdmsMessageProducer.connection.stop();
            } catch (Exception e) {
            }
            try {
                nsdmsMessageProducer.connection.close();
            } catch (Exception e) {
            }
        }
        logger.info("sendTextMessage() - {}", "END");
    }

    public static void sendObjectMessage(String queueName, Map<String, Object> jmsProperties) throws TechnicalException{
        logger.info("sendObjectMessage() - {}", "START");
        sendObjectMessage(queueName, jmsProperties, null);
        logger.info("sendObjectMessage() - {}", "END");        
    }
    
    public static void sendObjectMessage(String queueName, Map<String, Object> jmsProperties, Serializable object)
            throws TechnicalException {
        logger.info("sendObjectMessage() - {}", "START");
        NSDMSMessageProducer nsdmsMessageProducer = new NSDMSMessageProducer();
        try {
            logger.debug("creating a message");
            nsdmsMessageProducer.createMessageProducer(queueName);
            ObjectMessage jmsMessage = nsdmsMessageProducer.session.createObjectMessage();
            if (object != null) {
                jmsMessage.setObject(object);
            }
            for (String propertyName : jmsProperties.keySet()) {
                jmsMessage.setObjectProperty(propertyName, jmsProperties.get(propertyName));
            }
            logger.debug("sending the message to a queue");
            nsdmsMessageProducer.producer.send(jmsMessage);
        } catch (TechnicalException e) {
            throw e;
        } catch (Exception e) {
            String errorMessage = String.format("An exception occured while enqueuing a message [%s:%s]", e.getClass(),
                    e.getMessage());
            logger.error(errorMessage, e);
            throw new TechnicalException(errorMessage, e);
        } finally {
            try {
                nsdmsMessageProducer.producer.close();
            } catch (Exception e) {
            }
            try {
                nsdmsMessageProducer.session.close();
            } catch (Exception e) {
            }
            try {
                nsdmsMessageProducer.connection.stop();
            } catch (Exception e) {
            }
            try {
                nsdmsMessageProducer.connection.close();
            } catch (Exception e) {
            }
        }
        logger.info("sendObjectMessage() - {}", "END");
    }

    private void createMessageProducer(String queueName) throws TechnicalException {

        String url = NSDMSConfiguration.getString(JMSConstants.NSDMS_JMS_BROKER_URL_PROPERTY);
        logger.debug("JMS BROKER URL = {}", url);
        String username = NSDMSConfiguration.getString(JMSConstants.NSDMS_JMS_BROKER_USERNAME_PROPERTY);
        logger.debug("JMS BROKER USERNAME = {}", username);
        String password = NSDMSConfiguration.getString(JMSConstants.NSDMS_JMS_BROKER_PASSWORD_PROPERTY);

        try {
            logger.debug("creating JMS connection factory");
            connectionFactory = new ActiveMQConnectionFactory(username, password, url);
            connectionFactory.setTrustAllPackages(true);
            logger.debug("creating a queue connection");
            connection = connectionFactory.createConnection();
            logger.debug("starting the queue connection");
            connection.start();
            logger.debug("creating a queue session");
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            logger.debug("creating a queue");
            destination = session.createQueue(queueName);
            logger.debug("creating a producer");
            producer = session.createProducer(destination);
            producer.setDeliveryMode(DeliveryMode.PERSISTENT);
        } catch (Exception e) {
            String errorMessage = String.format("An exception occured while enqueuing a message [%s:%s]", e.getClass(),
                    e.getMessage());
            logger.error(errorMessage, e);
            throw new TechnicalException(errorMessage, e);
        }
    }
}
