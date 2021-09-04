package org.techtown.practice1;

public class Homework {
    int _id;
    String deadline;
    String subjectName;
    String homeworkName;
    String alarm_time;

    public Homework(int _id, String deadline, String subjectName, String homeworkName, String alarm_time) {
        this._id = _id;
        this.deadline = deadline;
        this.subjectName = subjectName;
        this.homeworkName = homeworkName;
        this.alarm_time = alarm_time;
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

    public String getAlarm_time() { return alarm_time; }

    public void setAlarm_time(String alarm_time) {
        this.alarm_time = alarm_time;
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