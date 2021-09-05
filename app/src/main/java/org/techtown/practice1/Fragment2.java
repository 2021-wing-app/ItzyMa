package org.techtown.practice1;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.NotificationManager;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.text.format.DateFormat;
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

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import org.techtown.practice1.MainActivity;

public class Fragment2 extends Fragment {
    private static final String TAG = "Fragment2";
    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm";
    int mMode = AppConstants.MODE_INSERT;

    Context context;
    OnTabItemSelectedListener listener;

    EditText editText1, editText2;  // 과목명과 과제명을 입력하는 editText
    Button calendarButton, clockButton;
    Button addHomework, delete, close;  // 레코드 추가, 삭제, 그리고 창 닫기 버튼
    CheckBox check1, check2, check3;  // 과제 알림 시간 설정 체크 박스
    int checkBoxChecker1, checkBoxChecker2, checkBoxChecker3;

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

    TimePickerDialog.OnTimeSetListener setTime = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
            calendar.set(Calendar.MINUTE, minute);
            changeTimeFormat(); // 시간 형식 변환하는 함수
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
            calendarButton.setText(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(currentTime));
            clockButton.setText(new SimpleDateFormat("HH:mm", Locale.getDefault()).format(currentTime));
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
                new TimePickerDialog(getContext(), setTime, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), DateFormat.is24HourFormat(getContext())).show();
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
                ((MainActivity)MainActivity.Context).removeNotification();

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

    // 날짜 형식 변경 함수
    public void changeDateFormat() {
        String format = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            simpleDateFormat = new SimpleDateFormat(format, Locale.KOREA);
        }
        calendarButton.setText(simpleDateFormat.format(calendar.getTime()));
    }

    // 시간 형식 변경 함수
    public void changeTimeFormat() {
        String format = "HH:mm";
        SimpleDateFormat simpleDateFormat = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            simpleDateFormat = new SimpleDateFormat(format, Locale.KOREA);
        }
        clockButton.setText(simpleDateFormat.format(calendar.getTime()));
    }

    /**
     * 데이터베이스 레코드 추가
     */
    private void saveNote() {
        String subjectName = editText1.getText().toString();
        String homeworkName = editText2.getText().toString();
        String deadline = calendarButton.getText().toString();
        String deadline_time = clockButton.getText().toString();
        String alarm_time = deadline + " " + deadline_time;  // 형식: "2021-09-06 10:08"

        if (subjectName.length() == 0) {
            Toast.makeText(getContext(), "과목명을 다시 입력해주세요", Toast.LENGTH_LONG).show();
        }
        else if (homeworkName.length() == 0) {
            Toast.makeText(getContext(), "과제명을 다시 입력해주세요", Toast.LENGTH_LONG).show();
        }

        else {
            Toast.makeText(getContext(), "과제 등록 완료", Toast.LENGTH_SHORT).show();

            // date를 밀리초로 변환
            long longDate = DateToMill(alarm_time);


            // 알람 설정
            //Toast.makeText(getContext(), alarm_time, Toast.LENGTH_SHORT).show();
            if (checkBoxChecker1 == 1) {  // 15minute before
                ((MainActivity) getActivity()).setAlarm(longTimeToDatetimeAsString(longDate - 900000));
            }
            if (checkBoxChecker2 == 1) {  // 1hour before
                ((MainActivity) getActivity()).setAlarm(longTimeToDatetimeAsString(longDate - 3600000));
            }
            if (checkBoxChecker3 == 1) {  // 1day before
                ((MainActivity) getActivity()).setAlarm(longTimeToDatetimeAsString(longDate - 86400000));
            }

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

            //setDeadline("날짜");
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

    // long형 시간을 String으로 변환.
    public static String longTimeToDatetimeAsString(long resultTime)
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        String formatTime = dateFormat.format(resultTime);
        return formatTime;
    }

    // String을 long형 시간으로 바꿈
    public long DateToMill(String date) {
        String pattern = DATE_FORMAT;
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        Date trans_date = null;
        try {
            trans_date = formatter.parse(date);
        } catch (ParseException e) {
            // TODO Auto-generated
            e.printStackTrace();
        }
        return trans_date.getTime();
    }

}