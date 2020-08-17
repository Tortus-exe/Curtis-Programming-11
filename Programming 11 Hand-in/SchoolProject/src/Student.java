import java.util.ArrayList;

public class Student {
    //This is a student class that contains a student's first and last name, the grade they are in, and their individual student number.
    //The class automatically generates a student number, so when initializing a student object, there is no student number parameter.
    //the first name, last name, and grade a student is in must be set when initializing a student object. They can be individually set using the set methods. 
    //the school class contains a list of Student objects.
    String firstName;
    String lastName;
    int grade;
    int studentNumber;
    //all students have these four fields as specified by the instructions
    private static ArrayList<Integer> AllStudentNumbers = new ArrayList<>();
    //this variable is private so it can't be accessed outside the class, and static so it stays the same through all instances of the class
    //it keeps track of all the currently used 9-digit student numbers so we don't have repeats. We wouldn't want two students with the same student number!
    //since arraylists cannot contain primitives, we set it to the Integer object instead, which allows us to input integers into the array.
    public Student() {
        this.firstName = "unknown";
        //if we have no parameters we don't know the first name of the student
        this.lastName = "unknown";
        //nor the last name
        this.grade = 0;
        //nor their grade
        do {
            this.studentNumber = (int) (Math.random() * 100000000);
        } while(AllStudentNumbers.contains(this.studentNumber));
        AllStudentNumbers.add(this.studentNumber);
        //all we can do is generate a random 9-digit student number, check AFTER GENERATION whether it is already in the list of all student numbers, continue generating if it is, break if it isn't, and add it to the list of all student numbers upon breaking out of the loop.
    }

    public Student(String firstName, String lastName, int grade) {
        this.firstName = firstName;
        //we actually know the first name of the student so we set it to the parameter
        this.lastName = lastName;
        //we know the last name so we set that to the parameter as well
        this.grade = grade;
        //we set the grade to the parameter
        do {
            this.studentNumber = (int) (Math.random() * 100000000);
            //generate a number
        } while(AllStudentNumbers.contains(this.studentNumber));
        //if the number is already in the list of all student numbers, do it again
        AllStudentNumbers.add(this.studentNumber);
        //we will only reach this code if we break out of the do-while
        //add this student number to the list of all student numbers
    }

    public String toString() {
        return "Name: " + this.firstName + " " + this.lastName + " Grade: " + this.grade;
    }
    //this ensures that printing this object returns a string of a certain format as specified by the instructions
    public String getFirstName() {
        return this.firstName;
    }
    public String getLastName() {
        return this.lastName;
    }
    public int getGrade() { return this.grade; }
    public int getStudentNumber() {
        return this.studentNumber;
    }
    //all the get methods simply return their respective fields from this object
    public void setFirstName(String newFirstName) {
        this.firstName = newFirstName;
    }
    public void setLastName(String newLastName) {
        this.lastName = newLastName;
    }
    public void setGrade(int newGrade) {
        this.grade = newGrade;
    }
    public void setStudentNumber(int newStudentNumber) {
        this.studentNumber = newStudentNumber;
    }
    //all the set methods simply make the field from this object equal to the parameter OF A MATCHING TYPE
}
