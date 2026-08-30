package haj.com.entity.datamodel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.CompanyLearners;
import haj.com.entity.WorkplaceMonitoring;
import haj.com.service.CompanyLearnersService;

public class CompanyLearnersTradeDataModel extends LazyDataModel<CompanyLearners> {

	private static final long serialVersionUID = 1L;
	private List<CompanyLearners> retorno = new ArrayList<CompanyLearners>();
	private CompanyLearnersService companyLearnersService = new CompanyLearnersService();
	private WorkplaceMonitoring workplacemonitoring = null;
	private Long designatedTradeID;

	public CompanyLearnersTradeDataModel(WorkplaceMonitoring workplacemonitoring, Long designatedTradeID) {
		super();
		this.workplacemonitoring = workplacemonitoring;
		this.designatedTradeID = designatedTradeID;
	}

	@Override
	public List<CompanyLearners> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

		try {
			retorno = companyLearnersService.sortAndFilter(first, pageSize, sortField, sortOrder, filters, workplacemonitoring.getCompany(), designatedTradeID);
			setRowCount(companyLearnersService.count(filters, workplacemonitoring.getCompany(), designatedTradeID));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retorno;
	}

	@Override
	public Object getRowKey(CompanyLearners obj) {
		return obj.getId();
	}

	@Override
	public CompanyLearners getRowData(String rowKey) {
		for (CompanyLearners obj : retorno) {
			if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
		}
		return null;
	}

}
