package org.techtown.practice1;

public class Homework {
    String deadline;
    String subjectName;
    String homeworkName;

    public Homework(String deadline, String subjectName, String homeworkName) {
        this.deadline = deadline;
        this.subjectName = subjectName;
        this.homeworkName = homeworkName;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getHomeworkName() {
        return homeworkName;
    }

    public void setHomeworkName(String homeworkName) {
        this.homeworkName = homeworkName;
    }
}
