package haj.com.service;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.bean.SSPListBean;
import haj.com.dao.WspSkillsRequirementsDAO;
import haj.com.entity.Wsp;
import haj.com.entity.WspSkillsRequirements;
import haj.com.entity.enums.InterventionTypeEnum;
import haj.com.framework.AbstractService;
import haj.com.utils.GenericUtility;

// TODO: Auto-generated Javadoc
/**
 * The Class WspSkillsRequirementsService.
 */
public class WspSkillsRequirementsService extends AbstractService {

	/** The dao. */
	private WspSkillsRequirementsDAO dao = new WspSkillsRequirementsDAO();

	/**
	 * All wsp skills requirements.
	 *
	 * @return the list
	 * @throws Exception
	 *             the exception
	 */
	public List<WspSkillsRequirements> allWspSkillsRequirements() throws Exception {
		return dao.allWspSkillsRequirements();
	}

	/**
	 * Creates the.
	 *
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 */
	public void create(WspSkillsRequirements entity) throws Exception {
		if (entity.getId() == null) {
			this.dao.create(entity);
		} else this.dao.update(entity);
	}

	/**
	 * Update.
	 *
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 */
	public void update(WspSkillsRequirements entity) throws Exception {
		this.dao.update(entity);
	}

	/**
	 * Delete.
	 *
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 */
	public void delete(WspSkillsRequirements entity) throws Exception {
		this.dao.delete(entity);
	}

	/**
	 * Find by key.
	 *
	 * @param id
	 *            the id
	 * @return the wsp skills requirements
	 * @throws Exception
	 *             the exception
	 */
	public WspSkillsRequirements findByKey(long id) throws Exception {
		return dao.findByKey(id);
	}

	/**
	 * Find by name.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 * @throws Exception
	 *             the exception
	 */
	public List<WspSkillsRequirements> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}

	/**
	 * All wsp skills requirements.
	 *
	 * @param first
	 *            the first
	 * @param pageSize
	 *            the page size
	 * @return the list
	 * @throws Exception
	 *             the exception
	 */
	public List<WspSkillsRequirements> allWspSkillsRequirements(int first, int pageSize) throws Exception {
		return dao.allWspSkillsRequirements(Long.valueOf(first), pageSize);
	}

	/**
	 * Count.
	 *
	 * @return the long
	 * @throws Exception
	 *             the exception
	 */
	public Long count() throws Exception {
		return dao.count(WspSkillsRequirements.class);
	}

	/**
	 * All wsp skills requirements.
	 *
	 * @param class1
	 *            the class 1
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
	 * @return the list
	 * @throws Exception
	 *             the exception
	 */
	
	public List<WspSkillsRequirements> findByYear(int finYear, InterventionTypeEnum interventionTypeEnum) throws Exception {
		return dao.findByYear(finYear, interventionTypeEnum);
	}
	
	public List<WspSkillsRequirements> findByYear(int finYear) throws Exception {
		return dao.findByYear(finYear);
	}
	
	public List<SSPListBean> findAllocationList(int finYear) throws Exception {
		return dao.findAllocationList(finYear);
	}

	/**
	 * Count.
	 *
	 * @param entity
	 *            the entity
	 * @param filters
	 *            the filters
	 * @return the int
	 * @throws Exception
	 *             the exception
	 */
	public int count(Class<WspSkillsRequirements> entity, Map<String, Object> filters) throws Exception {
		return dao.count(entity, filters);
	}

	public int findByYearCount(Map<String, Object> filters) throws Exception {
		return dao.findByYearCount(filters, GenericUtility.resolveFinYear(getSynchronizedDate()));
	}

	/**
	 * Find by wsp.
	 *
	 * @param wsp
	 *            the wsp
	 * @return the list
	 * @throws Exception
	 *             the exception
	 */
	public List<WspSkillsRequirements> findByWsp(Wsp wsp) throws Exception {
		return dao.findByWsp(wsp.getId());
	}

	/**
	 * Lazy load WspSkillsRequirements with pagination, filter, sorting.
	 *
	 * @param entity
	 *            the entity
	 * @param startingAt
	 *            the starting at
	 * @param pageSize
	 *            the page size
	 * @param sortField
	 *            the sort field
	 * @param sortOrder
	 *            the sort order
	 * @param filters
	 *            the filters
	 * @param hql
	 *            the hql
	 * @return the list
	 */
	@SuppressWarnings("unchecked")
	public List<WspSkillsRequirements> findByWsp(Class<WspSkillsRequirements> entity, int startingAt, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Long wspid) throws Exception {
		return (List<WspSkillsRequirements>) dao.findByWsp(entity, startingAt, pageSize, sortField, sortOrder, filters, wspid);
	}

	/**
	 * All WspSkillsRequirements from WSP count.
	 *
	 * @param filters
	 *            the filters
	 * @param Wsp
	 *            theWsp
	 * @return the long
	 * @throws Exception
	 *             the exception
	 */
	public long allWspSkillsRequirementsCount(Map<String, Object> filters, Wsp wsp) throws Exception {
		return dao.allWspSkillsRequirementsCount(filters, wsp);
	}

	/**
	 * Find by wsp count.
	 *
	 * @param wsp
	 *            the wsp
	 * @return the long
	 * @throws Exception
	 *             the exception
	 */
	public long findByWspCount(Wsp wsp) throws Exception {
		return dao.findByWspCount(wsp.getId());
	}
}
