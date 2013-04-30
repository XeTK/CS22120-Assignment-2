package uk.ac.aber.rcs.cs211.schedulersim.scheduler;

import java.util.*;
import uk.ac.aber.rcs.cs211.schedulersim.*;

/**
 * Based of RCS's First Come First Serve Class, this is a very simple implementation of the round robin scheduling approach 
 * @author thr2 & rcs
 * @see uk.ac.aber.rcs.cs211.schedulersim.Simulator
 *
 */
public class RoundRobin implements Scheduler {

	protected ArrayList<Job> queue;
	private int numberOfJobs, cyclesperjob; //added a variable to keep track of how many ticks a specific job takes
	
	public RoundRobin () {
		this.queue = new ArrayList<Job>();
		this.numberOfJobs=0;
	}
	
	public void addNewJob(Job job) throws SchedulerException {
		if (this.queue.contains(job)) throw new SchedulerException("Job already on Queue");
		this.queue.add(this.numberOfJobs, job);
		this.numberOfJobs++;
	}

	/**
	 * Returns the next job at the head of the ready queue.
	 * This method should only ever do this - the queue should be kept in the correct order when things are
	 * added and removed.
	 * 
	 * Think about making an abstract class rather then an interface, and make this method final.
	 */
	public Job getNextJob() throws SchedulerException {
		Job lastJobReturned;
		if (this.numberOfJobs<1) throw new SchedulerException("Empty Queue");
		lastJobReturned = (Job)this.queue.get(0);
		this.cyclesperjob++; //when we return the current job we increment the timer
		return lastJobReturned;
	}
	/**
	 * This method is called when the job is returned after a single execution
	 * in a round robin once the process has got to a specific number then it resets the array counter to 0 then removes and readds the job to the queue
	 */
	public void returnJob(Job job) throws SchedulerException {
		if (!this.queue.contains(job)) throw new SchedulerException("Job not on Queue");
		if (this.cyclesperjob == 4)
		{
			this.cyclesperjob = 0;
			this.queue.remove(job);
			this.queue.add(job);
		}
	}
	
	public void removeJob(Job job) throws SchedulerException {
		if (!this.queue.contains(job)) throw new SchedulerException("Job not on Queue");
		this.queue.remove(job);
		this.numberOfJobs--;
		this.cyclesperjob = 0; //reset the count of how many ticks we have done.
	}

	public void reset() {
		this.queue.clear();
		this.numberOfJobs=0;
	}

	public Job[] getJobList() {
		Job[] jobs = new Job[queue.size()];
		for (int i=0; i<queue.size(); i++) {
			jobs[i]=this.queue.get(i);
		}
		return jobs;
	}

}
