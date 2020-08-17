public class Main {
    public static void main(String[] args) {
        School school = new School();
        school.Rename("Vancouver High School");
        //create a new school object called Vancouver High School.
        Student[] studentsArray = {
                new Student("John", "Smith", 9),
                new Student("Bill", "Stewart", 8),
                new Student("Hunter", "Mills", 8),
                new Student("Grace", "Abbott", 9),
                new Student("Connor", "East", 9),
                new Student("Michael", "Warren", 8),
                new Student("Fred", "Moore", 10),
                new Student("James", "McDonald", 10),
                new Student("Bill", "Weber", 9),
                new Student("Shelley", "Findlay", 9)};
        Teacher[] teachersArray = {
                new Teacher("Steven", "Barry", "Chemistry"),
                new Teacher("Emanuel", "Boyle", "Geography"),
                new Teacher("Anaya", "Clayton", "English")
        };
        //define all our teachers and students first so I don't have to write 13 lines of code adding each of them individually
        for(int x = 0; x < studentsArray.length; x++) {
            school.AddStudent(studentsArray[x]);
        }
        for(int y = 0; y < teachersArray.length; y++) {
            school.AddTeacher(teachersArray[y]);
        }
        for(int z = 0; z < teachersArray.length; z++) {
            school.AddCourse(teachersArray[z].getSubject());
        }
        //loop through all the students and teachers and add them to the school object
        school.ShowAllTeachers();
        school.ShowAllStudents();
        //show all teachers and students
        school.RemoveStudent(6);
        school.RemoveStudent(8);
        school.RemoveTeacher(2);
        System.out.println("----------------------------------------");
        //to keep things organized I'm going to add a line break there
        school.ShowAllTeachers();
        school.ShowAllStudents();
        //show all teachers and students after removing two students and one teacher
    }
}
