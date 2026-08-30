package haj.com.bean;

public class LegacyReportingBean {

	private String name;
	private Integer result;

	public LegacyReportingBean() {
		super();
	}

	public LegacyReportingBean(String name) {
		this();
		this.name = name;
	}

	public LegacyReportingBean(String name, Integer result) {
		this(name);
		this.result = result;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getResult() {
		return result;
	}

	public void setResult(Integer result) {
		this.result = result;
	}
}
