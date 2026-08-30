package haj.com.dao;

import java.util.List;
import java.util.Map;

import haj.com.entity.Doc;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.framework.IDataEntity;
import haj.com.provider.MySQLProvider;

// TODO: Auto-generated Javadoc
/**
 * The Class BlankDAO.
 */
public class ResolveDataDAO extends AbstractDAO {

	/*
	 * (non-Javadoc)
	 * 
	 * @see haj.com.framework.AbstractDAO#getDataProvider()
	 */
	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	@SuppressWarnings("unchecked")
	public List<String> findAllAvailableTargetClasses() {
		String hql = "select o.targetClass from Doc o where o.targetClass is not null group by o.targetClass";
		return (List<String>) super.getList(hql);
	}


	@SuppressWarnings("unchecked")
	public List<IDataEntity> queryDB(String hql, Map<String, Object> parameters) {
		return (List<IDataEntity>) super.getList(hql, parameters);
	}
}
