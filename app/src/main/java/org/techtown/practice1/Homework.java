package org.techtown.practice1;

public class Homework {
    int _id;
    String deadline;
    String subjectName;
    String homeworkName;

    public Homework(int _id, String deadline, String subjectName, String homeworkName) {
        this._id = _id;
        this.deadline = deadline;
        this.subjectName = subjectName;
        this.homeworkName = homeworkName;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
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

    @Override
    public String toString() {
        return "Homework{" +
                " deadline='" + deadline + '\'' +
                ", subjectName='" + subjectName + '\'' +
                ", homeworkName='" + homeworkName + '\'' +
                '}';
    }
}
