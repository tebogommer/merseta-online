package haj.com.dao.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;
import haj.com.entity.lookup.DGYear;
import haj.com.entity.lookup.DGYearLearningPrograms;
import haj.com.entity.lookup.InterventionType;

public class DGYearLearningProgramsDAO extends AbstractDAO {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all DGYearLearningPrograms
	 * 
	 * @author TechFinium
	 * @see DGYearLearningPrograms
	 * @return a list of {@link haj.com.entity.DGYearLearningPrograms}
	 * @throws Exception
	 *             global exception
	 */
	@SuppressWarnings("unchecked")
	public List<DGYearLearningPrograms> allDGYearLearningPrograms() throws Exception {
		return (List<DGYearLearningPrograms>) super.getList("select o from DGYearLearningPrograms o");
	}

	/**
	 * Get all DGYearLearningPrograms between from and noRows
	 * 
	 * @author TechFinium
	 * @param from
	 *            the from
	 * @param noRows
	 *            the no rows
	 * @see DGYearLearningPrograms
	 * @return a list of {@link haj.com.entity.DGYearLearningPrograms}
	 * @throws Exception
	 *             global exception
	 */
	@SuppressWarnings("unchecked")
	public List<DGYearLearningPrograms> allDGYearLearningPrograms(Long from, int noRows) throws Exception {
		String              hql        = "select o from DGYearLearningPrograms o ";
		Map<String, Object> parameters = new Hashtable<String, Object>();

		return (List<DGYearLearningPrograms>) super.getList(hql, parameters, from.intValue(), noRows);
	}

	/**
	 * Find object by primary key
	 * 
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @see DGYearLearningPrograms
	 * @return a {@link haj.com.entity.DGYearLearningPrograms}
	 * @throws Exception
	 *             global exception
	 */
	public DGYearLearningPrograms findByKey(Long id) throws Exception {
		String              hql        = "select o from DGYearLearningPrograms o where o.id = :id ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("id", id);
		return (DGYearLearningPrograms) super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find DGYearLearningPrograms by description
	 * 
	 * @author TechFinium
	 * @param description
	 *            the description
	 * @see DGYearLearningPrograms
	 * @return a list of {@link haj.com.entity.DGYearLearningPrograms}
	 * @throws Exception
	 *             global exception
	 */
	@SuppressWarnings("unchecked")
	public List<DGYearLearningPrograms> findByName(String description) throws Exception {
		String              hql        = "select o from DGYearLearningPrograms o where o.description like  :description order by o.desc ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("description", "" + description.trim() + "%");
		return (List<DGYearLearningPrograms>) super.getList(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<DGYearLearningPrograms> findByDgYear(DGYear dgYear) throws Exception {
		String              hql        = "select o from DGYearLearningPrograms o where o.dgYear.id = :dgYearID";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("dgYearID", dgYear.getId());
		return (List<DGYearLearningPrograms>) super.getList(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<InterventionType> findInterventionsByDgYear(DGYear dgYear) throws Exception {
		String              hql        = "select o.interventionType from DGYearLearningPrograms o where o.dgYear.id = :dgYearID";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("dgYearID", dgYear.getId());
		return (List<InterventionType>) super.getList(hql, parameters);
	}
}
