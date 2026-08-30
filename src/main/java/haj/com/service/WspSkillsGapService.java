package haj.com.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.WspSkillsGapDAO;
import haj.com.entity.Wsp;
import haj.com.entity.WspSkillsGap;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;
import haj.com.report.bean.MainSkillsGapReport;

public class WspSkillsGapService extends AbstractService {
	/** The dao. */
	private WspSkillsGapDAO dao = new WspSkillsGapDAO();

	/**
	 * Get all WspSkillsGap
	 * 
	 * @author TechFinium
	 * @see WspSkillsGap
	 * @return a list of {@link haj.com.entity.WspSkillsGap}
	 * @throws Exception
	 *             the exception
	 */
	public List<WspSkillsGap> allWspSkillsGap() throws Exception {
		return dao.allWspSkillsGap();
	}

	/**
	 * Create or update WspSkillsGap.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see WspSkillsGap
	 */
	public void create(WspSkillsGap entity) throws Exception {
		if (entity.getId() == null) {
			this.dao.create(entity);
		} else
			this.dao.update(entity);
	}

	/**
	 * Batch update for WspSkillsGap
	 * 
	 * @param entitylist
	 * @throws Exception
	 */
	public void updateList(List<WspSkillsGap> entitylist) throws Exception {
		List<IDataEntity> dataList = new ArrayList<IDataEntity>();
		dataList.addAll(entitylist);
		dao.updateBatch(dataList);
	}

	/**
	 * Update WspSkillsGap.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see WspSkillsGap
	 */
	public void update(WspSkillsGap entity) throws Exception {
		this.dao.update(entity);
	}

	/**
	 * Delete WspSkillsGap.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see WspSkillsGap
	 */
	public void delete(WspSkillsGap entity) throws Exception {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @return a {@link haj.com.entity.WspSkillsGap}
	 * @throws Exception
	 *             the exception
	 * @see WspSkillsGap
	 */
	public WspSkillsGap findByKey(long id) throws Exception {
		return dao.findByKey(id);
	}

	/**
	 * Find WspSkillsGap by description.
	 *
	 * @author TechFinium
	 * @param desc
	 *            the desc
	 * @return a list of {@link haj.com.entity.WspSkillsGap}
	 * @throws Exception
	 *             the exception
	 * @see WspSkillsGap
	 */
	public List<WspSkillsGap> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}

	/**
	 * Lazy load WspSkillsGap
	 * 
	 * @param first
	 *            from key
	 * @param pageSize
	 *            no of rows to fetch
	 * @return a list of {@link haj.com.entity.WspSkillsGap}
	 * @throws Exception
	 *             the exception
	 */
	public List<WspSkillsGap> allWspSkillsGap(int first, int pageSize) throws Exception {
		return dao.allWspSkillsGap(Long.valueOf(first), pageSize);
	}

	/**
	 * Get count of WspSkillsGap for lazy load
	 * 
	 * @author TechFinium
	 * @return Number of rows in the WspSkillsGap
	 * @throws Exception
	 *             the exception
	 */
	public Long count() throws Exception {
		return dao.count(WspSkillsGap.class);
	}

	/**
	 * Lazy load WspSkillsGap with pagination, filter, sorting
	 * 
	 * @author TechFinium
	 * @param class1
	 *            WspSkillsGap class
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
	 * @return a list of {@link haj.com.entity.WspSkillsGap} containing data
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<WspSkillsGap> allWspSkillsGap(Class<WspSkillsGap> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return (List<WspSkillsGap>) dao.sortAndFilter(class1, first, pageSize, sortField, sortOrder, filters);

	}

	/**
	 * Get count of WspSkillsGap for lazy load with filters
	 * 
	 * @author TechFinium
	 * @param entity
	 *            WspSkillsGap class
	 * @param filters
	 *            the filters
	 * @return Number of rows in the WspSkillsGap entity
	 * @throws Exception
	 *             the exception
	 */
	public int count(Class<WspSkillsGap> entity, Map<String, Object> filters) throws Exception {
		return dao.count(entity, filters);
	}

	/**
	 * locates all WspSkillsGap where WSP is null
	 * 
	 * @return the list
	 * @throws Exception
	 */
	public List<WspSkillsGap> findByWspNull() throws Exception {
		return dao.findByWspNull();
	}

	/**
	 * Locates List of WspSkillsGap by WSP
	 * 
	 * @param wsp
	 * @return the list
	 * @throws Exception
	 */
	public List<WspSkillsGap> findByWsp(Wsp wsp) throws Exception {
		return dao.findByWsp(wsp.getId());
	}

	/**
	 * Locates List of WspSkillsGap by WSP and by section number. Only currently
	 * section 2 and 3.
	 * 
	 * @param wsp
	 * @param sectionNumber
	 * @return the list
	 * @throws Exception
	 */
	public List<WspSkillsGap> findByWspAndSection(Wsp wsp, Integer sectionNumber) throws Exception {
		return dao.findByWspAndSection(wsp.getId(), sectionNumber);
	}

	public List<MainSkillsGapReport> findTotalAndSection(Integer sectionNumber) throws Exception {
		return dao.findTotalAndSection(sectionNumber);
	}
}
