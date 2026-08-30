package haj.com.dao;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.SummativeAssessmentReport;
import haj.com.entity.SummativeAssessmentReportUnitStandards;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

public class SummativeAssessmentReportDAO extends AbstractDAO {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all SummativeAssessmentReport
	 * 
	 * @author TechFinium
	 * @see SummativeAssessmentReport
	 * @return a list of {@link haj.com.entity.SummativeAssessmentReport}
	 * @throws Exception
	 *             global exception
	 */
	@SuppressWarnings("unchecked")
	public List<SummativeAssessmentReport> allSummativeAssessmentReport() throws Exception {
		return (List<SummativeAssessmentReport>) super.getList("select o from SummativeAssessmentReport o");
	}

	/**
	 * Get all SummativeAssessmentReport between from and noRows
	 * 
	 * @author TechFinium
	 * @param from
	 *            the from
	 * @param noRows
	 *            the no rows
	 * @see SummativeAssessmentReport
	 * @return a list of {@link haj.com.entity.SummativeAssessmentReport}
	 * @throws Exception
	 *             global exception
	 */
	@SuppressWarnings("unchecked")
	public List<SummativeAssessmentReport> allSummativeAssessmentReport(Long from, int noRows) throws Exception {
		String hql = "select o from SummativeAssessmentReport o ";
		Map<String, Object> parameters = new Hashtable<String, Object>();

		return (List<SummativeAssessmentReport>) super.getList(hql, parameters, from.intValue(), noRows);
	}

	/**
	 * Find object by primary key
	 * 
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @see SummativeAssessmentReport
	 * @return a {@link haj.com.entity.SummativeAssessmentReport}
	 * @throws Exception
	 *             global exception
	 */
	public SummativeAssessmentReport findByKey(Long id) throws Exception {
		String hql = "select o from SummativeAssessmentReport o where o.id = :id ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("id", id);
		return (SummativeAssessmentReport) super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find SummativeAssessmentReport by description
	 * 
	 * @author TechFinium
	 * @param description
	 *            the description
	 * @see SummativeAssessmentReport
	 * @return a list of {@link haj.com.entity.SummativeAssessmentReport}
	 * @throws Exception
	 *             global exception
	 */
	@SuppressWarnings("unchecked")
	public List<SummativeAssessmentReport> findByName(String description) throws Exception {
		String hql = "select o from SummativeAssessmentReport o where o.description like  :description order by o.desc ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("description", "" + description.trim() + "%");
		return (List<SummativeAssessmentReport>) super.getList(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<SummativeAssessmentReportUnitStandards> findUnitStandards(Long summativeAssessmentReportID) throws Exception {
		String hql = "select o from SummativeAssessmentReportUnitStandards o left join fetch o.unitStandards where o.summativeAssessmentReport.id = :summativeAssessmentReportID  order by o.unitStandards.title";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("summativeAssessmentReportID", summativeAssessmentReportID);
		return (List<SummativeAssessmentReportUnitStandards>) super.getList(hql, parameters);
	}

	public SummativeAssessmentReport findByTrainingProviderVerficationKey(Long id) throws Exception {
		String hql = "select o from SummativeAssessmentReport o where o.trainingProviderVerfication.id = :id ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("id", id);
		return (SummativeAssessmentReport) super.getUniqueResult(hql, parameters);
	}
}
