package za.co.merseta.nsdms.externalsystems.greatplains.dynamicgp.adapter;

import javax.xml.datatype.DatatypeFactory;

import com.microsoft.schemas.dynamics.gp._2006._01.BatchKey;
import com.microsoft.schemas.dynamics.gp._2006._01.GLTransaction;
import com.microsoft.schemas.dynamics.gp._2006._01.GLTransactionKey;
import haj.com.service.JAXBService;
import za.co.merseta.nsdms.externalsystems.greatplains.dynamicgp.GPUpdateHandler;
import za.co.merseta.nsdms.framework.exception.BusinessException;
import za.co.merseta.nsdms.framework.exception.RequestTimeOutException;
import za.co.merseta.nsdms.framework.exception.TechnicalException;
import za.co.merseta.nsdms.framework.exception.ValidationException;
import za.co.merseta.nsdms.framework.logging.NSDMSLogger;

public class CreateGLTransactionAdapter extends GPUpdateHandler<GLTransaction, Void> {
    private static final NSDMSLogger logger = NSDMSLogger.getLogger(CreateGLTransactionAdapter.class);
    private GLTransaction glTransaction;

    public void createGLTransaction(GLTransaction glTransaction)
            throws ValidationException, BusinessException, TechnicalException, RequestTimeOutException {
        logger.info("createGLTransaction() - {}", "START");
        logger.trace("Parameters Received \n \t address ={}", JAXBService.marshallToStringNoException(glTransaction));

        this.glTransaction = glTransaction;
        processRequest();

        logger.info("createGLTransaction() - {}", "END");
    }

    @Override
    protected GLTransaction createGPRequest() {
        logger.info("createGPRequest() - {}", "START");
        logger.trace("Response Returned \n ", () -> JAXBService.marshallToStringNoException(this.glTransaction));
        logger.info("createGPRequest() - {}", "END");

        return this.glTransaction;
    }

    @Override
    protected Void callGP() throws Exception {
        logger.info("callGP() - {}", "START");
        getGPProxy().createGLTransaction(glTransaction, getContext(),
                createPolicy("ffa3b9a6-fa25-47a2-8179-cb37c695455e"));
        logger.info("callGP() - {}", "END");
        return null;
    }

    public static void main(String[] args) throws Exception {
        BatchKey batchKey = new BatchKey();
        batchKey = new BatchKey();
        batchKey.setSource("MIS-System");
        batchKey.setId("LMIS00000000248");

        GLTransaction transaction = new GLTransaction();
        transaction.setKey(new GLTransactionKey(null,
                DatatypeFactory.newInstance().newXMLGregorianCalendar("2022-07-31T00:00:00.000+02:00")));
        transaction.setBatchKey(batchKey);
        transaction.setReference("Levy for July 2021-248 (2000)");
        new CreateGLTransactionAdapter().createGLTransaction(transaction);
    }

}
