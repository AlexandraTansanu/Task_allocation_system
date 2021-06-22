package SoftwareEngineeringPractice;

/* This class might be deleted and replaced with database functionality */
public class Task implements Comparable<Task> {
	private String name;
	private int duration;
	private String importance;
	private String frequency;
	private boolean isRoutine;
	
	public Task() {
		
	}
	
	void setName(String n) {
		this.name = n;
	}
	
	String getName() {
		return this.name;
	}
	
	void setDuration(int dur) {
		this.duration = dur;
	}
	
	int getDuration() {
		return this.duration;
	}
	
	void setImportance(String imp) {
		this.importance = imp;
	}
	
	String getImportance() {
		return this.importance;
	}
	
	void setFrequency(String freq) {
		this.frequency = freq;
	}
	
	String getFrequency() {
		return this.frequency;
	}
	
	void setRoutine(boolean rout) {
		this.isRoutine = rout;
	}
	
	boolean isRoutine() {
		return this.isRoutine;
	}
	
	/* Prioritises based on importance. If equal, then
    based on time taken. If also equal, then based on most frequent ones */
	@Override public int compareTo(Task other) {
		if((importance.length() - other.importance.length()) == 0) {
			if((duration - other.duration) == 0) {
				return frequency.compareTo(other.frequency);
			}
			else {
				return duration - other.duration;
			}
		}
		else {
			return importance.length() - other.importance.length();
		}
	}
	
	/* Override the equals method */
	@Override public boolean equals(Object o) {
		if(o instanceof Task) {
			if(name.equals(((Task) o).name) && duration == (((Task) o).duration) && importance.equals(((Task) o).importance) 
					&& frequency.equals(((Task) o).frequency) && isRoutine == (((Task) o).isRoutine)) {
				return true;
			}
		}
		return false;
	}
}
