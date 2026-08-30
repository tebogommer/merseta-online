package za.co.merseta.nsdms.batch.grant.gp.validation;

import java.util.HashMap;
import java.util.Map;
import za.co.merseta.nsdms.batch.grant.gp.DiskBackedBatchMap;
import za.co.merseta.nsdms.framework.exception.TechnicalException;
import za.co.merseta.nsdms.framework.jms.NSDMSMessageProducer;
import za.co.merseta.nsdms.framework.logging.NSDMSLogger;

public class GrantBatchValidationMessageProducer {
    private static final NSDMSLogger logger = NSDMSLogger.getLogger(GrantBatchValidationMessageProducer.class);
    private final static String QUEUE_NAME = "batch.grants.gp.validation.queue";

    public static void enqueueValidationEntry(String levyNumber, Long batchID, Long userID, String wspType)
            throws TechnicalException {
        logger.info("enqueueValidationEntry() - {}", "START");
        logger.debug("Parameters Received \n \t levyNumber = {},batchID = {} , userID = {}, wspType = {}", levyNumber,
                batchID, userID, wspType);
        Map<String, Object> jmsProperties = new HashMap<>();
        jmsProperties.put(GrantBatchValidationMessageListener.JMS_HEADER_LEVY_NUMBER, levyNumber);
        jmsProperties.put(GrantBatchValidationMessageListener.JMS_HEADER_BATCH_ID, batchID);
        jmsProperties.put(GrantBatchValidationMessageListener.JMS_HEADER_WSP_TYPE, wspType);
        jmsProperties.put(GrantBatchValidationMessageListener.JMS_HEADER_USER_ID, userID);
        NSDMSMessageProducer.sendTextMessage(QUEUE_NAME, jmsProperties);
        DiskBackedBatchMap.getInstance().put(GrantBatchValidationMessageProducer.class, batchID, levyNumber);
        logger.info("enqueueValidationEntry() - {}", "END");
    }
}
