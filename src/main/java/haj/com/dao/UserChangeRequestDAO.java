package haj.com.dao;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;

import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;
import haj.com.entity.UserChangeRequest;
import haj.com.entity.enums.ApprovalEnum;

public class UserChangeRequestDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all UserChangeRequest
 	 * @author TechFinium 
 	 * @see    UserChangeRequest
 	 * @return a list of {@link haj.com.entity.UserChangeRequest}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<UserChangeRequest> allUserChangeRequest() throws Exception {
		return (List<UserChangeRequest>)super.getList("select o from UserChangeRequest o");
	}

	/**
	 * Get all UserChangeRequest between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    UserChangeRequest
 	 * @return a list of {@link haj.com.entity.UserChangeRequest}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<UserChangeRequest> allUserChangeRequest(Long from, int noRows) throws Exception {
	 	String hql = "select o from UserChangeRequest o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<UserChangeRequest>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    UserChangeRequest
 	 * @return a {@link haj.com.entity.UserChangeRequest}
 	 * @throws Exception global exception
 	 */
	public UserChangeRequest findByKey(Long id) throws Exception {
	 	String hql = "select o from UserChangeRequest o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (UserChangeRequest)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find UserChangeRequest by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    UserChangeRequest
  	 * @return a list of {@link haj.com.entity.UserChangeRequest}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<UserChangeRequest> findByName(String description) throws Exception {
	 	String hql = "select o from UserChangeRequest o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<UserChangeRequest>)super.getList(hql, parameters);
	}
	
	
	@SuppressWarnings("unchecked")
	public List<UserChangeRequest> findByTargetKeyAndTargetClass(String targetClass,Long targetKey) throws Exception {
	 	String hql = "select o from UserChangeRequest o where o.targetClass = :targetClass and o.targetKey = :targetKey order by o.createDate desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("targetKey",targetKey);
	    parameters.put("targetClass",targetClass);
		return (List<UserChangeRequest>)super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public UserChangeRequest findLatestByTargetKeyAndTargetClass(String targetClass,Long targetKey) throws Exception {
	 	String hql = "select o from UserChangeRequest o where o.targetClass = :targetClass and o.targetKey = :targetKey order by o.createDate desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("targetKey",targetKey);
	    parameters.put("targetClass",targetClass);
		List<UserChangeRequest> list=(List<UserChangeRequest>) super.getList(hql, parameters,1);
		UserChangeRequest userChangeRequest=null;
		if(list !=null && list.size()>0){
			userChangeRequest=list.get(0);
		}
		return userChangeRequest;
	}
	
	

	@SuppressWarnings("unchecked")
	public List<UserChangeRequest> findByUserAndStatus(Long id, ApprovalEnum approvalStatus) {
		String hql = "select o from UserChangeRequest o where o.user.id = :id and o.approvalStatus = :approvalStatus order by createDate desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id",id);
	    parameters.put("approvalStatus",approvalStatus);
		return (List<UserChangeRequest>)super.getList(hql, parameters);
	}
}

