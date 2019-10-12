package br.org.crea.commons.models.redmine;

public class RedMineIssueDto {
	
	private IssueDto issue;

	public IssueDto getIssue() {
		return issue;
	}

	public void setIssue(IssueDto issue) {
		this.issue = issue;
	}
	
	public boolean enviaAtendimento () {
		return this.issue.getTracker_id() == 61 || this.issue.getTracker_id() == 63;
	}

}


