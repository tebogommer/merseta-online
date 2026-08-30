package haj.com.dao.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;
import haj.com.entity.lookup.PreviousSchools;

public class PreviousSchoolsDAO extends AbstractDAO {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all PreviousSchools
	 * 
	 * @author TechFinium
	 * @see PreviousSchools
	 * @return a list of {@link haj.com.entity.PreviousSchools}
	 * @throws Exception global exception
	 */
	@SuppressWarnings("unchecked")
	public List<PreviousSchools> allPreviousSchools() throws Exception {
		return (List<PreviousSchools>) super.getList("select o from PreviousSchools o");
	}

	/**
	 * Get all PreviousSchools between from and noRows
	 * 
	 * @author TechFinium
	 * @param from   the from
	 * @param noRows the no rows
	 * @see PreviousSchools
	 * @return a list of {@link haj.com.entity.PreviousSchools}
	 * @throws Exception global exception
	 */
	@SuppressWarnings("unchecked")
	public List<PreviousSchools> allPreviousSchools(Long from, int noRows) throws Exception {
		String hql = "select o from PreviousSchools o ";
		Map<String, Object> parameters = new Hashtable<String, Object>();

		return (List<PreviousSchools>) super.getList(hql, parameters, from.intValue(), noRows);
	}

	/**
	 * Find object by primary key
	 * 
	 * @author TechFinium
	 * @param id the id
	 * @see PreviousSchools
	 * @return a {@link haj.com.entity.PreviousSchools}
	 * @throws Exception global exception
	 */
	public PreviousSchools findByKey(Long id) throws Exception {
		String hql = "select o from PreviousSchools o where o.id = :id ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("id", id);
		return (PreviousSchools) super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find PreviousSchools by description
	 * 
	 * @author TechFinium
	 * @param description the description
	 * @see PreviousSchools
	 * @return a list of {@link haj.com.entity.PreviousSchools}
	 * @throws Exception global exception
	 */
	@SuppressWarnings("unchecked")
	public List<PreviousSchools> findByName(String officialInstitutionName) throws Exception {
		String hql = "select o from PreviousSchools o where o.officialInstitutionName like :officialInstitutionName order by o.officialInstitutionName ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("officialInstitutionName", "" + officialInstitutionName.trim() + "%");
		return (List<PreviousSchools>) super.getList(hql, parameters, 10);
	}

	public Integer countAllResults() throws Exception {
		return ((Long) super.getUniqueResult("select count(o) from PreviousSchools o")).intValue();
	}
}
