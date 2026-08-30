package haj.com.framework;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Set;

import javax.faces.context.FacesContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

// TODO: Auto-generated Javadoc
/**
 * The Class AbstractService.
 */
public abstract class AbstractService implements Serializable
{
  
  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 1L;
  
  /** The logger. */
  protected final Log logger = LogFactory.getLog(this.getClass());
 
  /** The auditlog. */
  public Map<String, Object> auditlog;
  
  /** The  resourceBundle */
  private ResourceBundle resourceBundle;
  
  /**
   * Instantiates a new abstract service.
   */
  public AbstractService() {
	super();
	// TODO Auto-generated constructor stub
   }


/**
 * Instantiates a new abstract service.
 *
 * @param auditlog the auditlog
 */
public AbstractService(Map<String, Object> auditlog) {
	super();
	this.auditlog = auditlog;
}

	public AbstractService(ResourceBundle resourceBundle) {
		super();
		this.resourceBundle = resourceBundle;
	}

	public AbstractService(Map<String, Object> auditlog, ResourceBundle resourceBundle) {
		super();
		this.auditlog = auditlog;
		this.resourceBundle = resourceBundle;
	}
	
	public String getEntryLanguage(String key, Object ... params) {
		String value = "";
		try {
			value = resourceBundle.getString(key);
			MessageFormat formatter = new MessageFormat(value, FacesContext.getCurrentInstance().getViewRoot().getLocale());
			value = formatter.format(params);
		} catch (MissingResourceException missingException) {
			missingException.printStackTrace();
		}
		return value;
	}
	
	public String getEntryLanguage(String key) {
	    String value = "";
	    try {
	      value = resourceBundle.getString(key);
	    } catch (MissingResourceException missingException) {
	     // logger.fatal("The Key Message doesn't exist in any resource file key=" + key);
	      missingException.printStackTrace();
	    }
	    return value;
	  }
/**
 * Get an instance of current date, no idea why this is synchronized
 * but has been left so from original e-port code.
 *
 * @return the synchronized date
 */
  protected synchronized Date getSynchronizedDate()
  {
    return new Date();
  }
  
 /*
  * Removes duplicates from an List
  * */
  public static <T> List<T> removeDuplicatesFromList(List<T> list) 
  { 
      Set<T> set = new LinkedHashSet<>(); 
      set.addAll(list); 
      list.clear(); 
      list.addAll(set); 
      return list; 
  } 
}
