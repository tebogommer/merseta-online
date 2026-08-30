package haj.com.entity.datamodel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.Company;
import haj.com.entity.MandatoryGrant;
import haj.com.entity.enums.WspReportEnum;
import haj.com.service.MandatoryGrantService;

public class MandatoryGrantDataModel extends LazyDataModel<MandatoryGrant> {

	private static final long serialVersionUID = 1L;
	private List<MandatoryGrant> retorno = new ArrayList<MandatoryGrant>();
	private Company company;
	private WspReportEnum wspReport;
	private MandatoryGrantService service = new MandatoryGrantService();
	private Long funding;

	public MandatoryGrantDataModel(Company company, WspReportEnum wspReport, Long funding) {
		super();
		this.company = company;
		this.wspReport = wspReport;
		this.funding = funding;
	}

	@Override
	public List<MandatoryGrant> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
		try {
			retorno = service.allMandatoryGrant(MandatoryGrant.class, first, pageSize, sortField, sortOrder, filters, company);
			setRowCount(service.count(MandatoryGrant.class, filters, company));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retorno;
	}

	@Override
	public Object getRowKey(MandatoryGrant obj) {
		return obj.getId();
	}

	@Override
	public MandatoryGrant getRowData(String rowKey) {
		for (MandatoryGrant obj : retorno) {
			if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
		}
		return null;
	}

}
