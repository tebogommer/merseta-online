package haj.com.bean;

import java.io.Serializable;

import haj.com.entity.Company;
import haj.com.entity.SDFCompany;
import haj.com.entity.Tasks;
import haj.com.entity.Users;

public class TaskReportBean implements Serializable {

	private Tasks task;
	private SDFCompany sdfCompany;
	private Users user;
	private Company company;

	public TaskReportBean() {
		super();
	}

	public TaskReportBean(Tasks task, Company company) {
		super();
		this.task = task;
		this.company = company;
	}

	public TaskReportBean(Tasks task, SDFCompany sdfCompany) {
		super();
		this.task = task;
		this.sdfCompany = sdfCompany;
	}

	public TaskReportBean(Tasks task, Users user) {
		super();
		this.task = task;
		this.user = user;
	}

	public Tasks getTask() {
		return task;
	}

	public void setTask(Tasks task) {
		this.task = task;
	}

	public SDFCompany getSdfCompany() {
		return sdfCompany;
	}

	public void setSdfCompany(SDFCompany sdfCompany) {
		this.sdfCompany = sdfCompany;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}



}
