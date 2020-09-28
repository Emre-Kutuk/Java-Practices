package nl.inholland;

import java.time.LocalDate;

public class User
    {
    protected String username;
    protected String password;
    protected String firstName;
    protected String lastName;
    protected LocalDate birthdate;
    protected int type;

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
