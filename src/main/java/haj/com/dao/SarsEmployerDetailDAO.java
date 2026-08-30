package haj.com.dao;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.primefaces.model.SortOrder;

import haj.com.bean.SarsEmployerCompanyBean;
import haj.com.entity.enums.CompanyStatusEnum;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;
import haj.com.sars.SarsEmployerDetail;

// TODO: Auto-generated Javadoc
/**
 * The Class SarsEmployerDetailDAO.
 */
public class SarsEmployerDetailDAO extends AbstractDAO  {

	/* (non-Javadoc)
	 * @see haj.com.framework.AbstractDAO#getDataProvider()
	 */
	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all SarsEmployerDetail.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.sars.SarsEmployerDetail}
	 * @throws Exception global exception
	 * @see    SarsEmployerDetail
	 */
	@SuppressWarnings("unchecked")
	public List<SarsEmployerDetail> allSarsEmployerDetail() throws Exception {
		return (List<SarsEmployerDetail>)super.getList("select o from SarsEmployerDetail o");
	}

	/**
	 * Get all SarsEmployerDetail between from and noRows.
	 *
	 * @author TechFinium
	 * @param from the from
	 * @param noRows the no rows
	 * @return a list of {@link haj.com.sars.SarsEmployerDetail}
	 * @throws Exception global exception
	 * @see    SarsEmployerDetail
	 */
	@SuppressWarnings("unchecked")
	public List<SarsEmployerDetail> allSarsEmployerDetail(Long from, int noRows) throws Exception {
	 	String hql = "select o from SarsEmployerDetail o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<SarsEmployerDetail>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.sars.SarsEmployerDetail}
	 * @throws Exception global exception
	 * @see    SarsEmployerDetail
	 */
	public SarsEmployerDetail findByKey(Long id) throws Exception {
	 	String hql = "select o from SarsEmployerDetail o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (SarsEmployerDetail)super.getUniqueResult(hql, parameters);
	}
	
	/**
	 * Find by ref no.
	 *
	 * @param refNo the ref no
	 * @param id the id
	 * @return the sars employer detail
	 * @throws Exception the exception
	 */
	public SarsEmployerDetail findByRefNo(String refNo, Long id) throws Exception {
	 	String hql = "select o from SarsEmployerDetail o where o.refNo = :refNo and o.sarsFiles.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("refNo", refNo);
	    parameters.put("id", id);
		return (SarsEmployerDetail)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find SarsEmployerDetail by description.
	 *
	 * @author TechFinium
	 * @param description the description
	 * @return a list of {@link haj.com.sars.SarsEmployerDetail}
	 * @throws Exception global exception
	 * @see    SarsEmployerDetail
	 */
	@SuppressWarnings("unchecked")
	public List<SarsEmployerDetail> findByName(String description) throws Exception {
	 	String hql = "select o from SarsEmployerDetail o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<SarsEmployerDetail>)super.getList(hql, parameters);
	}
	
	/**
	 * Find SDL.
	 *
	 * @param refNo the ref no
	 * @return the sars employer detail
	 * @throws Exception the exception
	 */
	@SuppressWarnings("unchecked")
	public SarsEmployerDetail findSDL(String refNo) throws Exception {
	 	String hql = "select o from SarsEmployerDetail o where o.refNo = :refNo order by o.sarsFiles.forMonth desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("refNo", refNo);
	    List<SarsEmployerDetail> l = (List<SarsEmployerDetail>)super.getList(hql, parameters,1);
	    if (l.size()==0) return null;
	    else return l.get(0);
	}
	
	/**
	 * Sort and filter.
	 *
	 * @param entity the entity
	 * @param startingAt the starting at
	 * @param pageSize the page size
	 * @param sortField the sort field
	 * @param sortOrder the sort order
	 * @param filters the filters
	 * @param sarsFileId the sars file id
	 * @return the list
	 */
	public List<?> sortAndFilter(Class<?> entity, int startingAt, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters,Long sarsFileId) {
		String hql = "select o from " + entity.getName() + " o left join fetch o.sarsFiles sf where o.sarsFiles.id = :sarsFileId ";
		Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("sarsFileId", sarsFileId);
		if (filters != null) {
			boolean ft = true;
			
			String hvar = null;
			for (Entry<String, Object> entry : filters.entrySet()) {
				hvar = entry.getKey();
				if (hvar.contains(".")) {
					hvar = hvar.replaceAll("\\.", "");
					parms.put(hvar, entry.getValue());
				} else {
					parms.put(entry.getKey(), entry.getValue());
				}
				if (ft) {
					hql += " and o." + entry.getKey() + " like " + " :" + hvar;
					ft = false;
				} else {
					hql += " and o." + entry.getKey() + " like " + " :" + hvar;
				}
			}
			filters = parms;
		}

		if (sortField != null) {

			switch (sortOrder) {
			case ASCENDING:
				hql += " order by o." + sortField + " asc ";
				break;
			case DESCENDING:
				hql += " order by o." + sortField + " desc ";
				break;
			default:
				break;
			}
		}
		return getList(hql, filters, startingAt, pageSize);
	}
	
	
	/**
	 * Count.
	 *
	 * @param entity the entity
	 * @param filters the filters
	 * @param sarsFileId the sars file id
	 * @return the int
	 */
	public int count(Class<?> entity, Map<String, Object> filters,Long sarsFileId) {
		String hql = "select count(o) from " + entity.getName() + " o where o.sarsFiles.id = :sarsFileId ";
		Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("sarsFileId", sarsFileId);
		if (filters != null) {
			boolean ft = true;			
			String hvar = null;
			for (Entry<String, Object> entry : filters.entrySet()) {
				hvar = entry.getKey();
				if (hvar.contains(".")) {
					hvar = hvar.replaceAll("\\.", "");
					parms.put(hvar, entry.getValue());
				} else {
					parms.put(entry.getKey(), entry.getValue());
				}
				if (ft) {
					hql += " and o." + entry.getKey() + " like " + " :" + hvar;
					ft = false;
				} else {
					hql += " and o." + entry.getKey() + " like " + " :" + hvar;
				}
			}
			filters = parms;
		}
		return ((Long) getUniqueResult(hql, filters)).intValue();
	}
	

	
	/*
select b
from SarsEmployerDetail a  
inner join Company b  
on a.refNo  = b.levyNumber 
where a.sarsFiles.id = 182
and a.tradingStatus <> b.sarsTradingStatus
	 */
	
	@SuppressWarnings("unchecked")
	public List<SarsEmployerDetail> populateFromSars(Long fileId) throws Exception {
	 	String hql = "select o from SarsEmployerDetail o " + 
	 			"where o.sarsFiles.id = :fileId " +
	 			"and o.tradingStatus in ('A','S') " +
	 	     	"and o.refNo  not in  " +
	 			"	(select b.levyNumber from Company b where b.levyNumber is not null)" ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("fileId", fileId);
		return (List<SarsEmployerDetail>)super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<SarsEmployerCompanyBean> compareToSars(Long fileId) throws Exception {
	 	String hql = "select new haj.com.bean.SarsEmployerCompanyBean(o, b) from Company o , SarsEmployerDetail b " + 
	 			"where o.levyNumber = b.refNo " + 
	 			"and b.tradingStatus in ('A','S') " +
	 			"and b.sarsFiles.id = :fileId " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("fileId", fileId);
		return (List<SarsEmployerCompanyBean>)super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<SarsEmployerCompanyBean> sarsStatusChanged(Long fileId) throws Exception {
	 	String hql = "select new haj.com.bean.SarsEmployerCompanyBean(b, a) " + 
	 			"from SarsEmployerDetail a " + 
	 			"inner join Company b " + 
	 			"on a.refNo  = b.levyNumber " + 
	 			"where a.sarsFiles.id = :fileId " + 
	 			"and a.tradingStatus <> b.sarsTradingStatus " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("fileId", fileId);
		return (List<SarsEmployerCompanyBean>)super.getList(hql, parameters);
	}
	
	
	@SuppressWarnings("unchecked")
	public List<SarsEmployerCompanyBean> nonMatchingSicCodes() throws Exception {
	 	String hql = "select new haj.com.bean.SarsEmployerCompanyBean(a, b, s) " + 
	 			" 			from Company a  " + 
	 			"	 			inner join SarsEmployerDetail b  " + 
	 			"	 			on a.levyNumber = b.refNo  " + 
	 			"	 			left join SICCode s  " + 
	 			"	 			on s.code = b.sicCode2  " + 
	 			"	 			where b.sarsFiles.id = (select  x.id from SarsFiles x where  x.forMonth = (select max(y.forMonth) from SarsFiles y)  )  " + 
	 			"	 			and s.id <> a.sicCode.id " + 
	 			"	 			and a.companyStatus = :companyStatus " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("companyStatus", CompanyStatusEnum.Active);
		return (List<SarsEmployerCompanyBean>)super.getList(hql, parameters);
	}
	
	/*
	 select b.id, b.levyNumber ,o.tradingStatus
from Company b 
inner join SarsEmployerDetail o
	on b.levyNumber =  o.refNo
	and o.sarsFiles.id = 183
where b.levyNumber is not null	
and o.tradingStatus <> 'A'  
	 */
	
	@SuppressWarnings("unchecked")
	public List<SarsEmployerDetail> findByRefNumberReturnOneResult(String refNumber) throws Exception {
	 	String hql = "select o from SarsEmployerDetail o where o.refNo =  :refNumber order by o.createDate desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("refNumber", refNumber.trim());
		return (List<SarsEmployerDetail>)super.getList(hql, parameters, 1);
	}
	
	public Integer countByRefNumber(String refNumber) throws Exception {
	 	String hql = "select count(o) from SarsEmployerDetail o where o.refNo =  :refNumber " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("refNumber", refNumber.trim());
		return ((Long)super.getUniqueResult(hql, parameters)).intValue();
	}
}

