package haj.com.dao;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.ExtractErrorList;
import haj.com.entity.LevyIncomeSummary;
import haj.com.entity.enums.ReportPropertiesEnum;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

// TODO: Auto-generated Javadoc
/**
 * The Class LevyIncomeSummaryDAO.
 */
public class LevyIncomeSummaryDAO extends AbstractDAO {

	/*
	 * (non-Javadoc)
	 * 
	 * @see haj.com.framework.AbstractDAO#getDataProvider()
	 */
	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all LevyIncomeSummary.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.LevyIncomeSummary}
	 * @throws Exception
	 *             global exception
	 * @see LevyIncomeSummary
	 */
	@SuppressWarnings("unchecked")
	public List<LevyIncomeSummary> allLevyIncomeSummary() throws Exception {
		return (List<LevyIncomeSummary>) super.getList("select o from LevyIncomeSummary o where description is not null");
	}
	
	@SuppressWarnings("unchecked")
	public List<LevyIncomeSummary> allLevyIncomeSummaryByType(ReportPropertiesEnum reportPropertiesEnum) throws Exception {
		String hql = "select o from LevyIncomeSummary o where o.reportProperty = :reportPropertiesEnum and o.description is not null ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("reportPropertiesEnum", reportPropertiesEnum);
		return (List<LevyIncomeSummary>) super.getList(hql, parameters);
	}
	/**
	 * Get all LevyIncomeSummary between from and noRows.
	 *
	 * @author TechFinium
	 * @param from
	 *            the from
	 * @param noRows
	 *            the no rows
	 * @return a list of {@link haj.com.entity.LevyIncomeSummary}
	 * @throws Exception
	 *             global exception
	 * @see LevyIncomeSummary
	 */
	@SuppressWarnings("unchecked")
	public List<LevyIncomeSummary> allLevyIncomeSummary(Long from, int noRows) throws Exception {
		String hql = "select o from LevyIncomeSummary o ";
		Map<String, Object> parameters = new Hashtable<String, Object>();

		return (List<LevyIncomeSummary>) super.getList(hql, parameters, from.intValue(), noRows);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @return a {@link haj.com.entity.LevyIncomeSummary}
	 * @throws Exception
	 *             global exception
	 * @see LevyIncomeSummary
	 */
	public LevyIncomeSummary findByKey(Long id) throws Exception {
		String hql = "select o from LevyIncomeSummary o where o.id = :id ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("id", id);
		return (LevyIncomeSummary) super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find LevyIncomeSummary by description.
	 *
	 * @author TechFinium
	 * @param description
	 *            the description
	 * @return a list of {@link haj.com.entity.LevyIncomeSummary}
	 * @throws Exception
	 *             global exception
	 * @see LevyIncomeSummary
	 */
	@SuppressWarnings("unchecked")
	public List<LevyIncomeSummary> findByName(String description) throws Exception {
		String hql = "select o from LevyIncomeSummary o where o.description like  :description order by o.desc ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("description", "" + description.trim() + "%");
		return (List<LevyIncomeSummary>) super.getList(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<ExtractErrorList> findExportErrors() throws Exception {
		String hql = "select o from ExtractErrorList o";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		return (List<ExtractErrorList>) super.getList(hql, parameters);
	}
}
