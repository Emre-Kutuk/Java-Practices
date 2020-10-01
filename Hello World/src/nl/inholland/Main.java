package nl.inholland;

import java.util.Scanner;

public class Main
    {

    public static void main(String[] args)
        {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Please enter the size of your group and press [ENTER]");
        int groupSize = Integer.parseInt(scanner.nextLine());
        System.out.format("Group size: %s\n", groupSize);
        Student[] students = new Student[groupSize];

        for (int i = 1; i <= students.length; i++)
            {
            System.out.format("Please enter the name of student #%d and press [ENTER]\n", i);
            students[i - 1] = new Student(scanner.nextLine());
            }

        System.out.println();

        for (int i = 1; i <= students.length; i++)
            {
            System.out.format("Student #%d: %s\n", i, students[i - 1].getName());
            }

        System.out.println();

        for (int i = 1; i <= students.length; i++)
            {
            System.out.format("Is student #%d: %s present? [Y/N + ENTER]\n", i, students[i - 1].getName());
            String answer = scanner.nextLine().toLowerCase();

            if (answer.equals("y"))
                students[i - 1].present = true;
            else if (answer.equals("n"))
                students[i - 1].present = false;
            }

        System.out.println();

        for (int i = 1; i <= students.length; i++)
            {
            System.out.format("Student #%d: %s          | Present: %s\n", i, students[i - 1].getName(), students[i - 1].getPresence());
            }
        }
    }
