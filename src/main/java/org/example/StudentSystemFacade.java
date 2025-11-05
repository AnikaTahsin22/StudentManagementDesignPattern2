package org.example;

public class StudentSystemFacade {
    private StudentManager manager = StudentManager.getInstance();

    public void addNewStudent(String name, int bangla, int english, int math) {
        manager.addStudent(name, bangla, english, math);
    }

    public void saveData() {
        manager.saveToFile("students.json");
    }

    public void loadData() {
        manager.loadFromFile("students.json");
    }
}
