package org.techtown.practice1;

import java.util.ArrayList;

// 프래그먼트에서 새로운 레코드를 삽입하고, 어댑터에 모든 아이템을 넣기 위해 만든 인터페이스
public interface OnDatabaseCallback {
    public void insert(String deadline, String subjectName, String homeworkName, String deadline_time, int alarm_time);
    public ArrayList<Homework> selectAll();
}
