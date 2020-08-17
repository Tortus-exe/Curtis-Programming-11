public class Teacher {
    //This is the teacher class, a custom class that contains the first name, last name, and subject taught of a teacher.
    //All three fields must be set at initialization. They can be modified individually by using the set methods.
    //the school class contains a list of Teacher objects.
    String firstName;
    String lastName;
    String subject;

    public Teacher() {
        this.firstName = "unknown";
        this.lastName = "unknown";
        this.subject = "unknown";
        //this will probably not be used, but it is good to have a constructor to create an empty teacher object.
    }

    public Teacher(String firstName, String lastName, String subject) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.subject = subject;
        //When we actually know the first and last name of a teacher and the course the teacher teaches we use this constructor
    }

    public String toString() {
        return "Name: " + this.firstName + " " + this.lastName + " Subject: " + this.subject;
    }
    //ensures that printing this object will show not as a hash code but as a certain format of string as specified in the instructions
    public void setFirstName (String newFirstName) {
        this.firstName = newFirstName;
    }
    public void setLastName (String newLastName) { this.lastName = newLastName; }
    public void setSubject (String newSubject) {
        this.subject = newSubject;
    }
    //all the set methods simply make a field in this object equal to the input parameter of a MATCHING TYPE
    public String getFirstName () { return this.firstName; }
    public String getLastName () {
        return this.lastName;
    }
    public String getSubject () {
        return this.subject;
    }
    //all the get methods simply return their respective fields in this object
}
