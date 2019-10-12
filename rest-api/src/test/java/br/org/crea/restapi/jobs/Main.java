package br.org.crea.restapi.jobs;

import org.quartz.SchedulerException;

public class Main {

	public static void main(String[] args) throws SchedulerException{
		Person person = new Person("Tiago");
		JobSchedulerBuilder jobSchedulerBuilder = new JobSchedulerBuilder(person);
		jobSchedulerBuilder.night();
	}

}
