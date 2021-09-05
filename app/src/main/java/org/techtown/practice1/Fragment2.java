package org.techtown.practice1;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Fragment2 extends Fragment {
    private static final String TAG = "Fragment2";
    int mMode = AppConstants.MODE_INSERT;

    Context context;
    OnTabItemSelectedListener listener;

    EditText editText1, editText2;  // 과목명과 과제명을 입력하는 editText
    Button calendarButton, clockButton, addHomework, delete, close;
    CheckBox check1, check2, check3;
    int checkBoxChecker1, checkBoxChecker2, checkBoxChecker3;
    int alarmHour = 0, alarmMinute = 0;

    Homework item;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        this.context = context;

        if (context instanceof OnTabItemSelectedListener) {
            listener = (OnTabItemSelectedListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();

        if (context != null) {
            context = null;
            listener = null;
        }
    }

    Calendar calendar = Calendar.getInstance();
    // default 값은 오늘 날짜로 설정
    DatePickerDialog.OnDateSetListener setDate = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            changeDateFormat(); // 날짜 형식 변환하는 함수
        }
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // 최상위 레이아웃(rootView) 선언
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_2, container, false);

        initUI(rootView);

        applyItem();

        return rootView;
    }

    private void initUI(ViewGroup rootView) {
        editText1 = rootView.findViewById(R.id.editText1);
        editText2 = rootView.findViewById(R.id.editText2);
        calendarButton = rootView.findViewById(R.id.calendarButton);
        addHomework = rootView.findViewById(R.id.addHomework);
        delete = rootView.findViewById(R.id.delete);
        clockButton = rootView.findViewById(R.id.clockButton);

        Date currentTime = Calendar.getInstance().getTime();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            calendarButton.setText(new SimpleDateFormat("YYYY-MM-dd", Locale.getDefault()).format(currentTime));
        }

        calendarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(getContext(), setDate, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        clockButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), android.R.style.Theme_Holo_Light_Dialog,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                clockButton.setText(hourOfDay + ":" + minute + ":00");
                            }
                        }, alarmHour, alarmMinute, false);
                timePickerDialog.show();
            }
        });

        check1 = rootView.findViewById(R.id.check1);
        check1.setOnClickListener(new CheckBox.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((CheckBox)v).isChecked()) {
                    // TODO : CheckBox is checked.
                    checkBoxChecker1 = 1;
                } else {
                    // TODO : CheckBox is unchecked.
                    checkBoxChecker1 = 0;
                }
            }
        });

        check2 = rootView.findViewById(R.id.check2);
        check2.setOnClickListener(new CheckBox.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((CheckBox)v).isChecked()) {
                    // TODO : CheckBox is checked.
                    checkBoxChecker2 = 1;
                } else {
                    // TODO : CheckBox is unchecked.
                    checkBoxChecker2 = 0;
                }
            }
        });

        check3 = rootView.findViewById(R.id.check3);
        check3.setOnClickListener(new CheckBox.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((CheckBox)v).isChecked()) {
                    // TODO : CheckBox is checked.
                    checkBoxChecker3 = 1;
                } else {
                    // TODO : CheckBox is unchecked.
                    checkBoxChecker3 = 0;
                }
            }
        });



        // 과제 추가 버튼
        addHomework.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mMode == AppConstants.MODE_INSERT) {
                    saveNote();
                } else if(mMode == AppConstants.MODE_MODIFY) {
                    modifyNote();
                }

                // 화면 전환
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                Fragment1 fragment1 = new Fragment1();
                transaction.replace(R.id.container, fragment1);
                transaction.commit();
            }
        });

        // 삭제 버튼
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteNote();

                // 화면 전환
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                Fragment1 fragment1 = new Fragment1();
                transaction.replace(R.id.container, fragment1);
                transaction.commit();
            }
        });

        // 닫기 버튼
        close = rootView.findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 화면 전환
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                Fragment1 fragment1 = new Fragment1();
                transaction.replace(R.id.container, fragment1);
                transaction.commit();
            }
        });
    }

    public void changeDateFormat() {
        String format = "YYYY/MM/dd";
        SimpleDateFormat simpleDateFormat = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            simpleDateFormat = new SimpleDateFormat(format, Locale.KOREA);
        }
        calendarButton.setText(simpleDateFormat.format(calendar.getTime()));
    }

    /*

    String subjectName = editText1.getText().toString();
                String homeworkName = editText2.getText().toString();
                String deadline = calendarButton.getText().toString();
                String deadline_time = clockButton.getText().toString();


                if (subjectName.length() == 0) {
                    Toast.makeText(getContext(), "과목명을 다시 입력해주세요", Toast.LENGTH_LONG).show();
                }
                else if (homeworkName.length() == 0) {
                    Toast.makeText(getContext(), "과제명을 다시 입력해주세요", Toast.LENGTH_LONG).show();
                }

                else {
        Toast.makeText(getContext(), "과제 등록 완료", Toast.LENGTH_LONG).show();
        //org.techtown.practice1.SecondFragment fragment2 = new org.techtown.practice1.SecondFragment();  // SecondFragment 선언

        // 임시 조치 (수정 요함)
        String alarm_time = "2021-09-05 01:35:00";

        // 데이터 베이스에 레코드 삽입
        onDatabaseCallback.insert(deadline, subjectName, homeworkName, alarm_time);

    }

     */


    /**
     * 데이터베이스 레코드 추가
     */
    private void saveNote() {
        String subjectName = editText1.getText().toString();
        String homeworkName = editText2.getText().toString();
        String deadline = calendarButton.getText().toString();
        String deadline_time = clockButton.getText().toString();
        String alarm_time = "2021-09-06 10:08:00";

        if (subjectName.length() == 0) {
            Toast.makeText(getContext(), "과목명을 다시 입력해주세요", Toast.LENGTH_LONG).show();
        }
        else if (homeworkName.length() == 0) {
            Toast.makeText(getContext(), "과제명을 다시 입력해주세요", Toast.LENGTH_LONG).show();
        }

        else {
            Toast.makeText(getContext(), "과제 등록 완료", Toast.LENGTH_SHORT).show();

            String sql = "insert into " + HomeworkDatabase.TABLE_HOMEWORK +
                    "(DEADLINE, SUBJECTNAME, HOMEWORKNAME, ALARM_TIME) values(" +
                    "'"+ deadline + "', " +
                    "'"+ subjectName + "', " +
                    "'"+ homeworkName + "', " +
                    "'"+ alarm_time + "')";

            Log.d(TAG, "sql : " + sql);
            HomeworkDatabase database = HomeworkDatabase.getInstance(context);
            database.execSQL(sql);
        }
    }

    /**
     * 데이터베이스 레코드 수정
     */
    private void modifyNote() {
        if (item != null) {
            String subjectName = editText1.getText().toString();
            String homeworkName = editText2.getText().toString();

            // update note
            String sql = "update " + HomeworkDatabase.TABLE_HOMEWORK +
                    " set " +
                    "   DEADLINE = '" + "" + "'" +
                    "   ,SUBJECTNAME = '" + subjectName + "'" +
                    "   ,HOMEWORKNAME = '" + homeworkName + "'" +
                    "   ,ALARM_TIME = '" + "" + "'" +
                    " where " +
                    "   _id = " + item._id;

            Log.d(TAG, "sql : " + sql);
            HomeworkDatabase database = HomeworkDatabase.getInstance(context);
            database.execSQL(sql);
        }
    }

    /**
     * 레코드 삭제
     */
    private void deleteNote() {

        Toast.makeText(getContext(), "삭제 완료", Toast.LENGTH_SHORT);

        if (item != null) {
            // delete note
            String sql = "delete from " + HomeworkDatabase.TABLE_HOMEWORK +
                    " where " +
                    "   _id = " + item._id;

            Log.d(TAG, "sql : " + sql);
            HomeworkDatabase database = HomeworkDatabase.getInstance(context);
            database.execSQL(sql);
        }
    }

    public void setItem(Homework item) {
        this.item = item;
    }

    public void applyItem() {
        AppConstants.println("applyItem called.");

        if (item != null) {
            mMode = AppConstants.MODE_MODIFY;

            setDeadline(item.deadline);
            setHomeworkName(item.homeworkName);
            setSubjectName(item.subjectName);

        } else {
            mMode = AppConstants.MODE_INSERT;

            setHomeworkName("");
            setSubjectName("");
        }
    }

    public void setDeadline(String deadline) {
        AppConstants.println("setDeadline called : " + deadline);

        if (deadline != null) {
            calendarButton.setText(deadline);
        }
    }

    public void setSubjectName(String subjectName) {
        AppConstants.println("setSubjectName called : " + subjectName);

        if (subjectName != null) {
            editText1.setText(subjectName);
        }
    }

    public void setHomeworkName(String homeworkName) {
        AppConstants.println("setHomeworkName called : " + homeworkName);

        if (homeworkName != null) {
            editText2.setText(homeworkName);
        }
    }
}