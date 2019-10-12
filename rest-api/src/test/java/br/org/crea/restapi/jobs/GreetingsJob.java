package br.org.crea.restapi.jobs;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class GreetingsJob implements Job {
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		String greetings = (String) context.getJobDetail().getJobDataMap().get("greetings");
		Person person = (Person) context.getJobDetail().getJobDataMap().get("person");
		String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		System.out.println(date + " " + greetings + person.getName());
	}

}
