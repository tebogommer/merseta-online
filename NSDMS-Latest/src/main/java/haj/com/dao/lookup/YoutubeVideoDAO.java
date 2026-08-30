package haj.com.dao.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.lookup.YoutubeVideo;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

public class YoutubeVideoDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all YoutubeVideo
 	 * @author TechFinium 
 	 * @see    YoutubeVideo
 	 * @return a list of {@link haj.com.entity.YoutubeVideo}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<YoutubeVideo> allYoutubeVideo() throws Exception {
		return (List<YoutubeVideo>)super.getList("select o from YoutubeVideo o");
	}

	/**
	 * Get all YoutubeVideo between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    YoutubeVideo
 	 * @return a list of {@link haj.com.entity.YoutubeVideo}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<YoutubeVideo> allYoutubeVideo(Long from, int noRows) throws Exception {
	 	String hql = "select o from YoutubeVideo o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<YoutubeVideo>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    YoutubeVideo
 	 * @return a {@link haj.com.entity.YoutubeVideo}
 	 * @throws Exception global exception
 	 */
	public YoutubeVideo findByKey(Long id) throws Exception {
	 	String hql = "select o from YoutubeVideo o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (YoutubeVideo)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find YoutubeVideo by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    YoutubeVideo
  	 * @return a list of {@link haj.com.entity.YoutubeVideo}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<YoutubeVideo> findByName(String description) throws Exception {
	 	String hql = "select o from YoutubeVideo o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<YoutubeVideo>)super.getList(hql, parameters);
	}
}

