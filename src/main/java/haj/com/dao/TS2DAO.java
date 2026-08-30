package haj.com.dao;

import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.bean.ReconSchemeYears;
import haj.com.bean.SarsLevyRecon;
import haj.com.entity.Tempx;
import haj.com.entity.datatakeon.TS2;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

// TODO: Auto-generated Javadoc
/**
 * The Class TS2DAO.
 */
public class TS2DAO extends AbstractDAO  {

	/* (non-Javadoc)
	 * @see haj.com.framework.AbstractDAO#getDataProvider()
	 */
	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all TS2.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.datatakeon.TS2}
	 * @throws Exception global exception
	 * @see    TS2
	 */
	@SuppressWarnings("unchecked")
	public List<TS2> allTS2() throws Exception {
		return (List<TS2>)super.getList("select o from TS2 o where o.postingDate is not null");
	}

	/**
	 * Get all TS2 between from and noRows.
	 *
	 * @author TechFinium
	 * @param from the from
	 * @param noRows the no rows
	 * @return a list of {@link haj.com.entity.datatakeon.TS2}
	 * @throws Exception global exception
	 * @see    TS2
	 */
	@SuppressWarnings("unchecked")
	public List<TS2> allTS2(Long from, int noRows) throws Exception {
	 	String hql = "select o from TS2 o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<TS2>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.datatakeon.TS2}
	 * @throws Exception global exception
	 * @see    TS2
	 */
	public TS2 findByKey(Long id) throws Exception {
	 	String hql = "select o from TS2 o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (TS2)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find TS2 by description.
	 *
	 * @author TechFinium
	 * @param description the description
	 * @return a list of {@link haj.com.entity.datatakeon.TS2}
	 * @throws Exception global exception
	 * @see    TS2
	 */
	@SuppressWarnings("unchecked")
	public List<TS2> findByName(String description) throws Exception {
	 	String hql = "select o from TS2 o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<TS2>)super.getList(hql, parameters);
	}
	
	/**
	 * Find by TS 1.
	 *
	 * @param id the id
	 * @return the list
	 * @throws Exception the exception
	 */
	@SuppressWarnings("unchecked")
	public List<TS2> findByTS1(Long id) throws Exception {
	 	String hql = "select o from TS2 o where o.ts1.id = :id order by o.documentDateD , o.postingDateD " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (List<TS2>)super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<Tempx> allTempx() throws Exception {
		return (List<Tempx>)super.getList("select o from Tempx o");
	}
	
	@SuppressWarnings("unchecked")
	public List<Tempx> findByfindBySDLnumber(String sdlnumber) throws Exception {
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("sdlnumber", sdlnumber);
		return (List<Tempx>)super.getNQList("NQ_NATIVE_findBySDLnumber", parameters);
	}
	
	
	public TS2 invoiceTotalForSchemeYear(Integer setaSchemeYear) throws Exception {
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("setaSchemeYear", setaSchemeYear);
		return (TS2)super.getUniqueNQResult("NQ_NATIVE_invoicesForSchemeYear", parameters);
	}
	
	
	public TS2 invoiceTotalForPeriod(Date fromDate, Date toDate, Integer setaSchemeYear ) throws Exception {
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("fromDate", fromDate);
	    parameters.put("toDate", toDate);
	    parameters.put("setaSchemeYear", setaSchemeYear);
		return (TS2)super.getUniqueNQResult("NQ_NATIVE_invoicesForPeriod", parameters);
	}
	
	
	@SuppressWarnings("unchecked")
	public List<SarsLevyRecon> invoiceTotalPerSdlForSchemeYear(Integer setaSchemeYear) throws Exception {
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("setaSchemeYear", setaSchemeYear);
		return (List<SarsLevyRecon>)super.getNQList("NQ_NATIVE_invoicesPerSDLNrForSchemeYear", parameters);
	}
	
	public Date maxPostDate() throws Exception {
		return (Date)getSession().createNativeQuery("select max(posting_date_d) from t_s2").getSingleResult();
	}
	
	
	@SuppressWarnings("unchecked")
	public List<TS2> findByPeriod(Date fromDate, Date toDate, Integer setaSchemeYear) throws Exception {
	 	String hql = "select o from TS2 o where o.postingDateD between :fromDate and :toDate and  o.schemeYear = :setaSchemeYear " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("fromDate", fromDate);
	    parameters.put("toDate", toDate);
	    parameters.put("setaSchemeYear", setaSchemeYear);
		return (List<TS2>)super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<ReconSchemeYears> listSchemeYears() throws Exception {
	 	String hql = "select distinct new haj.com.bean.ReconSchemeYears( o.schemeYear) from TS2 o" ;
		return (List<ReconSchemeYears>)super.getList(hql);
	}
	
}

