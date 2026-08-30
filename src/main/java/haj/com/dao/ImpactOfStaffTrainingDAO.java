package haj.com.dao;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.ImpactOfStaffTraining;
import haj.com.entity.Wsp;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

// TODO: Auto-generated Javadoc
/**
 * The Class ImpactOfStaffTrainingDAO.
 */
public class ImpactOfStaffTrainingDAO extends AbstractDAO {

	/* (non-Javadoc)
	 * @see haj.com.framework.AbstractDAO#getDataProvider()
	 */
	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all ImpactOfStaffTraining.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.ImpactOfStaffTraining}
	 * @throws Exception             global exception
	 * @see ImpactOfStaffTraining
	 */
	@SuppressWarnings("unchecked")
	public List<ImpactOfStaffTraining> allImpactOfStaffTraining() throws Exception {
		return (List<ImpactOfStaffTraining>) super.getList("select o from ImpactOfStaffTraining o");
	}

	/**
	 * Get all ImpactOfStaffTraining between from and noRows.
	 *
	 * @author TechFinium
	 * @param from            the from
	 * @param noRows            the no rows
	 * @return a list of {@link haj.com.entity.ImpactOfStaffTraining}
	 * @throws Exception             global exception
	 * @see ImpactOfStaffTraining
	 */
	@SuppressWarnings("unchecked")
	public List<ImpactOfStaffTraining> allImpactOfStaffTraining(Long from, int noRows) throws Exception {
		String hql = "select o from ImpactOfStaffTraining o ";
		Map<String, Object> parameters = new Hashtable<String, Object>();

		return (List<ImpactOfStaffTraining>) super.getList(hql, parameters, from.intValue(), noRows);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id            the id
	 * @return a {@link haj.com.entity.ImpactOfStaffTraining}
	 * @throws Exception             global exception
	 * @see ImpactOfStaffTraining
	 */
	public ImpactOfStaffTraining findByKey(Long id) throws Exception {
		String hql = "select o from ImpactOfStaffTraining o where o.id = :id ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("id", id);
		return (ImpactOfStaffTraining) super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find ImpactOfStaffTraining by description.
	 *
	 * @author TechFinium
	 * @param description            the description
	 * @return a list of {@link haj.com.entity.ImpactOfStaffTraining}
	 * @throws Exception             global exception
	 * @see ImpactOfStaffTraining
	 */
	@SuppressWarnings("unchecked")
	public List<ImpactOfStaffTraining> findByName(String description) throws Exception {
		String hql = "select o from ImpactOfStaffTraining o where o.description like  :description order by o.desc ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("description", "" + description.trim() + "%");
		return (List<ImpactOfStaffTraining>) super.getList(hql, parameters);
	}

	/**
	 * Find by WSP.
	 *
	 * @param wsp the wsp
	 * @return the impact of staff training
	 * @throws Exception the exception
	 */
	public ImpactOfStaffTraining findByWSP(Wsp wsp) throws Exception {
		String hql = "select o from ImpactOfStaffTraining o where o.wsp.id = :wspId";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("wspId", wsp.getId());
		return (ImpactOfStaffTraining) super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find by WSP count.
	 *
	 * @param wsp the wsp
	 * @return the long
	 * @throws Exception the exception
	 */
	public long findByWSPCount(Wsp wsp) throws Exception {
		String hql = "select count(o) from ImpactOfStaffTraining o where o.wsp.id = :wspId";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("wspId", wsp.getId());
		return (long) super.getUniqueResult(hql, parameters);
	}
}
