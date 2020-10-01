package nl.inholland;

import java.time.LocalDate;

public class User
    {
    protected int id;
    protected String username;
    protected String password;
    protected String firstName;
    protected String lastName;
    protected LocalDate birthdate;
    protected int type;

    public User()
        {
        }

    public User(String username, String password, String firstName, String lastName, LocalDate birthdate, int type)
        {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthdate = birthdate;
        this.type = type;
        }

    public String getUsername()
        {
        return username;
        }

    public String getPassword()
        {
        return password;
        }

    public String getFirstName()
        {
        return firstName;
        }

    public String getLastName()
        {
        return lastName;
        }

    public LocalDate getBirthdate()
        {
        return birthdate;
        }

    public int getType()
        {
        return type;
        }

    }
