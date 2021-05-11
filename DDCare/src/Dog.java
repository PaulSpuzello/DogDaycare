public class Dog {
	String fName = "", lName = "", dogName = "", dogBreed = "";
	
	public Dog() {
		fName = "";
		lName = "";
		dogName = "";
		dogBreed = "";
	}
	
	public Dog(String fname, String lname, String dogname, String dogbreed) {
		fName = fname;
		lName = lname;
		dogName = dogname;
		dogBreed = dogbreed;
	}

	public String getfName() {
		return fName;
	}

	public void setfName(String fName) {
		this.fName = fName;
	}

	public String getlName() {
		return lName;
	}

	public void setlName(String lName) {
		this.lName = lName;
	}

	public String getDogName() {
		return dogName;
	}

	public void setDogName(String dogName) {
		this.dogName = dogName;
	}

	public String getDogBreed() {
		return dogBreed;
	}

	public void setDogBreed(String dogBreed) {
		this.dogBreed = dogBreed;
	}
	
	public String print() {
		return " Owner's name: " + fName + " " + lName + "\n" + " Pet name: " + dogName
				+ "\n" + " Breed: " + dogBreed + "\n";
		
	}
}
