package za.co.merseta.nsdms.batch.grant.gp.payments;

import java.util.HashMap;
import java.util.Map;
import haj.com.entity.GpGrantBatchEntry;
import za.co.merseta.nsdms.batch.grant.gp.DiskBackedBatchMap;
import za.co.merseta.nsdms.framework.exception.TechnicalException;
import za.co.merseta.nsdms.framework.jms.NSDMSMessageProducer;
import za.co.merseta.nsdms.framework.logging.NSDMSLogger;

public class GrantPaymentsBatchMessageProducer {
    private static final NSDMSLogger logger = NSDMSLogger.getLogger(GrantPaymentsBatchMessageProducer.class);
    private final static String QUEUE_NAME = "batch.grants.gp.payments.queue";

    public static void enqueuePaymentsProcessingEntry(Long batchID, int batchNumber, String wspType, GpGrantBatchEntry entry)
            throws TechnicalException {
        logger.info("enqueuePaymentsProcessingEntry() - {}", "START");
        logger.debug("Parameters Received \n \t batchID = {},batchNumber = {} , userID = {}, wspType = {} \n entry-id = {}", batchID,
                batchNumber, wspType, entry.getId());

        Map<String, Object> jmsProperties = new HashMap<>();
        jmsProperties.put(GrantBatchPaymentsMessageListener.JMS_HEADER_BATCH_ID, batchID);
        jmsProperties.put(GrantBatchPaymentsMessageListener.JMS_HEADER_WSP_TYPE, wspType);
        jmsProperties.put(GrantBatchPaymentsMessageListener.JMS_HEADER_BATCH_NUMBER, batchNumber);
        NSDMSMessageProducer.sendObjectMessage(QUEUE_NAME, jmsProperties, entry);
        DiskBackedBatchMap.getInstance().put(GrantPaymentsBatchMessageProducer.class, batchID, entry.getId());
        logger.info("enqueuePaymentsProcessingEntry() - {}", "END");
    }
}
