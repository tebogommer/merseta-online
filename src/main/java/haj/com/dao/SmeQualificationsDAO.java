package haj.com.dao;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.bean.SmeQualReportBean;
import haj.com.entity.SmeQualifications;
import haj.com.entity.enums.ApprovalEnum;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

public class SmeQualificationsDAO extends AbstractDAO {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all SmeQualifications
	 * 
	 * @author TechFinium
	 * @see SmeQualifications
	 * @return a list of {@link haj.com.entity.SmeQualifications}
	 * @throws Exception
	 *             global exception
	 */
	@SuppressWarnings("unchecked")
	public List<SmeQualifications> allSmeQualifications() throws Exception {
		return (List<SmeQualifications>) super.getList("select o from SmeQualifications o");
	}

	/**
	 * Get all SmeQualifications between from and noRows
	 * 
	 * @author TechFinium
	 * @param from
	 *            the from
	 * @param noRows
	 *            the no rows
	 * @see SmeQualifications
	 * @return a list of {@link haj.com.entity.SmeQualifications}
	 * @throws Exception
	 *             global exception
	 */
	@SuppressWarnings("unchecked")
	public List<SmeQualifications> allSmeQualifications(Long from, int noRows) throws Exception {
		String hql = "select o from SmeQualifications o ";
		Map<String, Object> parameters = new Hashtable<String, Object>();

		return (List<SmeQualifications>) super.getList(hql, parameters, from.intValue(), noRows);
	}

