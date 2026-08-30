package haj.com.dao;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;
import haj.com.entity.AddressChange;
import haj.com.entity.Users;
import haj.com.entity.enums.AddressEnum;
import haj.com.entity.enums.ApprovalEnum;

public class AddressChangeDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all AddressChange
 	 * @author TechFinium 
 	 * @see    AddressChange
 	 * @return a list of {@link haj.com.entity.AddressChange}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<AddressChange> allAddressChange() throws Exception {
		return (List<AddressChange>)super.getList("select o from AddressChange o");
	}

	/**
	 * Get all AddressChange between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    AddressChange
 	 * @return a list of {@link haj.com.entity.AddressChange}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<AddressChange> allAddressChange(Long from, int noRows) throws Exception {
	 	String hql = "select o from AddressChange o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<AddressChange>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    AddressChange
 	 * @return a {@link haj.com.entity.AddressChange}
 	 * @throws Exception global exception
 	 */
	public AddressChange findByKey(Long id) throws Exception {
	 	String hql = "select o from AddressChange o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (AddressChange)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find AddressChange by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    AddressChange
  	 * @return a list of {@link haj.com.entity.AddressChange}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<AddressChange> findByName(String description) throws Exception {
	 	String hql = "select o from AddressChange o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<AddressChange>)super.getList(hql, parameters);
	}

	public AddressChange findByUser(Users user, AddressEnum addressEnum) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public List<AddressChange> findByUser(Long userID, AddressEnum addressEnum) throws Exception {
	 	String hql = "select o from AddressChange o where o.user.id = :userID and o.addressEnum = :addressEnum order by o.createDate desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("userID", userID);
	    parameters.put("addressEnum", addressEnum);
		return (List<AddressChange>)super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public int countByForPostalAndForResidentialAddress(Long forResID,Long forPostID,ApprovalEnum status) throws Exception {
	 	String hql = "select count(o) from AddressChange o where o.forResidentialAddress.id = :forResID and o.forPostalAddress.id = :forPostID and o.status = :status" ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("forResID", forResID);
	    parameters.put("forPostID", forPostID);
	    parameters.put("status", status);
		return super.countWhere(parameters, hql);
	}
}

