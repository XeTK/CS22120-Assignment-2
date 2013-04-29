package uk.ac.aber.rcs.cs211.schedulersim.scheduler;

import java.util.*;
import uk.ac.aber.rcs.cs211.schedulersim.*;

/**
 * @author thr2
 * @see uk.ac.aber.rcs.cs211.schedulersim.Simulator
 *
 */
public class ShortestFirst implements Scheduler {

	protected ArrayList<Job> queue;
	//private int numberOfJobs;
	
	public ShortestFirst () {
		this.queue = new ArrayList<Job>();
		//this.numberOfJobs=0;
	}
	
	public void addNewJob(Job job) throws SchedulerException {
		if (this.queue.contains(job)) throw new SchedulerException("Job already on Queue");
		//this.queue.add(this.numberOfJobs, job);
		System.out.println("Job :" + job.getLength());
		if (queue.size() != 0)
		{
			int i;
			for (i = 0; i < this.queue.size();i++)
			{
				if (this.queue.get(i).getLength() >= job.getLength())
				{	
					break;
				}
			}
			this.queue.add(i, job);
		}
		else
		{
			this.queue.add(job);
		}
		//this.numberOfJobs++;
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
		if (this.queue.size() == 0) throw new SchedulerException("Empty Queue");
		lastJobReturned = (Job)this.queue.get(0);
		return lastJobReturned;
	}

	public void returnJob(Job job) throws SchedulerException {
		if (!this.queue.contains(job)) throw new SchedulerException("Job not on Queue");
		// nothing to do in this implementation.
		Collections.sort(this.queue, new compar());
	}

	public void removeJob(Job job) throws SchedulerException {
		if (!this.queue.contains(job)) throw new SchedulerException("Job not on Queue");
		this.queue.remove(job);
		//this.numberOfJobs--;
	}

	public void reset() {
		this.queue.clear();
		//this.numberOfJobs=0;
	}

	public Job[] getJobList() {
		Job[] jobs = new Job[queue.size()];
		for (int i=0; i<queue.size(); i++) {
			jobs[i]=this.queue.get(i);
		}
		return jobs;
	}

}
class compar implements Comparator<Job>
{

	@Override
	public int compare(Job a, Job b)
	{
		return a.getLength() < b.getLength() ? -1 : a.getLength() == b.getLength() ? 0 : 1;
	}
}
