package haj.com.dao;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;
import haj.com.entity.Company;
import haj.com.entity.Sites;
import haj.com.entity.SitesSme;
import haj.com.entity.enums.ApprovalEnum;

public class SitesSmeDAO extends AbstractDAO {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all SitesSme
	 * 
	 * @author TechFinium
	 * @see SitesSme
	 * @return a list of {@link haj.com.entity.SitesSme}
	 * @throws Exception
	 *             global exception
	 */
	@SuppressWarnings("unchecked")
	public List<SitesSme> allSitesSme() throws Exception {
		return (List<SitesSme>) super.getList("select o from SitesSme o");
	}

	/**
	 * Get all SitesSme between from and noRows
	 * 
	 * @author TechFinium
	 * @param from
	 *            the from
	 * @param noRows
	 *            the no rows
	 * @see SitesSme
	 * @return a list of {@link haj.com.entity.SitesSme}
	 * @throws Exception
	 *             global exception
	 */
	@SuppressWarnings("unchecked")
	public List<SitesSme> allSitesSme(Long from, int noRows) throws Exception {
		String hql = "select o from SitesSme o ";
		Map<String, Object> parameters = new Hashtable<String, Object>();

		return (List<SitesSme>) super.getList(hql, parameters, from.intValue(), noRows);
	}

	/**
	 * Find object by primary key
	 * 
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @see SitesSme
	 * @return a {@link haj.com.entity.SitesSme}
	 * @throws Exception
	 *             global exception
	 */
	public SitesSme findByKey(Long id) throws Exception {
		String hql = "select o from SitesSme o where o.id = :id ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("id", id);
		return (SitesSme) super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find SitesSme by description
	 * 
	 * @author TechFinium
	 * @param description
	 *            the description
	 * @see SitesSme
	 * @return a list of {@link haj.com.entity.SitesSme}
	 * @throws Exception
	 *             global exception
	 */
	@SuppressWarnings("unchecked")
	public List<SitesSme> findByName(String description) throws Exception {
		String hql = "select o from SitesSme o where o.description like  :description order by o.desc ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("description", "" + description.trim() + "%");
		return (List<SitesSme>) super.getList(hql, parameters);
	}
	
	
	/**
	 * Find object by targetClass and targetKey.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.SitesSme}
	 * @throws Exception global exception
	 * @see    SitesSme
	 */
	@SuppressWarnings("unchecked")
	public List<SitesSme> findByTargetClassTargetKey(String targetClass , Long targetKey) throws Exception {
	 	String hql = "select o from SitesSme o where o.targetClass = :targetClass and o.targetKey = :targetKey";
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("targetClass", targetClass);
	    parameters.put("targetKey",targetKey);
		return (List<SitesSme>)super.getList(hql, parameters);
	}


	/**
	 * counts results by rsa if or passport
	 * 
	 * @param rsaPassport
	 * @param rsaIdCheck
	 * @param companyId
	 * @return Integer
	 * @throws Exception
	 */
	public Integer countByRsaIdOrPassort(Long companyId, String rsaPassport, boolean rsaIdCheck) throws Exception {
		String hql = "select count(o) from SitesSme o where o.company.id = :companyId ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		if (rsaIdCheck) {
			hql += "and o.identityNumber =:rsaPassport";
		} else {
			hql += "and o.passportNumber =:rsaPassport";
		}
		parameters.put("companyId", companyId);
		parameters.put("rsaPassport", rsaPassport.trim());
		return ((Long) super.getUniqueResult(hql, parameters)).intValue();
	}
	
	public Integer countByRsaIdOrPassortNotDeactivated(Long companyId, String rsaPassport, boolean rsaIdCheck, ApprovalEnum status) throws Exception {
		String hql = "select count(o) from SitesSme o where o.company.id = :companyId and o.status <> :status ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		if (rsaIdCheck) {
			hql += "and o.identityNumber =:rsaPassport";
		} else {
			hql += "and o.passportNumber =:rsaPassport";
		}
		parameters.put("companyId", companyId);
		parameters.put("status", status);
		parameters.put("rsaPassport", rsaPassport.trim());
		return ((Long) super.getUniqueResult(hql, parameters)).intValue();
	}

	@SuppressWarnings("unchecked")
	public List<SitesSme> allSitesSmeByCompany(Company company) throws Exception {
		String hql = "select o from SitesSme o where o.company.id = :companyId";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("companyId", company.getId());
		return (List<SitesSme>) super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<SitesSme> allSitesSmeByCompanyAndStatus(Company entity, ApprovalEnum statusValue, boolean active){
		String hql = "select o from SitesSme o where o.company.id = :companyId and o.status = :statusValue and o.active = :active";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("companyId", entity.getId());
		parameters.put("statusValue", statusValue);
		parameters.put("active", active);
		return (List<SitesSme>) super.getList(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<SitesSme> findApprovedSitesSme(Sites site, Company company) {
		String hql = "select o from SitesSme o where o.company.id = :companyId and o.status = :statusValue and o.active = :activeValue ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		if (site != null) {
			hql += "and o.sites.id = :siteId ";
			//filters.put("activeValue", true);
			parameters.put("siteId", company.getId());
		}
		hql += "and o.id not in (select x.sitesSme.id from WorkPlaceApprovalSites x where x.workPlaceApproval.id = :workplaceApprovalId)";
		
		parameters.put("companyId", company.getId());
		parameters.put("statusValue", ApprovalEnum.Approved);
		parameters.put("activeValue", true);
		parameters.put("workplaceApprovalId", company.getId());
		return (List<SitesSme>) super.getList(hql, parameters);
	}
	
	public SitesSme findByRsaIdOrPassort(String rsaPassport, boolean rsaIdCheck) throws Exception {
		String hql = "select o from SitesSme o where ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		if (rsaIdCheck) {
			hql += "o.identityNumber =:rsaPassport";
		} else {
			hql += "o.passportNumber =:rsaPassport";
		}
		
		parameters.put("rsaPassport", rsaPassport.trim());
		return (SitesSme) super.getUniqueResult(hql, parameters);
	}
	
	// new method added for resolution of mentor issue REF:: 1660
	public SitesSme findByRsaIdOrPassortNotDeactivated(Long companyId, String rsaPassport, boolean rsaIdCheck, ApprovalEnum status) throws Exception {
		String hql = "select o from SitesSme o where o.company.id = :companyId and o.status <> :status ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		if (rsaIdCheck) {
			hql += "and o.identityNumber =:rsaPassport";
		} else {
			hql += "and o.passportNumber =:rsaPassport";
		}
		parameters.put("companyId", companyId);
		parameters.put("status", status);
		parameters.put("rsaPassport", rsaPassport.trim());
		return (SitesSme) super.getUniqueResult(hql, parameters);
	}
}
