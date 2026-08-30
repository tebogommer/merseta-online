package haj.com.entity.datamodel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.Users;
import haj.com.entity.Wsp;
import haj.com.entity.enums.WspStatusEnum;
import haj.com.service.WspDGService;

public class ProjectOwnerWspDatamodel extends LazyDataModel<Wsp> {

	private static final long serialVersionUID = 1L;
	private List<Wsp>         retorno          = new ArrayList<Wsp>();
	private WspDGService      wspService       = new WspDGService();
	private Users             users;

	public ProjectOwnerWspDatamodel() {
		super();
	}

	public ProjectOwnerWspDatamodel(Users users) {
		super();
		this.users = users;
	}

	@Override
	public List<Wsp> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
		try {
			if (filters == null) filters = new HashMap<String, Object>();
			filters.put("projectOwnerID", users.getId());
			retorno = wspService.allWspProjectOwner(first, pageSize, sortField, sortOrder, filters);
			setRowCount(wspService.countWspProjectOwner(filters));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retorno;
	}

	@Override
	public Object getRowKey(Wsp obj) {
		return obj.getId();
	}

	@Override
	public Wsp getRowData(String rowKey) {
		for (Wsp obj : retorno) {
			if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
		}
		return null;
	}

}
