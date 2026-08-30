package haj.com.dao;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.bean.ExtensionRequestReportBean;
import haj.com.entity.ExtensionRequest;
import haj.com.entity.Users;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

public class ExtensionRequestDAO extends AbstractDAO {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all ExtensionRequest
	 * 
	 * @author TechFinium
	 * @see ExtensionRequest
	 * @return a list of {@link haj.com.entity.ExtensionRequest}
	 * @throws Exception
	 *             global exception
	 */
	@SuppressWarnings("unchecked")
	public List<ExtensionRequest> allExtensionRequest() throws Exception {
		return (List<ExtensionRequest>) super.getList("select o from ExtensionRequest o");
	}

	/**
	 * Get all ExtensionRequest between from and noRows
	 * 
	 * @author TechFinium
	 * @param from
	 *            the from
	 * @param noRows
	 *            the no rows
	 * @see ExtensionRequest
	 * @return a list of {@link haj.com.entity.ExtensionRequest}
	 * @throws Exception
	 *             global exception
	 */
	@SuppressWarnings("unchecked")
	public List<ExtensionRequest> allExtensionRequest(Long from, int noRows) throws Exception {
		String hql = "select o from ExtensionRequest o ";
		Map<String, Object> parameters = new Hashtable<String, Object>();

		return (List<ExtensionRequest>) super.getList(hql, parameters, from.intValue(), noRows);
	}
	
	@SuppressWarnings("unchecked")
	public List<ExtensionRequest> findByWSP(Long wspId) throws Exception {
		String hql = "select o from ExtensionRequest o where o.wsp.id = :wspId";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("wspId", wspId);
		return (List<ExtensionRequest>) super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<ExtensionRequest> extensionRequestGranyedCheck(Long wspId) throws Exception {
		String hql = "select o from ExtensionRequest o where o.wsp.id = :wspId order by o.createDate desc";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("wspId", wspId);
		return (List<ExtensionRequest>) super.getList(hql, parameters);
	}
	
	/**
	 * Get all ExtensionRequest
	 * 
	 * @author TechFinium
	 * @see ExtensionRequest
	 * @return a list of {@link haj.com.entity.ExtensionRequest}
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<ExtensionRequest> findByCompany(Long companyID) throws Exception {
		String hql = "select o from ExtensionRequest o left join fetch o.wsp w  left join fetch w.company wc  where wc.id = :companyID order by o.newSubmissionDate";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("companyID", companyID);
		return (List<ExtensionRequest>) super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<ExtensionRequest> findByCompanyNoWSP(Long companyID) throws Exception {
		String hql = "select o from ExtensionRequest o left join fetch o.wsp w  left join fetch o.company c where c.id = :companyID or w.company.id = :companyID order by o.newSubmissionDate";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("companyID", companyID);
		return (List<ExtensionRequest>) super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<ExtensionRequest> findByCompanyNoWSPAssigned(Long companyID) throws Exception {
		String hql = "select o from ExtensionRequest o  left join fetch o.company c where c.id = :companyID order by o.newSubmissionDate desc";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("companyID", companyID);
		return (List<ExtensionRequest>) super.getList(hql, parameters);
	}
	
	
	@SuppressWarnings("unchecked")
	public List<ExtensionRequest> findByCompanyNoWSPWithFinYear(Long companyID, Integer finYear) throws Exception {
		String hql = "select o from ExtensionRequest o left join fetch o.wsp w  left join fetch o.company c where (c.id = :companyID or w.company.id = :companyID) and w.finYear = :finYear order by o.newSubmissionDate";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("companyID", companyID);
		parameters.put("finYear", finYear);
		return (List<ExtensionRequest>) super.getList(hql, parameters);
	}

	/**
	 * Find object by primary key
	 * 
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @see ExtensionRequest
	 * @return a {@link haj.com.entity.ExtensionRequest}
	 * @throws Exception
	 *             global exception
	 */
	public ExtensionRequest findByKey(Long id) throws Exception {
		String hql = "select o from ExtensionRequest o left join fetch o.wsp w left join fetch w.company where o.id = :id ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("id", id);
		return (ExtensionRequest) super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find ExtensionRequest by description
	 * 
	 * @author TechFinium
	 * @param description
	 *            the description
	 * @see ExtensionRequest
	 * @return a list of {@link haj.com.entity.ExtensionRequest}
	 * @throws Exception
	 *             global exception
	 */
	@SuppressWarnings("unchecked")
	public List<ExtensionRequest> findByName(String description) throws Exception {
		String hql = "select o from ExtensionRequest o where o.description like  :description order by o.desc ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("description", "" + description.trim() + "%");
		return (List<ExtensionRequest>) super.getList(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<ExtensionRequest> allExtensionRequest(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Users user) throws Exception {
		String hql = "select o from ExtensionRequest o left join fetch o.user u left join fetch o.company c where o.user.id = :userID";
		filters.put("userID", user.getId());
		return (List<ExtensionRequest>) super.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql);
	}
	
	@SuppressWarnings("unchecked")
	public List<ExtensionRequest> allExtensionRequest(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		String hql = "select o from ExtensionRequest o left join fetch o.user u left join fetch o.company c left join fetch o.wsp w";
		return (List<ExtensionRequest>) super.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql);
	}
	
	public int countHql(Map<String, Object> filters, Users user) throws Exception {
		String hql = "select count(o) from ExtensionRequest o where o.user.id = :userID";
		filters.put("userID", user.getId());
		return countWhere(filters, hql);
	}
	
	@SuppressWarnings("unchecked")
	public List<ExtensionRequest> allExtensionRequestByWspFinYear(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Integer selectedYear) throws Exception {
		String hql = "select o from ExtensionRequest o where o.wsp.finYear = :selectedYear ";
		filters.put("selectedYear", selectedYear);
		return (List<ExtensionRequest>) super.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql);
	}
	
	public int countAllExtensionRequestByWspFinYear(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from ExtensionRequest o where o.wsp.finYear = :selectedYear";
		return countWhere(filters, hql);
	}
	
	@SuppressWarnings("unchecked")
	public List<ExtensionRequest> allExtensionRequestAwaitingWspAssignment(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		String hql = "select o from ExtensionRequest o where o.wsp is null";
		return (List<ExtensionRequest>) super.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql);
	}
	
