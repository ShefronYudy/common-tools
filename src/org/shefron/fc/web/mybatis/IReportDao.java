package org.shefron.fc.web.mybatis;

import java.util.List;

public interface IReportDao {

	public ReportPojo getReport(int id);

	public List<ReportPojo> getReports(int taskid);

}
