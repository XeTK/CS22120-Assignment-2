package uk.ac.aber.rcs.cs211.schedulersim.scheduler;

import java.util.*;
import uk.ac.aber.rcs.cs211.schedulersim.*;

/**
 * This is a primitive implementation of the lottery scheduling algorithm
 * Based off roundrobin class
 * @author thr2 & rcs
 * @see uk.ac.aber.rcs.cs211.schedulersim.Simulator
 *
 */
public class Lottery implements Scheduler {

	protected ArrayList<Job> queue;
	private int numberOfJobs, count; //added count for the number of ticks that a task is carried out
	
	public Lottery () {
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
		//we increment are timer for the round robin approach 
		count++;
		return lastJobReturned;
	}
	/**
	 * When we return a job to the queue after doing a execution step
	 * when the executing process reaches a random number of ticks then we shuffle the collection and choose another job
	 */
	public void returnJob(Job job) throws SchedulerException {
		if (!this.queue.contains(job)) throw new SchedulerException("Job not on Queue");	
		//get a random int and then check if the number of tick we have is greater than that
		if (count > new Random().nextInt(10))
		{
			//shuffle are collection so that it is a lottery which job gets chosen
			Collections.shuffle(this.queue, new Random(System.nanoTime()));
			//return are ticks to nothing
			count = 0;
		}
	}
	/**
	 * This method removes the job when it has completed and will reset the counters on the round robin approach implemented
	 */
	public void removeJob(Job job) throws SchedulerException {
		if (!this.queue.contains(job)) throw new SchedulerException("Job not on Queue");
		this.queue.remove(job);
		this.numberOfJobs--;
		count = 0;
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
