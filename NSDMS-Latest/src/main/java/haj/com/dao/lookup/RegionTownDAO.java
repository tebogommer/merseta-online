package haj.com.dao.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.lookup.RegionTown;
import haj.com.entity.lookup.Town;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

public class RegionTownDAO extends AbstractDAO {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all RegionTown
	 * 
	 * @author TechFinium
	 * @see RegionTown
	 * @return a list of {@link haj.com.entity.RegionTown}
	 * @throws Exception
	 *             global exception
	 */
	@SuppressWarnings("unchecked")
	public List<RegionTown> allRegionTown() throws Exception {
		return (List<RegionTown>) super.getList("select o from RegionTown o");
	}

	/**
	 * Get all RegionTown between from and noRows
	 * 
	 * @author TechFinium
	 * @param from
	 *            the from
	 * @param noRows
	 *            the no rows
	 * @see RegionTown
	 * @return a list of {@link haj.com.entity.RegionTown}
	 * @throws Exception
	 *             global exception
	 */
	@SuppressWarnings("unchecked")
	public List<RegionTown> allRegionTown(Long from, int noRows) throws Exception {
		String hql = "select o from RegionTown o ";
		Map<String, Object> parameters = new Hashtable<String, Object>();

		return (List<RegionTown>) super.getList(hql, parameters, from.intValue(), noRows);
	}

	/**
	 * Find object by primary key
	 * 
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @see RegionTown
	 * @return a {@link haj.com.entity.RegionTown}
	 * @throws Exception
	 *             global exception
	 */
	public RegionTown findByKey(Long id) throws Exception {
		String hql = "select o from RegionTown o where o.id = :id ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("id", id);
		return (RegionTown) super.getUniqueResult(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public RegionTown findByTown(Town town) throws Exception {
		String hql = "select o from RegionTown o where o.town.id = :townId";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("townId", town.getId());
		return (RegionTown) super.getUniqueResult(hql, parameters);
	}
	
	/**
	 * Does a check if the user has been allocated as crm or clo on the region town 
	 * @param hotingCompanyEmployeeId
	 * @param crmCheck
	 * @return long
	 * @throws Exception
	 */
	public long findByCrmCloCount(Long hotingCompanyEmployeeId, Boolean crmCheck) throws Exception {
		String hql = "select count(o) from RegionTown o where ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		if (crmCheck) {
			hql += "o.crm.id = :crmId";
			parameters.put("crmId", hotingCompanyEmployeeId);
		} else {
			hql += "o.clo.id = :cloId";
			parameters.put("cloId", hotingCompanyEmployeeId);
		}
		return (long) super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find RegionTown by description
	 * 
	 * @author TechFinium
	 * @param description
	 *            the description
	 * @see RegionTown
	 * @return a list of {@link haj.com.entity.RegionTown}
	 * @throws Exception
	 *             global exception
	 */
	@SuppressWarnings("unchecked")
	public List<RegionTown> findByName(String description) throws Exception {
		String hql = "select o from RegionTown o where o.description like  :description order by o.desc ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("description", "" + description.trim() + "%");
		return (List<RegionTown>) super.getList(hql, parameters);
	}
}
