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

        if(checkUsername(username) != null)
            {
            System.out.println("This username exists " + checkUsername(username).username);
            }

        System.out.print("Enter password: ");
        String password = scanner.nextLine();



        }

    public List<User> getAllUsers() throws IOException
        {
        File users = new File("users.txt");
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(users)));

        List<User> allUsers = new ArrayList<>();
        String line;

        while ((line = reader.readLine()) != null)
            {
            allUsers.add(getUserFromString(line));
            }

        return allUsers;
        }

    public User getUserFromString(String line)
        {
        User user = new User();

        String[] userInfo = line.split(",");
        user.username = userInfo[0];
        user.password = userInfo[1];
        user.firstName = userInfo[2];
        user.lastName = userInfo[3];
        user.birthdate = LocalDate.parse(userInfo[4]);
        user.type = UserType.values()[Integer.parseInt(userInfo[5])];

        return user;
        }

    public User checkUsername(String username) throws IOException
        {
        for (User u : getAllUsers())
            {
            if (u.username == username)
                {
                return u;
                }
            }
        return null;
        }

    }