	/**
	 * Find object by primary key
	 * 
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @see SmeQualifications
	 * @return a {@link haj.com.entity.SmeQualifications}
	 * @throws Exception
	 *             global exception
	 */
	public SmeQualifications findByKey(Long id) throws Exception {
		String hql = "select o from SmeQualifications o where o.id = :id ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("id", id);
		return (SmeQualifications) super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find SmeQualifications by description
	 * 
	 * @author TechFinium
	 * @param description
	 *            the description
	 * @see SmeQualifications
	 * @return a list of {@link haj.com.entity.SmeQualifications}
	 * @throws Exception
	 *             global exception
	 */
	@SuppressWarnings("unchecked")
	public List<SmeQualifications> findByName(String description) throws Exception {
		String hql = "select o from SmeQualifications o where o.description like  :description order by o.desc ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("description", "" + description.trim() + "%");
		return (List<SmeQualifications>) super.getList(hql, parameters);
	}

	/**
	 * Find SmeQualifications By SitesSme Id
	 * @param smeId
	 * @return List<SmeQualifications>
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<SmeQualifications> findBySme(Long smeId) throws Exception {
		String hql = "select o from SmeQualifications o where o.sitesSme.id = :smeId";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("smeId", smeId);
		return (List<SmeQualifications>) super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<SmeQualifications> findBySme(Long smeId, ApprovalEnum status) throws Exception {
		String hql = "select o from SmeQualifications o where o.sitesSme.id = :smeId and o.status = :status";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("smeId", smeId);
		parameters.put("status", status);
		return (List<SmeQualifications>) super.getList(hql, parameters);
	}
	
	/**
	 * Count SmeQualifications By SitesSme Id
	 * @param smeId
	 * @return Integer
	 * @throws Exception
	 */
	public Integer countBySme(Long smeId) throws Exception {
		String hql = "select count(o) from SmeQualifications o where o.sitesSme.id = :smeId";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("smeId", smeId);
		return ((Long) super.getUniqueResult(hql, parameters)).intValue();
	}
	
	/**
	 * Find SmeQualifications by Qualification Id
	 * @param qualificationId
	 * @return List<SmeQualifications>
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<SmeQualifications> findByQualification(Long qualificationId) throws Exception {
		String hql = "select o from SmeQualifications o where o.Qualification.id = :qualificationId";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("qualificationId", qualificationId);
		return (List<SmeQualifications>) super.getList(hql, parameters);
	}
	
	/**
	 * Count SmeQualifications by Qualification Id
	 * @param smeId
	 * @return Integer
	 * @throws Exception
	 */
	public Integer countByQualification(Long qualificationId) throws Exception {
		String hql = "select count(o) from SmeQualifications o where o.Qualification.id = :qualificationId";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("qualificationId", qualificationId);
		return ((Long) super.getUniqueResult(hql, parameters)).intValue();
	}
	
	/**
	 * Find SmeQualifications by Qualification Id And SiteSme Id
	 * @param qualificationId
	 * @param smeId
	 * @return List<SmeQualifications>
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<SmeQualifications> findByQualificationAndSme(Long qualificationId, Long smeId) throws Exception {
		String hql = "select o from SmeQualifications o where o.Qualification.id = :qualificationId and o.sitesSme.id = :smeId";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("qualificationId", qualificationId);
		parameters.put("smeId", smeId);
		return (List<SmeQualifications>) super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<SmeQualifications> findByQualificationAndCompany(Long qualificationId, Long companyId, ApprovalEnum status) throws Exception {
		String hql = "select o from SmeQualifications o where o.qualification.id = :qualificationId and o.sitesSme.company.id = :companyId and o.status = :status";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("qualificationId", qualificationId);
		parameters.put("companyId", companyId);
		parameters.put("status", status);
		return (List<SmeQualifications>) super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<SmeQualifications> findByCompany(Long companyId, ApprovalEnum status) throws Exception {
		String hql = "select o from SmeQualifications o where o.sitesSme.company.id = :companyId and o.status = :status";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("companyId", companyId);
		parameters.put("status", status);
		return (List<SmeQualifications>) super.getList(hql, parameters);
	}
	
	/**
	 * Count SmeQualifications by Qualification Id And SiteSme Id
	 * @param qualificationId
	 * @param smeId
	 * @return Integer
	 * @throws Exception
	 */
	public Integer countByQualificationAndSme(Long qualificationId, Long smeId) throws Exception {
		String hql = "select count(o) from SmeQualifications o where o.Qualification.id = :qualificationId and o.sitesSme.id = :smeId";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("qualificationId", qualificationId);
		parameters.put("smeId", smeId);
		return ((Long) super.getUniqueResult(hql, parameters)).intValue();
	}
	
	public List<SmeQualReportBean> populateSmeQualReport() throws Exception {
		String sql = "select    " + 
				"	c.company_name as companyName   " + 
				"	, c.trading_name as tradingName   " + 
				"	, c.levy_number as entityId   " + 
				"	, case   " + 
				"		when s.id is not null then s.company_name   " + 
				"		else c.company_name   " + 
				"	end as siteName   " + 
				"	, ss.first_name as firstName   " + 
				"	, ss.last_name as lastName   " + 
				"	, case   " + 
				"		when ss.identity_number is not null and ss.identity_number <> '' then ss.identity_number   " + 
				"		else ss.passport_number   " + 
				"	end as identityPassportNum   " + 
				"	, case   " + 
				"		when ss.status in ('0') then 'Approved'   " + 
				"		when ss.status in ('1') then 'Rejected'   " + 
				"		when ss.status in ('3') then 'Pending Approval'   " + 
				"		when ss.status in ('22') then 'Pending Approval'		   " + 
				"	end as approvalStatus   " + 
				"	, q.qualificationid_string as saqaId   " + 
				"	, q.qualificationtitle as qualDescr    " + 
				"	, nqf.description as nqfDesc   " + 
				"	, case   " + 
				"		when sq.status in ('0') then 'Approved'   " + 
				"		when sq.status in ('1') then 'Rejected'   " + 
				"		when sq.status in ('3') then 'Pending Approval'   " + 
				"		Else ''   " + 
				"	end as smeStatus   " + 
				"	, sq.approved_date as approvalDate   " + 
				"	, case   " + 
				"		when sq.status in ('1')   " + 
				"			then (   " + 
				"				select    " + 
				"					GROUP_CONCAT(description)   " + 
				"				from    " + 
				"					reject_reasons   " + 
				"				where    " + 
				"					id in (   " + 
				"						select   " + 
				"							reject_reason_id   " + 
				"						from    " + 
				"							reject_reason_multiple_selection   " + 
				"						where   " + 
				"							target_class = 'haj.com.entity.SmeQualifications'   " + 
				"							and target_key = sqp.id   " + 
				"					)   " + 
				"			)			   " + 
				"		else 'N/A'   " + 
				"	end as rejectionReasons   " + 
				"from   " + 
				"	sme_qualifications sq   " + 
				"inner join sites_sme ss on ss.id = sq.sites_sme_id   " + 
				"left join sites s on s.id = ss.sites_id    " + 
				"left join company c on c.id = ss.company_id   " + 
				"left join saqa_qualification q on q.id = sq.qualification_id   " + 
				"left join nqf_levels nqf on nqf.id = q.nqf_level_id   " + 
				"left join sme_qualifications sqp on sqp.id = sqp.sme_qualifications_parent_id";
		return (List<SmeQualReportBean>) super.nativeSelectSqlList(sql, SmeQualReportBean.class);
	}
}
