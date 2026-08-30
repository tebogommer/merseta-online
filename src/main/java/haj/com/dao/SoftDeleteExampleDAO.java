package haj.com.dao;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.Blank;
import haj.com.entity.SoftDeleteExample;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;


public class SoftDeleteExampleDAO extends AbstractDAO  {

	/* (non-Javadoc)
	 * @see haj.com.framework.AbstractDAO#getDataProvider()
	 */
	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all Blank.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.Blank}
	 * @throws Exception global exception
	 * @see    Blank
	 */
	@SuppressWarnings("unchecked")
	public List<SoftDeleteExample> allSoftDeleteExample() throws Exception {
		return (List<SoftDeleteExample>)super.getList("select o from SoftDeleteExample o");
	}

	
	public SoftDeleteExample findByKey(Long id) throws Exception {
	 	String hql = "select o from SoftDeleteExample o left join fetch o.softDeleteChildExamples where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (SoftDeleteExample)super.getUniqueResult(hql, parameters);
	}

	
}

