package org.techtown.practice1;

public class Homework {
    int _id;
    String deadline;
    String subjectName;
    String homeworkName;
    String alarm_time;
    int ID; // 이건 addButton을 통해 생성된 아이디를 의미

    public Homework(int _id, String deadline, String subjectName, String homeworkName, String alarm_time, int ID) {
        this._id = _id;
        this.deadline = deadline;
        this.subjectName = subjectName;
        this.homeworkName = homeworkName;
        this.alarm_time = alarm_time;
        this.ID = ID;
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

    public int get_ID() { return ID; }

    public void set_ID(int ID) { this.ID = ID; }

    @Override
    public String toString() {
        return "Homework{" +
                " deadline='" + deadline  + '\'' +
                ", subjectName='" + subjectName + '\'' +
                ", homeworkName='" + homeworkName + '\'' +
                '}';
    }
}