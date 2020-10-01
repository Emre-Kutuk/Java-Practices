package nl.inholland;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.*;

public class Main
    {

    public static void main(String[] args) throws IOException
        {
        Main program = new Main();
        program.Start();
        }

    public void Start() throws IOException
        {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        User user;
        if ((user = CheckUser(username, password)) != null)
            {
            System.out.println("Welcome " + user.username.substring(0, 1).toUpperCase() + user.username.substring(1).toLowerCase());
            if (user.getType() == 0)
                {
                ShowStudentInterface();
                } else if (user.getType() == 1)
                {
                ShowTeacherInterface();
                } else if (user.getType() == 2)
                {
                //SHOW ADMIN
                }

            } else
            System.out.println("Wrong credentials, please try again!");


        }

    public void ShowTeacherInterface() throws IOException
        {
        Scanner scanner = new Scanner(System.in);
        while (true)
            {
            System.out.print("\nS. Display Students | T. Display Teachers | A. Add Students | R. Display Reports |X. Exit \nPlease enter a choice: ");
            String userInput = scanner.next();

            if (userInput.equalsIgnoreCase("s"))
                {
                DisplayStudents();
                } else if (userInput.equalsIgnoreCase("t"))
                {
                DisplayTeachers();
                } else if (userInput.equalsIgnoreCase("a"))
                {
                AddStudentInterface();
                } else if (userInput.equalsIgnoreCase("r"))
                {
                DisplayReportInterface();
                } else if (userInput.equalsIgnoreCase("x"))
                {
                System.out.println("\nExiting application...");
                break;
                }
            }

        }

    private void DisplayReportInterface() throws IOException
        {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nSTUDENT RESULTS" +
                "\n\nFirstName          LastName          Birthdate          Age          Group          Java     CSharp     Python     PHP\n" +
                "*********          ********          *********          ***          *****          ****     ******     ******     ***\n");
        for (Student s : GetAllStudents())
            {
            s.GetGrades();
            System.out.format("%s              %s               %s         %s          %s          %d          %d        %d          %d\n",
                    s.getFirstName(), s.getLastName(), s.getBirthdate(), LocalDate.now().getYear() - s.getBirthdate().getYear(), s.getGroup(), s.getGradeJava(), s.getGradeCsharp(), s.getGradePython(), s.getGradePHP());
            }

        System.out.print("\nEnter student first name(Report Details) | Or 0 back to main menu: ");
        String userInput = scanner.next();
        if(userInput.equalsIgnoreCase("0"))
            DisplayReportInterface();
        else
            {
            String studentName = userInput;
            System.out.format("Enter lastname for %s : ", studentName.substring(0,1).toUpperCase() + studentName.substring(1).toLowerCase());
            userInput = scanner.next();
            }
        }


    private void AddStudentInterface() throws IOException
        {
        Scanner scanner = new Scanner(System.in);

        System.out.println("ADD STUDENT");
        System.out.print("\nChoose a username: ");
        String username = scanner.next();
        System.out.print("Choose a password: ");
        String password = scanner.next();
        System.out.print("Enter first name: ");
        String firstName = scanner.next();
        System.out.print("Enter a last name: ");
        String lastName = scanner.next();
        System.out.print("Please enter date of birth in YYYY-MM-DD: ");
        LocalDate birthdate = LocalDate.parse(scanner.next());
        System.out.print("Enter group: ");
        String group = scanner.next();

        Student student = new Student(username, password, firstName, lastName, birthdate, 0, group);
        student.SaveStudent();
        System.out.print("Student has been saved successfully!");
        }


    private void ShowStudentInterface() throws IOException
        {
        Scanner scanner = new Scanner(System.in);
        while (true)
            {
            System.out.print("\nS. Display Students | T. Display Teachers | X. Exit \nPlease enter a choice: ");
            String userInput = scanner.next();

            if (userInput.equalsIgnoreCase("s"))
                {
                DisplayStudents();
                } else if (userInput.equalsIgnoreCase("t"))
                {
                DisplayTeachers();
                } else if (userInput.equalsIgnoreCase("x"))
                {
                System.out.println("\nExiting application...");
                break;
                }
            }
        }

    private void DisplayTeachers() throws IOException
        {
        System.out.println("\nLIST OF TEACHERS" +
                "\n\nFirstName          LastName          Birthdate          Age\n" +
                "*********          ********          *********          ***\n");

        for (Teacher t : GetAllTeachers())
            {
            System.out.format("%s              %s               %s         %s\n",
                    t.getFirstName(), t.getLastName(), t.getBirthdate(), LocalDate.now().getYear() - t.getBirthdate().getYear());
            }
        }

    private void DisplayStudents() throws IOException
        {
        System.out.println("\nLIST OF STUDENTS" +
                "\n\nFirstName          LastName          Birthdate          Age          Group\n" +
                "*********          ********          *********          ***          *****\n");
        for (Student s : GetAllStudents())
            {
            System.out.format("%s              %s               %s         %s          %s\n",
                    s.getFirstName(), s.getLastName(), s.getBirthdate(), LocalDate.now().getYear() - s.getBirthdate().getYear(), s.getGroup());
            }
        }

    private List<Teacher> GetAllTeachers() throws IOException
        {
        List<Teacher> allTeachers = new ArrayList<>();
        for (User u : GetAllUsers())
            {
            if (u.getType() == 1)
                {
                allTeachers.add((Teacher) u);
                }
            }

        return allTeachers;
        }

    private List<Student> GetAllStudents() throws IOException
        {
        List<Student> allStudents = new ArrayList<>();
        for (User u : GetAllUsers())
            {
            if (u.getType() == 0)
                {
                allStudents.add((Student) u);
                }
            }

        return allStudents;
        }

    private List<User> GetAllUsers() throws IOException
        {
        File users = new File("users.txt");
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(users)));

        List<User> allUsers = new ArrayList<>();
        String line;

        while ((line = reader.readLine()) != null)
            allUsers.add(GetUserFromString(line));

        return allUsers;
        }

    public User GetUserFromString(String line)
        {
        User user = new User();

        String[] userInfo = line.split(",");

        if (Integer.parseInt(userInfo[5]) == 0)
            {
            user = new Student();
            ((Student) user).group = userInfo[6];
            } else if (Integer.parseInt(userInfo[5]) == 1)
            {
            user = new Teacher();
            }

        user.username = userInfo[0];
        user.password = userInfo[1];
        user.firstName = userInfo[2];
        user.lastName = userInfo[3];
        user.birthdate = LocalDate.parse(userInfo[4]);
        user.type = Integer.parseInt(userInfo[5]);

        return user;
        }

    public User CheckUser(String username, String password) throws IOException
        {
        for (User u : GetAllUsers())
            if (u.username.equals(username) && u.password.equals(password))
                return u;

        return null;
        }

    }
