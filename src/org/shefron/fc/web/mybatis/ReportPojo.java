package org.shefron.fc.web.mybatis;

public class ReportPojo {

	private int id = 0;
	private int taskid = 0;

	private String name;

	public int getId() {
		return id;
	}

	public int getTaskid() {
		return taskid;
	}

	public String getName() {
		return name;
	}

	public String getUrl() {
		return url;
	}

	public String getPath() {
		return path;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setTaskid(int taskid) {
		this.taskid = taskid;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setPath(String path) {
		this.path = path;
	}

	private String url;
	private String path;

}
