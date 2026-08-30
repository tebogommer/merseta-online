package haj.com.dao.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;
import haj.com.entity.OfoCodes;
import haj.com.entity.lookup.AllocationList;

public class AllocationListDAO extends AbstractDAO {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all AllocationList
	 * 
	 * @author TechFinium
	 * @see AllocationList
	 * @return a list of {@link haj.com.entity.AllocationList}
	 * @throws Exception
	 *             global exception
	 */
	@SuppressWarnings("unchecked")
	public List<AllocationList> allAllocationList() throws Exception {
		return (List<AllocationList>) super.getList("select o from AllocationList o");
	}

	/**
	 * Get all AllocationList between from and noRows
	 * 
	 * @author TechFinium
	 * @param from
	 *            the from
	 * @param noRows
	 *            the no rows
	 * @see AllocationList
	 * @return a list of {@link haj.com.entity.AllocationList}
	 * @throws Exception
	 *             global exception
	 */
	@SuppressWarnings("unchecked")
	public List<AllocationList> allAllocationList(Long from, int noRows) throws Exception {
		String hql = "select o from AllocationList o ";
		Map<String, Object> parameters = new Hashtable<String, Object>();

		return (List<AllocationList>) super.getList(hql, parameters, from.intValue(), noRows);
	}

	/**
	 * Find object by primary key
	 * 
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @see AllocationList
	 * @return a {@link haj.com.entity.AllocationList}
	 * @throws Exception
	 *             global exception
	 */
	public AllocationList findByKey(Long id) throws Exception {
		String hql = "select o from AllocationList o where o.id = :id ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("id", id);
		return (AllocationList) super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find AllocationList by description
	 * 
	 * @author TechFinium
	 * @param description
	 *            the description
	 * @see AllocationList
	 * @return a list of {@link haj.com.entity.AllocationList}
	 * @throws Exception
	 *             global exception
	 */
	@SuppressWarnings("unchecked")
	public List<AllocationList> findByName(String description) throws Exception {
		String hql = "select o from AllocationList o where o.description like  :description order by o.desc ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("description", "" + description.trim() + "%");
		return (List<AllocationList>) super.getList(hql, parameters);
	}

	public Long findTotals(OfoCodes ofoCodes) throws Exception {
		String hql = "select (sum(o.maxPossibleLearners)+sum(o.coFundingLearnersAwarded)) from DgAllocation o where o.mandatoryGrant.ofoCodes.id = :ofoCodesID and o.awardAmount is not null";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("ofoCodesID", ofoCodes.getId());
		Long total = (Long) super.getUniqueResult(hql, parameters);
		if (total == null) {
			total = 0l;
		}
		return total;
	}
	
	public Long findTotalsByFinYear(OfoCodes ofoCodes, Integer finYear) throws Exception {
		String hql = "select (sum(o.maxPossibleLearners)+sum(o.coFundingLearnersAwarded)) from DgAllocation o where o.mandatoryGrant.ofoCodes.id = :ofoCodesID and o.mandatoryGrant.wsp.finYear = :finYear and o.awardAmount is not null";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("ofoCodesID", ofoCodes.getId());
		parameters.put("finYear", finYear);
		Long total = (Long) super.getUniqueResult(hql, parameters);
		if (total == null) {
			total = 0l;
		}
		return total;
	}
}
