package haj.com.service.lookup;

import java.util.List;

import haj.com.dao.lookup.HomeAffairsDAO;
import haj.com.entity.lookup.HomeAffairs;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.primefaces.model.SortOrder;

public class HomeAffairsService extends AbstractService {
	/** The dao. */
	private HomeAffairsDAO dao = new HomeAffairsDAO();

	/**
	 * Get all HomeAffairs
	 * 
	 * @author TechFinium
	 * @see HomeAffairs
	 * @return a list of {@link haj.com.entity.HomeAffairs}
	 * @throws Exception
	 *             the exception
	 */
	public List<HomeAffairs> allHomeAffairs() throws Exception {
		return dao.allHomeAffairs();
	}

	/**
	 * Create or update HomeAffairs.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see HomeAffairs
	 */
	public void create(HomeAffairs entity) throws Exception {
		if (entity.getId() == null) {
			this.dao.create(entity);
		} else this.dao.update(entity);
	}

	/**
	 * Update HomeAffairs.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see HomeAffairs
	 */
	public void update(HomeAffairs entity) throws Exception {
		this.dao.update(entity);
	}

	/**
	 * Delete HomeAffairs.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see HomeAffairs
	 */
	public void delete(HomeAffairs entity) throws Exception {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @return a {@link haj.com.entity.HomeAffairs}
	 * @throws Exception
	 *             the exception
	 * @see HomeAffairs
	 */
	public HomeAffairs findByKey(long id) throws Exception {
		return dao.findByKey(id);
	}

	/**
	 * Find HomeAffairs by description.
	 *
	 * @author TechFinium
	 * @param desc
	 *            the desc
	 * @return a list of {@link haj.com.entity.HomeAffairs}
	 * @throws Exception
	 *             the exception
	 * @see HomeAffairs
	 */
	public List<HomeAffairs> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}

	/**
	 * Lazy load HomeAffairs
	 * 
	 * @param first
	 *            from key
	 * @param pageSize
	 *            no of rows to fetch
	 * @return a list of {@link haj.com.entity.HomeAffairs}
	 * @throws Exception
	 *             the exception
	 */
	public List<HomeAffairs> allHomeAffairs(int first, int pageSize) throws Exception {
		return dao.allHomeAffairs(Long.valueOf(first), pageSize);
	}

	/**
	 * Get count of HomeAffairs for lazy load
	 * 
	 * @author TechFinium
	 * @return Number of rows in the HomeAffairs
	 * @throws Exception
	 *             the exception
	 */
	public Long count() throws Exception {
		return dao.count(HomeAffairs.class);
	}

	/**
	 * Lazy load HomeAffairs with pagination, filter, sorting
	 * 
	 * @author TechFinium
	 * @param class1
	 *            HomeAffairs class
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
	 * @return a list of {@link haj.com.entity.HomeAffairs} containing data
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<HomeAffairs> allHomeAffairs(Class<HomeAffairs> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return (List<HomeAffairs>) dao.sortAndFilter(class1, first, pageSize, sortField, sortOrder, filters);

	}

	/**
	 * Get count of HomeAffairs for lazy load with filters
	 * 
	 * @author TechFinium
	 * @param entity
	 *            HomeAffairs class
	 * @param filters
	 *            the filters
	 * @return Number of rows in the HomeAffairs entity
	 * @throws Exception
	 *             the exception
	 */
	public int count(Class<HomeAffairs> entity, Map<String, Object> filters) throws Exception {
		return dao.count(entity, filters);
	}

	@SuppressWarnings("unchecked")
	public void deleteUploadedEntries() throws Exception {
		List<HomeAffairs> allEntries = allHomeAffairs();
		dao.deleteBatch((List<IDataEntity>) (List<?>) allEntries, 1000);
	}

	@SuppressWarnings("unchecked")
	public void saveCsvUploadData(List<HomeAffairs> csvData) throws Exception {
		this.dao.createBatch((List<IDataEntity>) (List<?>) csvData);
	}

	public Integer countAllResults() throws Exception {
		return dao.countAllResults();
	}

	public Integer findByDhaIdNumber(String idNo) throws Exception {
		return dao.findByDhaIdNumber(idNo);
	}
	
	public Integer findByFullNamesAndIdNumber(String idNo, String fullName, String surname, String maidenName) throws Exception {
		return dao.findByFullNamesAndIdNumber(idNo, fullName, surname, maidenName);
	}

	public boolean findByDhaIdNumberMore(String idNo) throws Exception {
		return dao.findByDhaIdNumber(idNo) > 0;
	}
	
	public Integer findByDhaIdNumberAndDeathStatus(String idNo) throws Exception {
		return dao.findByDhaIdNumberAndDeathStatus(idNo);
	}
	
	// return true if dead
	public boolean idNoDeceasedCheck(String idNo) throws Exception{
		return dao.findByDhaIdNumberAndDeathStatus(idNo) > 0;
	}
	// return false if dead
	public boolean idNoDeceasedCheckTwo(String idNo) throws Exception{
		return dao.findByDhaIdNumberAndDeathStatus(idNo) < 0;
	}
	
	public Integer findByDhaIdNumberAndDeathStatusAlive(String idNo) throws Exception {
		return dao.findByDhaIdNumberAndDeathStatusAlive(idNo);
	}

	public boolean checkRsaId(String idVal) {
		if (idVal == null || (idVal != null && idVal.trim().length() == 0)) return true;
		if (!StringUtils.isNumeric(idVal)) return false;
		idVal = idVal.trim();
		if (idVal.length() < 13) return false;
		int checkDigit = ((Integer.valueOf("" + (idVal.charAt(idVal.length() - 1)))).intValue());
		String odd = "0";
		String even = "";
		int evenResult = 0;
		int result;
		for (int c = 1; c <= idVal.length(); c++) {
			if (c % 2 == 0) {
				even += idVal.charAt(c - 1);
			} else {
				if (c == idVal.length()) {
					continue;
				} else {
					odd = "" + (Integer.valueOf("" + odd).intValue() + Integer.valueOf("" + (idVal.charAt(c - 1))));
				}
			}
		}
		String evenS = "" + (Integer.valueOf(even) * 2);
		for (int r = 1; r <= evenS.length(); r++) {
			evenResult += Integer.valueOf("" + evenS.charAt(r - 1));
		}
		result = (Integer.valueOf(odd) + Integer.valueOf(evenResult));
		String resultS = "" + result;
		resultS = "" + (10 - (Integer.valueOf("" + (resultS.charAt(resultS.length() - 1)))).intValue());
		if (resultS.length() > 1) {
			resultS = "" + resultS.charAt(resultS.length() - 1);
		}
		if (Integer.valueOf(resultS) != checkDigit) {
			return false;
		} else {
			return true;
		}
	}
}
