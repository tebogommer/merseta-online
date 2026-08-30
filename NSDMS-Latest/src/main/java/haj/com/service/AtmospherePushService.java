package haj.com.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.primefaces.push.EventBus;
import org.primefaces.push.EventBusFactory;

import haj.com.framework.AbstractService;

// TODO: Auto-generated Javadoc
/**
 * The Class AtmospherePushService.
 */
public class AtmospherePushService extends AbstractService {
	
	/** The Constant logger. */
	protected static final Log logger = LogFactory.getLog(AtmospherePushService.class);
	 
	/**
	 * Refresh work flow badge.
	 *
	 * @param userid the userid
	 */
	public static void refreshWorkFlowBadge(String userid) {
		try {
			 EventBus eventBus = EventBusFactory.getDefault().eventBus();
            eventBus.publish(userid,"RefreshBadge");
		} catch (Exception e) {
			logger.fatal(e);
		}
	}
	
	/**
	 * Refresh work flow badge.
	 *
	 * @param userid the userid
	 */
	public static void refreshWorkFlowBadge(Long userid) { 
		refreshWorkFlowBadge(""+userid);
	}
}
