package org.example;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class StudentManager {
    // ✅ Step 1: private static instance
    private static StudentManager instance;

    // ✅ Step 2: private constructor (prevents "new" from outside)
    private StudentManager() {}

    // ✅ Step 3: static method to get the only instance
    public static StudentManager getInstance() {
        if (instance == null) {
            instance = new StudentManager();
        }
        return instance;
    }

    private List<Student> students = new ArrayList<>();

    public void addStudent(String name, int bangla, int english, int math) {
        students.add(new Student(name, bangla, english, math));
    }

    public List<Student> getAllStudents() {
        return students;
    }

    public String getHighestMarks() {
        int maxBangla = 0, maxEnglish = 0, maxMath = 0;
        for (Student s : students) {
            if (s.getBangla() > maxBangla) maxBangla = s.getBangla();
            if (s.getEnglish() > maxEnglish) maxEnglish = s.getEnglish();
            if (s.getMath() > maxMath) maxMath = s.getMath();
        }
        return "Highest Marks - Bangla: " + maxBangla + ", English: " + maxEnglish + ", Math: " + maxMath;
    }

    public String getTopper() {
        if (students.isEmpty()) return "No students found.";
        Student topper = students.get(0);
        for (Student s : students) {
            if (s.totalMarks() > topper.totalMarks()) {
                topper = s;
            }
        }
        return "Topper: " + topper.getName() + " (" + topper.totalMarks() + " marks)";
    }

    public void saveToFile(String filename) {
        try (FileWriter writer = new FileWriter(filename)) {
            new Gson().toJson(students, writer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadFromFile(String filename) {
        try (FileReader reader = new FileReader(filename)) {
            Type type = new TypeToken<ArrayList<Student>>() {}.getType();
            students = new Gson().fromJson(reader, type);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
