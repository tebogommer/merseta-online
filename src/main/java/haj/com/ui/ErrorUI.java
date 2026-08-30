/*
 * 
 */
package  haj.com.ui;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.SQLException;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;

import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;



// TODO: Auto-generated Javadoc
/**
 * The Class ErrorUI.
 */
@ManagedBean(name = "errorUI")
@RequestScoped
public class ErrorUI extends AbstractUI {


    /**
     * Inits the.
     */
    @PostConstruct
	public void init() {
		try {
			runInit();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Run init.
	 *
	 * @throws Exception the exception
	 */
	private void runInit() throws Exception {
	}

	   /**
   	 * Gets the stack trace.
   	 *
   	 * @return the stack trace
   	 */
   	public String getStackTrace() {
		      FacesContext context = FacesContext.getCurrentInstance();
		      Map<String, Object> request 
		         = context.getExternalContext().getRequestMap();
		      Throwable ex = (Throwable) request.get("javax.servlet.error.exception");
		      StringWriter sw = new StringWriter();
		      PrintWriter pw = new PrintWriter(sw);
		      fillStackTrace(ex, pw);
		      return sw.toString();
		   }
		 
		   /**
   		 * Fill stack trace.
   		 *
   		 * @param t the t
   		 * @param w the w
   		 */
   		private static void fillStackTrace(Throwable t, PrintWriter w) {
		      if (t == null) return;
		      t.printStackTrace(w);
		      if (t instanceof ServletException) {
		         Throwable cause = ((ServletException) t).getRootCause();
		         if (cause != null) {
		            w.println("Root cause:");
		            fillStackTrace(cause, w);
		         }
		      } else if (t instanceof SQLException) {
		         Throwable cause = ((SQLException) t).getNextException();
		         if (cause != null) {
		            w.println("Next exception:");
		            fillStackTrace(cause, w);
		         }
		      } else {
		         Throwable cause = t.getCause();
		         if (cause != null) {
		            w.println("Cause:");
		            fillStackTrace(cause, w);
		         }
		      }
		   }
}
