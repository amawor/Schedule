package DataBase;

public class Student {
	private String studentID;
	private String password;
	public Student(String id,String password) {
		this.studentID=id;
		this.password=password;
	}
	public String getID() {
		return this.studentID;
	}
	public String getPW() {
		return this.password;
	}
}
