package za.co.merseta.nsdms.jms;

import javax.jms.MessageListener;
import javax.jms.Session;
import za.co.merseta.nsdms.framework.logging.NSDMSLogger;

public abstract class NSDMSJMSListener  implements MessageListener {
    protected Session session;
    protected int maxRedeliveries;
    private static final NSDMSLogger logger = NSDMSLogger.getLogger(NSDMSJMSListener.class);
    public NSDMSJMSListener(Session session, int maxRedeliveries){
        logger.info("NSDMSJMSListener() - {}", "START");        
        logger.info("Parameters Received \n \t - maxRedeliveries= {}",maxRedeliveries);        
        this.session = session;
        this.maxRedeliveries = maxRedeliveries;
        logger.info("NSDMSJMSListener() - {}", "END");        
    }
}