	public int countAllExtensionRequestAwaitingWspAssignment(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from ExtensionRequest o where o.wsp is null";
		return countWhere(filters, hql);
	}
	
	public List<ExtensionRequestReportBean> populateExtensionRequestReport() throws Exception {
		String sql = "select   " + 
				"	er.create_date as createDate   " + 
				"	, case   " + 
				"		when c.id is not null then c.levy_number   " + 
				"		when erc.id is not null then erc.levy_number   " + 
				"		Else ''   " + 
				"	end as entityId   " + 
				"	, case   " + 
				"		when c.id is not null then c.company_name   " + 
				"		when erc.id is not null then erc.company_name   " + 
				"		Else ''   " + 
				"	end as companyName   " + 
				"	, case   " + 
				"		when w.id is not null then w.fin_year   " + 
				"		else null    " + 
				"	end as grantYear   " + 
				"	, case   " + 
				"		when u.id is not null then CONCAT(u.first_name, ' ', u.last_name)    " + 
				"		Else ''   " + 
				"	end as applicantDetails   " + 
				"	, case   " + 
				"		when er.status in (0) then 'Approved'   " + 
				"		when er.status in (3) then 'Pending Approval'   " + 
				"		Else ''   " + 
				"	end as statusAssigned   " + 
				"	, er.new_submission_date as newSubmissionDate   " + 
				"	, case    " + 
				"		when er.reason_for_extension is null then ''   " + 
				"		else er.reason_for_extension   " + 
				"	end as reasonForExtensionHtml   " + 
				"	   " + 
				"from extension_request er   " + 
				"   " + 
				"left join company erc on erc.id = er.company_id   " + 
				"left join wsp w on w.id = er.wsp_id   " + 
				"left join company c on c.id = w.company_id   " + 
				"left join users u on u.id = er.user_id";
		return (List<ExtensionRequestReportBean>) super.nativeSelectSqlList(sql, ExtensionRequestReportBean.class);
	}
	
}