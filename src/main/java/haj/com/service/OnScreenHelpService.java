package haj.com.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.constants.HAJConstants;
import haj.com.dao.OnScreenHelpDAO;
import haj.com.entity.OnScreenHelp;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;
import haj.com.utils.GenericUtility;

public class OnScreenHelpService extends AbstractService {
	
	/** The dao. */
	private OnScreenHelpDAO dao = new OnScreenHelpDAO();

	private Map<String, OnScreenHelp> map = null;

	private static OnScreenHelpService onScreenHelpService = null;

	public static synchronized OnScreenHelpService instance() {
		if (onScreenHelpService == null) {
			onScreenHelpService = new OnScreenHelpService();
		}
		return onScreenHelpService;
	}

	public OnScreenHelpService() {
		super();
		initMap();
	}

	private void initMap() {
		if (map == null)
			map = new HashMap<String, OnScreenHelp>();
		try {
			List<OnScreenHelp> list = dao.allActiveOnScreenHelp();
			list.forEach(a -> {
				map.put(a.getScreen().trim(), a);
			});
		} catch (Exception e) {
			logger.fatal(e);
		}
	}

	private void reloadMap() {
		this.map = null;
		initMap();
	}

	public String findHelp(String screen) {
		if (map == null)
			return null;
		else {
			if (screen.contains("pages/"))
				screen = screen.substring(screen.indexOf("pages/")).trim();
			if (screen.contains("mis/"))
				screen = screen.substring(screen.indexOf("mis/")).trim();
			// logger.info(screen);
			return map.containsKey(screen) ? map.get(screen).getHelpText() : null;
		}
	}

	/**
	 * Get all OnScreenHelp
	 * 
	 * @author TechFinium
	 * @see OnScreenHelp
	 * @return a list of {@link haj.com.entity.OnScreenHelp}
	 * @throws Exception
	 *             the exception
	 */
	public List<OnScreenHelp> allOnScreenHelp() throws Exception {
		return dao.allOnScreenHelp();
	}

	/**
	 * Create or update OnScreenHelp.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see OnScreenHelp
	 */
	public void create(OnScreenHelp entity) throws Exception {
		if (entity.getId() == null) {
			this.dao.create(entity);
		} else
			this.dao.update(entity);

		OnScreenHelpService.instance().reloadMap();
	}

	/**
	 * Update OnScreenHelp.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see OnScreenHelp
	 */
	public void update(OnScreenHelp entity) throws Exception {
		this.dao.update(entity);
	}

	/**
	 * Delete OnScreenHelp.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see OnScreenHelp
	 */
	public void delete(OnScreenHelp entity) throws Exception {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @return a {@link haj.com.entity.OnScreenHelp}
	 * @throws Exception
	 *             the exception
	 * @see OnScreenHelp
	 */
	public OnScreenHelp findByKey(long id) throws Exception {
		return dao.findByKey(id);
	}

	/**
	 * Find OnScreenHelp by description.
	 *
	 * @author TechFinium
	 * @param desc
	 *            the desc
	 * @return a list of {@link haj.com.entity.OnScreenHelp}
	 * @throws Exception
	 *             the exception
	 * @see OnScreenHelp
	 */
	public List<OnScreenHelp> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}

	/**
	 * Lazy load OnScreenHelp
	 * 
	 * @param first
	 *            from key
	 * @param pageSize
	 *            no of rows to fetch
	 * @return a list of {@link haj.com.entity.OnScreenHelp}
	 * @throws Exception
	 *             the exception
	 */
	public List<OnScreenHelp> allOnScreenHelp(int first, int pageSize) throws Exception {
		return dao.allOnScreenHelp(Long.valueOf(first), pageSize);
	}

	/**
	 * Get count of OnScreenHelp for lazy load
	 * 
	 * @author TechFinium
	 * @return Number of rows in the OnScreenHelp
	 * @throws Exception
	 *             the exception
	 */
	public Long count() throws Exception {
		return dao.count(OnScreenHelp.class);
	}

	/**
	 * Lazy load OnScreenHelp with pagination, filter, sorting
	 * 
	 * @author TechFinium
	 * @param class1
	 *            OnScreenHelp class
	 * @param first
	 *            the first
	 * @param pageSize
	 *            the page size
	 * @param sortField
	 *            the sort field
	 * @param sortOrder
	 *            the sort order
	 * @param filters
	 *            the filters
	 * @return a list of {@link haj.com.entity.OnScreenHelp} containing data
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<OnScreenHelp> allOnScreenHelp(Class<OnScreenHelp> class1, int first, int pageSize, String sortField,
			SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return (List<OnScreenHelp>) dao.sortAndFilter(class1, first, pageSize, sortField, sortOrder, filters);
	}

	/**
	 * Get count of OnScreenHelp for lazy load with filters
	 * 
	 * @author TechFinium
	 * @param entity
	 *            OnScreenHelp class
	 * @param filters
	 *            the filters
	 * @return Number of rows in the OnScreenHelp entity
	 * @throws Exception
	 *             the exception
	 */
	public int count(Class<OnScreenHelp> entity, Map<String, Object> filters) throws Exception {
		return dao.count(entity, filters);
	}

	public void populateHelpTextTable() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					logger.info("populateHelpTextTable START");
					List<String> l = GenericUtility.allFilesRecursivelyWithExtension(HAJConstants.APP_PATH, "xhtml");
					List<OnScreenHelp> l2 = dao.allOnScreenHelp();
					List<IDataEntity> entityList = new ArrayList<IDataEntity>();
					for (String a : l) {
						a = a.replaceAll(".xhtml", ".jsf");
						boolean doesNotExist = true;
						for (OnScreenHelp onScreenHelp : l2) {
							if (a.trim().equals((onScreenHelp.getScreen() == null ? "" : onScreenHelp.getScreen()).trim())) {
								doesNotExist = false;
							}
						}
						if (doesNotExist) {
							OnScreenHelp os = new OnScreenHelp();
							os.setScreen(a.trim());
							os.setActive(Boolean.FALSE);
							entityList.add(os);
						}
					}
					logger.info("l:" + l.size() + " l2:" + l2.size() + " entityList:" + entityList.size());
					if (!entityList.isEmpty()) {
						logger.info("About to insert batch Help");
						dao.createBatch(entityList);
						logger.info("Done insert batch Help");
					}
					logger.info("populateHelpTextTable END");
				} catch (Exception e) {
					logger.fatal(e);
				}
			}
		}).start();
	}

}