package br.org.crea.commons.models.redmine;

public class IssueDto {
	
	private int project_id;
	
	private String subject;
	
	private String description;
	
	private int tracker_id;

	public int getProject_id() {
		return project_id;
	}

	public void setProject_id(int project_id) {
		this.project_id = project_id;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getTracker_id() {
		return tracker_id;
	}

	public void setTracker_id(int tracker_id) {
		this.tracker_id = tracker_id;
	}
	
	

}
