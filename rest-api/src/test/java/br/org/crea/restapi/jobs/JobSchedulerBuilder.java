package br.org.crea.restapi.jobs;

import java.time.LocalDateTime;

public class JobSchedulerBuilder {
	
	private JobScheduler jobScheduler = new JobScheduler();
	private final Person person;
	private LocalDateTime today = LocalDateTime.now();

	public JobSchedulerBuilder(final Person person) {
		this.person = person;
	}

	public void morning() {
		String greetings = "Good morning, ";
		jobScheduler.schedule(person, greetings, today.getHour(), today.plusMinutes(3).getSecond());
	}

	public void afternoon() {
		String greetings = "Good Afternoon, ";
		jobScheduler.schedule(person, greetings, today.getHour(), today.plusMinutes(3).getSecond());
	}

	public void night() {
		String greetings = "Good night, ";
		jobScheduler.schedule(person, greetings, today.getHour(), today.plusMinutes(3).getMinute());
	}
}
