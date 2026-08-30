package haj.com.framework;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

// TODO: Auto-generated Javadoc
/**
 * The Class GenericAuditLog.
 */
public class GenericAuditLog implements Serializable {

	/** The Constant logger. */
	protected static final Log logger = LogFactory.getLog(GenericAuditLog.class);

	  
	  /**
	   * Write the current event to the event log.
	   * @param additionalInfo String,Object pairs of any additional/override data to store on the event.
	   */
	  public static void log(Object... additionalInfo)
	  {
	    Map<String, Object> map = new HashMap<String, Object>();
	    try
	    {
	      StackTraceElement[] elements = new Throwable().getStackTrace();
	      int counter = 0;
	      String callerMethodName = null;
	      String callerClassName = null;
	      do
	      {
	        callerMethodName = elements[counter].getMethodName();
	        callerClassName = elements[counter].getClassName();
	        counter++;
	      } while (callerClassName.equalsIgnoreCase(AbstractUI.class.getName()));
	      
	      map.put("callingClass", callerClassName);
	      map.put("callingMethod", callerMethodName);
	      
	      if (additionalInfo!=null && additionalInfo.length>0)
	        for (int i = 1 ; i < additionalInfo.length ; i += 2)
	          if (additionalInfo[i-1]!=null)
	            map.put(additionalInfo[i-1].toString(), additionalInfo[i]);
	      populateDefaults(map);
	     // AuditService auditService = new AuditService();
	     // auditService.auditEvent(map);
	    }
	    catch (Exception e)
	    {
	      logger.error("Failed to log event", e);
	    }
	  }


	/**
	 * Populate defaults.
	 *
	 * @param map the map
	 */
	private static void populateDefaults(Map<String, Object> map) {
		if (!map.containsKey("userId")) map.put("userId", "0");
		if (!map.containsKey("policyRefNo")) map.put("policyRefNo", "0");
		if (!map.containsKey("memberId")) map.put("memberId", "0");
		if (!map.containsKey("ip")) map.put("ip", "0");
	}


}
