package haj.com.dao.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;
import haj.com.entity.lookup.PostCodeLink;

public class PostCodeLinkDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all PostCodeLink
 	 * @author TechFinium 
 	 * @see    PostCodeLink
 	 * @return a list of {@link haj.com.entity.PostCodeLink}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<PostCodeLink> allPostCodeLink() throws Exception {
		return (List<PostCodeLink>)super.getList("select o from PostCodeLink o");
	}
	
	public Integer countAllResults() throws Exception {
		return ((Long)super.getUniqueResult("select count(o) from PostCodeLink o")).intValue();
	}

	/**
	 * Get all PostCodeLink between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    PostCodeLink
 	 * @return a list of {@link haj.com.entity.PostCodeLink}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<PostCodeLink> allPostCodeLink(Long from, int noRows) throws Exception {
	 	String hql = "select o from PostCodeLink o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<PostCodeLink>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    PostCodeLink
 	 * @return a {@link haj.com.entity.PostCodeLink}
 	 * @throws Exception global exception
 	 */
	public PostCodeLink findByKey(Long id) throws Exception {
	 	String hql = "select o from PostCodeLink o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (PostCodeLink)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find PostCodeLink by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    PostCodeLink
  	 * @return a list of {@link haj.com.entity.PostCodeLink}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<PostCodeLink> findByName(String description) throws Exception {
	 	String hql = "select o from PostCodeLink o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<PostCodeLink>)super.getList(hql, parameters);
	}
	
	public int countWhereNumberNotAssigned() throws Exception {
	 	String hql = "select count(o) from PostCodeLink o where o.postCodeUsedForSarsNumberValue is null" ;
		return ((Long) super.getUniqueResult(hql)).intValue();
	}
	
	public int countByPostCodeLinkByNumberAssigned(int number) throws Exception {
	 	String hql = "select count(o) from PostCodeLink o where o.postCodeUsedForSarsNumberValue = :number " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("number", number);
	    return ((Long) super.getUniqueResult(hql, parameters)).intValue();
	}
	
	@SuppressWarnings("unchecked")
	public List<PostCodeLink> findPostCodeLinkByNumberAssigned(int number) throws Exception {
	 	String hql = "select o from PostCodeLink o where o.postCodeUsedForSarsNumberValue = :number " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("number", number);
		return (List<PostCodeLink>)super.getList(hql, parameters);
	}
}

