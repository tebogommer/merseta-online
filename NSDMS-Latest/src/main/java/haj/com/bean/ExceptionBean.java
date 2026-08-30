package haj.com.bean;

public class ExceptionBean {

	private Long   id;
	private String exceptions;

	public ExceptionBean(Long id, String exceptions) {
		super();
		this.id = id;
		this.exceptions = exceptions;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getExceptions() {
		return exceptions;
	}

	public void setExceptions(String exceptions) {
		this.exceptions = exceptions;
	}
}
