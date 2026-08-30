package haj.com.listener;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

// TODO: Auto-generated Javadoc
/**
 * The listener interface for receiving session events.
 * The class that is interested in processing a session
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addSessionListener<code> method. When
 * the session event occurs, that object's appropriate
 * method is invoked.
 *
 * @see SessionEvent
 */
public class SessionListener implements HttpSessionListener
{
  
  /** The logger. */
  private final Log logger = LogFactory.getLog(this.getClass());

  /* (non-Javadoc)
   * @see javax.servlet.http.HttpSessionListener#sessionCreated(javax.servlet.http.HttpSessionEvent)
   */
  @Override
  public void sessionCreated(HttpSessionEvent event)
  {
    logger.info("Current Session created : " + event.getSession().getId());
  }

  /* (non-Javadoc)
   * @see javax.servlet.http.HttpSessionListener#sessionDestroyed(javax.servlet.http.HttpSessionEvent)
   */
  @Override
  public void sessionDestroyed(HttpSessionEvent event)
  {
    HttpSession session = event.getSession();
    logger.info("Current Session destroyed :" + session.getId());
    
    /*
     * 
     * nobody can reach user data after this point because session is
     * invalidated already. So, get the user data from session and save its
     * logout information before losing it. User's redirection to the timeout
     * page will be handled by the SessionTimeoutFilter.
     */
    try
    {
      redirect("http://www.metra.co.za");
    }
    catch (Exception e)
    {
      e.printStackTrace();
      logger.fatal("Error while logging out at session destroyed.", e);
    }
  }
  
  /**
   * Prepare logout info and logout active user.
   *
   * @param session the session
   */
  public void prepareLogoutInfoAndLogoutActiveUser(HttpSession session)
  {
    session.setAttribute("uobj", null);
  }
  
  /**
   * Redirect.
   *
   * @param outcome the outcome
   */
  private void redirect(String outcome)
  {
    try
    {
      FacesContext facesContext = FacesContext.getCurrentInstance();
      if (facesContext!=null && facesContext.getExternalContext()!=null)
      {
        HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
        if (response!=null)
          response.sendRedirect(outcome);
      }
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
    }
  }
}
