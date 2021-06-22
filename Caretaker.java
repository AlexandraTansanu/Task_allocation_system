package SoftwareEngineeringPractice;

public class Caretaker {
	private String name;
	private Integer password;
	
	public Caretaker() {
		
	}
	
	void setName(String newName) {
		 this.name = newName;
	}
	
	String getName() {
		return this.name;
	}
	
	void setPassword(Integer newPassword) {
		 this.password = newPassword;
	}
	
	Integer getPassword() {
		return this.password;
	}
}
