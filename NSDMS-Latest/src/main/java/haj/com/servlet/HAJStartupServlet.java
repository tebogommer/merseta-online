package haj.com.servlet;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.dao.EmployeePagesDAO;
import haj.com.dao.HAJPropertiesDAO;
import haj.com.entity.EmployeePages;
import haj.com.entity.HAJProperties;
import haj.com.jobs.HAJScheduler;

/**
 * The Class HAJStartupServlet.
 */
public class HAJStartupServlet extends HttpServlet {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The logger. */
	protected final Log logger = LogFactory.getLog(this.getClass());

	/*
	 * (non-Javadoc)
	 *
	 * @see javax.servlet.GenericServlet#init(javax.servlet.ServletConfig)
	 */
	@Override
	public void init(ServletConfig config) throws ServletException {
		// init super
		super.init(config);
		System.getProperties().put("DD-APP-PATH", super.getServletContext().getRealPath("/"));
		System.getProperties().put("DD-SERVER-TYPE", super.getServletContext().getServerInfo());
		java.util.Properties prop = new java.util.Properties();
		loadpropsFromDB(prop);
		System.getProperties().put("DD-PROPERTIES", prop);
		loadConstants();
		startScheduler();
		initScreenHelp();
		// cleanup file system
		// cleanupFileSystem();
	}

	private void initScreenHelp() {
		// new OnScreenHelpService().populateHelpTextTable();

	}

	/**
	 * Loadprops from DB.
	 *
	 * @param prop
	 *            the prop
	 */
	private void loadpropsFromDB(Properties prop) {
		try {
			List<HAJProperties> list = new HAJPropertiesDAO().allProps();
			for (HAJProperties p : list) {
				try {
					prop.put(p.geteProperty().trim(), p.geteValue().trim());
				} catch (Exception e) {
					logger.fatal(e);
				}

			}
			List<EmployeePages> epages = new EmployeePagesDAO().allEmployeePages();
			Map<String, String> pages = new HashMap<>();
			for (EmployeePages employeePages : epages) {
				pages.put(employeePages.getPage(), employeePages.getPage());
			}

			prop.put("employee_pages", pages);

		} catch (Exception e) {
			logger.fatal(e);
		}

	}

	/**
	 * Load constants.
	 */
	protected void loadConstants() {
		logger.info("in loadConstants()");

	}

	/**
	 * Start scheduler.
	 */
	private void startScheduler() {
		new Thread(new Runnable() {
			@Override
			public void run() {

				try {
					logger.info("HAJStartupServlet: starting scheduler");
					new HAJScheduler().run();
					// new HAJScheduler().run3();
					logger.info("HAJStartupServlet: scheduler started");
				} catch (Exception e) {
					logger.fatal(e);
				}
			}
		}).start();

	}
}
