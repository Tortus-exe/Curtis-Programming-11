import java.lang.reflect.Array;
import java.util.ArrayList;

public class School {
    //this is a school object that contains a list of Teachers at the school, a list of students at the school, a list of courses offered at the school,
    //the name of the school, the total population of the school, and the start time of the first class.
    //The Teacher and Student classes are custom classes.
    ArrayList<Teacher> teachers = new ArrayList<>();
    ArrayList<Student> students = new ArrayList<>();
    ArrayList<String> courses = new ArrayList<>();
    String name;
    //the name of the school
    int population = teachers.size() + students.size();
    //the population of the school is the total number of people.
    double startTime = 8.5;
    //the start time in hours, school starts at 8:30 by default

    School() {
        //what if we initialize a school without any parameters? We get an empty school!
        this.teachers = new ArrayList<>();
        //no teachers yet
        this.students = new ArrayList<>();
        //no students
        this.courses = new ArrayList<>();
        //we don't offer any courses
        this.name = "unknown";
        //we haven't even decided on a name yet!
        this.population = 0;
        //there is no one at the school.
        this.startTime = 8.5;
        //we start at 8:30 by default.
    }

    School(ArrayList<Teacher> teachers, ArrayList<Student> students, ArrayList<String> courses, String name, double startTime) {
        //now we have parameters!
        this.teachers = teachers;
        this.students = students;
        this.courses = courses;
        this.name = name;
        this.population = teachers.size() + students.size();
        //the population is not a parameter because it depends on the total number of people, which is the number of teachers and students together.
        this.startTime = startTime;
        //set each of the fields to each of the matching parameters.
    }

    public void AddTeacher(Teacher newTeacher) {
        this.teachers.add(newTeacher);
    }
    //use the built-in add method from arraylists to add a new teacher
    public void AddStudent(Student newStudent) {
        this.students.add(newStudent);
    }
    //use the built-in add method from arraylists to add a new student
    public void AddCourse(String newCourse) { this.courses.add(newCourse); }
    //use the built-in add method from arraylists to add a new course
    public void RemoveTeacher(int index) {
        this.teachers.remove(index);
    }
    //use the built-in add method from arraylists to remove a teacher based on an index
    public void RemoveStudent(int index) {
        this.students.remove(index);
    }
    //use the built-in add method from arraylists to remove a student based on an index
    public void RemoveTeacher(Teacher teacherToRemove) {
        this.teachers.remove(teacherToRemove);
    }
    //use the built-in add method from arraylists to remove a teacher based on the teacher object we want to remove.
    public void RemoveStudent(Student studentToRemove) {
        this.students.remove(studentToRemove);
    }
    //use the built-in add method from arraylists to remove a student based on the student object we want to remove
    public ArrayList<Teacher> ShowAllTeachers() {
        for(int i = 0; i < this.teachers.size(); i++) {
            System.out.println(this.teachers.get(i));
        }
        //loop through all the teachers and print them out
        return this.teachers;
        //in case we want to use this method as a parameter we need to write a return statement.
    }

    public ArrayList<Student> ShowAllStudents() {
        for(int i = 0; i < this.students.size(); i++) {
            System.out.println(this.students.get(i));
        }
        //loop through all the students and print them out
        return this.students;
        //in case we want to use this method as a parameter we need to write a return statement.
    }

    public String GetName() { return this.name; }
    public int GetPopulation() { return this.population; }
    public double GetStartTime() { return this.startTime; }
    //all the get functions simply return the field from this object
    public void Rename(String newName) { this.name = newName; }
    public void OverwritePopulation(int newPop) { this.population = newPop; }
    public void SetStartTime(double newStartTime) { this.startTime = newStartTime; }
    //All the set commands set their respective fields in this object. OverwritePopulation is called that to emphasize that it should not be used in most situations since the population is automatically set based on the length of the teacher and student arrayLists
}
