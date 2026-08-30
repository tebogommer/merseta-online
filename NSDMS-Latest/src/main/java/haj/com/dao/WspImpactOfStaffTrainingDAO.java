package haj.com.dao;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.WspImpactOfStaffTraining;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

public class WspImpactOfStaffTrainingDAO extends AbstractDAO {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all WspImpactOfStaffTraining
	 * 
	 * @author TechFinium
	 * @see WspImpactOfStaffTraining
	 * @return a list of {@link haj.com.entity.WspImpactOfStaffTraining}
	 * @throws Exception
	 *             global exception
	 */
	@SuppressWarnings("unchecked")
	public List<WspImpactOfStaffTraining> allWspImpactOfStaffTraining() throws Exception {
		return (List<WspImpactOfStaffTraining>) super.getList("select o from WspImpactOfStaffTraining o");
	}

	/**
	 * Get all WspImpactOfStaffTraining between from and noRows
	 * 
	 * @author TechFinium
	 * @param from
	 *            the from
	 * @param noRows
	 *            the no rows
	 * @see WspImpactOfStaffTraining
	 * @return a list of {@link haj.com.entity.WspImpactOfStaffTraining}
	 * @throws Exception
	 *             global exception
	 */
	@SuppressWarnings("unchecked")
	public List<WspImpactOfStaffTraining> allWspImpactOfStaffTraining(Long from, int noRows) throws Exception {
		String hql = "select o from WspImpactOfStaffTraining o ";
		Map<String, Object> parameters = new Hashtable<String, Object>();

		return (List<WspImpactOfStaffTraining>) super.getList(hql, parameters, from.intValue(), noRows);
	}

	/**
	 * Find object by primary key
	 * 
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @see WspImpactOfStaffTraining
	 * @return a {@link haj.com.entity.WspImpactOfStaffTraining}
	 * @throws Exception
	 *             global exception
	 */
	public WspImpactOfStaffTraining findByKey(Long id) throws Exception {
		String hql = "select o from WspImpactOfStaffTraining o where o.id = :id ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("id", id);
		return (WspImpactOfStaffTraining) super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find WspImpactOfStaffTraining by description
	 * 
	 * @author TechFinium
	 * @param description
	 *            the description
	 * @see WspImpactOfStaffTraining
	 * @return a list of {@link haj.com.entity.WspImpactOfStaffTraining}
	 * @throws Exception
	 *             global exception
	 */
	@SuppressWarnings("unchecked")
	public List<WspImpactOfStaffTraining> findByName(String description) throws Exception {
		String hql = "select o from WspImpactOfStaffTraining o where o.description like  :description order by o.desc ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("description", "" + description.trim() + "%");
		return (List<WspImpactOfStaffTraining>) super.getList(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<WspImpactOfStaffTraining> findByWspNull() throws Exception {
		return (List<WspImpactOfStaffTraining>) super.getList("select o from WspImpactOfStaffTraining o where o.wsp is null");
	}
	@SuppressWarnings("unchecked")
	public List<WspImpactOfStaffTraining> findByWsp(Long wspID) throws Exception {
		String hql = "select o from WspImpactOfStaffTraining o where o.wsp.id = :wspID";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("wspID", wspID);
		return (List<WspImpactOfStaffTraining>) super.getList(hql, parameters);
	}
}
