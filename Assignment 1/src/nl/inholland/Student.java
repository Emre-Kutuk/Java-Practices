package nl.inholland;

public class Student
    {

    public String name;
    public boolean present;

    public Student(String name)
        {
        this.name = name;
        }

    public String getName()
        {
        return this.name;
        }

    public boolean getPresence()
        {
        return this.present;
        }
    }
