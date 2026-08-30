package za.co.merseta.nsdms.servlet;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import za.co.merseta.nsdms.framework.logging.NSDMSLogger;
import za.co.merseta.nsdms.jms.JMSTransportThread;

public class JMSServletContextListener implements ServletContextListener {
    private static final NSDMSLogger logger = NSDMSLogger.getLogger(JMSServletContextListener.class);
    JMSTransportThread transportThread;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        logger.info("---------------------------------------------------------------------------");
        logger.info("-------------------NSDMS APPLICATION STARTED----------------------------");
        logger.info("---------------------------------------------------------------------------");

        try {
            transportThread=new JMSTransportThread();
            new Thread(transportThread,"transportthread-1").start();
        } catch (Exception e) {
            logger.fatal("Error occuured while starting up the JMS listener", e);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        logger.info("---------------------------------------------------------------------------");
        logger.info("-------------------NSDMS APPLICATION DESTROYED--------------------------");
        logger.info("---------------------------------------------------------------------------");
        if(transportThread!=null){
            transportThread.stop();
        }
    }
}

