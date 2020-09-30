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
            System.out.println("Welcome " + user.username);
            if (user.type == 0)
                {
                ShowStudentInterface();
                } else if (user.type == 1)
                {
                //SHOW TEACHER
                } else if (user.type == 2)
                {
                //SHOW ADMIN
                }

            } else
            System.out.println("Wrong credentials, please try again!");


        }

    public void ShowStudentInterface() throws IOException
        {
        Scanner scanner = new Scanner(System.in);
        while (true)
            {
            System.out.print("\nS. Display Students | T. Display Teachers | X. Exit \nPlease enter a choice: ");
            String userInput = scanner.next();

            if (userInput.equalsIgnoreCase("s"))
                {
                System.out.println("\nLIST OF STUDENTS" +
                        "\n\nFirstName          LastName          Birthdate          Age          Group\n" +
                        "*********          ********          *********          ***          *****\n");
                for (Student s : GetAllStudents())
                    {
                    System.out.format("%s              %s               %s         %s          %s\n",
                            s.getFirstName(), s.getLastName(), s.getBirthdate(), LocalDate.now().getYear() - s.getBirthdate().getYear(), s.getGroup());
                    }


                } else if (userInput.equalsIgnoreCase("t"))
                {
                //DISPLAY TEACHERS
                } else if (userInput.equalsIgnoreCase("x"))
                {
                System.out.println("\nExiting application...");
                break;
                }
            }
        }

    public List<Student> GetAllStudents() throws IOException
        {
        File users = new File("users.txt");
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(users)));

        List<Student> allStudents = new ArrayList<>();
        for (User u : GetAllUsers())
            {
            if (u.type == 0)
                {
                allStudents.add((Student) u);
                }
            }

        return allStudents;
        }

    public List<User> GetAllUsers() throws IOException
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
            //teacher;
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
