package nl.inholland;

import java.time.LocalDate;
import java.io.*;

public class Student extends User
    {
    protected String group;
    protected int gradeJava;
    protected int gradeCsharp;
    protected int gradePython;
    protected int gradePHP;

    public Student()
        {
        }

    public Student(String username, String password, String firstName, String lastName, LocalDate birthdate, int type, String group)
        {
        super(username, password, firstName, lastName, birthdate, type);
        this.group = group;

        }

    public int getGradeJava()
        {
        return gradeJava;
        }

    public int getGradeCsharp()
        {
        return gradeCsharp;
        }

    public int getGradePython()
        {
        return gradePython;
        }

    public int getGradePHP()
        {
        return gradePHP;
        }

    public String getGroup()
        {
        return group;
        }

    public void SaveStudent() throws IOException
        {
        FileWriter writer = new FileWriter("users.txt", true);
        writer.write(System.getProperty("line.separator"));
        writer.append(this.username + "," + this.password + "," + this.firstName + "," + this.lastName + "," + this.birthdate + "," + this.type + "," + this.group);
        writer.close();

        FileWriter gradeWriter = new FileWriter("grades.txt", true);
        gradeWriter.write(System.getProperty("line.separator"));
        gradeWriter.append(this.username+","+this.password+",0,0,0,0");
        gradeWriter.close();
        }

    public void GetGrades() throws IOException
        {
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("grades.txt")));

        String line;

        while ((line = reader.readLine()) != null)
            {
            String[] fields = line.split(",");
            if (this.getUsername().equals(fields[0]) && this.getPassword().equals(fields[1]))
                {
                    this.gradeJava = Integer.parseInt(fields[2]);
                    this.gradeCsharp = Integer.parseInt(fields[3]);
                    this.gradePython = Integer.parseInt(fields[4]);
                    this.gradePHP = Integer.parseInt(fields[5]);
                }
            }
        }

    }


