package haj.com.dao.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;
import haj.com.entity.enums.AllocationChangeTypeEnum;
import haj.com.entity.lookup.AllocationChange;

public class AllocationChangeDAO extends AbstractDAO {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all AllocationChange
	 * 
	 * @author TechFinium
	 * @see AllocationChange
	 * @return a list of {@link haj.com.entity.AllocationChange}
	 * @throws Exception
	 *             global exception
	 */
	@SuppressWarnings("unchecked")
	public List<AllocationChange> allAllocationChange() throws Exception {
		return (List<AllocationChange>) super.getList("select o from AllocationChange o");
	}

	@SuppressWarnings("unchecked")
	public List<AllocationChange> allAllocationChangeByType(AllocationChangeTypeEnum allocationChangeTypeEnum) throws Exception {
		String hql = "select o from AllocationChange o where o.allocationChangeTypeEnum = :allocationChangeTypeEnum";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("allocationChangeTypeEnum", allocationChangeTypeEnum);
		return (List<AllocationChange>) super.getList(hql, parameters);
	}

	/**
	 * Get all AllocationChange between from and noRows
	 * 
	 * @author TechFinium
	 * @param from
	 *            the from
	 * @param noRows
	 *            the no rows
	 * @see AllocationChange
	 * @return a list of {@link haj.com.entity.AllocationChange}
	 * @throws Exception
	 *             global exception
	 */
	@SuppressWarnings("unchecked")
	public List<AllocationChange> allAllocationChange(Long from, int noRows) throws Exception {
		String hql = "select o from AllocationChange o ";
		Map<String, Object> parameters = new Hashtable<String, Object>();

		return (List<AllocationChange>) super.getList(hql, parameters, from.intValue(), noRows);
	}

	/**
	 * Find object by primary key
	 * 
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @see AllocationChange
	 * @return a {@link haj.com.entity.AllocationChange}
	 * @throws Exception
	 *             global exception
	 */
	public AllocationChange findByKey(Long id) throws Exception {
		String hql = "select o from AllocationChange o where o.id = :id ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("id", id);
		return (AllocationChange) super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find AllocationChange by description
	 * 
	 * @author TechFinium
	 * @param description
	 *            the description
	 * @see AllocationChange
	 * @return a list of {@link haj.com.entity.AllocationChange}
	 * @throws Exception
	 *             global exception
	 */
	@SuppressWarnings("unchecked")
	public List<AllocationChange> findByName(String description) throws Exception {
		String hql = "select o from AllocationChange o where o.description like  :description order by o.desc ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("description", "" + description.trim() + "%");
		return (List<AllocationChange>) super.getList(hql, parameters);
	}
}
