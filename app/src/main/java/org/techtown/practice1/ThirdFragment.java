package org.techtown.practice1;

import android.app.DatePickerDialog;
import android.os.Build;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ThirdFragment extends Fragment {

    EditText editText1, editText2;
    Button calendarButton, addHomework;
    CheckBox check1, check2, check3;
    int checkBoxChecker = 0;

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
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_third, container, false);

        editText1 = rootView.findViewById(R.id.editText1);
        editText2 = rootView.findViewById(R.id.editText2);
        calendarButton = rootView.findViewById(R.id.calendarButton);
        addHomework = rootView.findViewById(R.id.addHomework);

        Date currentTime = Calendar.getInstance().getTime();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            calendarButton.setText(new SimpleDateFormat("YYYY/MM/dd", Locale.getDefault()).format(currentTime));
        }

        calendarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(getContext(), setDate, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        check1 = rootView.findViewById(R.id.check1);
        check1.setOnClickListener(new CheckBox.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((CheckBox)v).isChecked()) {
                    // TODO : CheckBox is checked.
                    checkBoxChecker = 1;
                } else {
                    // TODO : CheckBox is unchecked.
                }
            }
        });

        check2 = rootView.findViewById(R.id.check2);
        check2.setOnClickListener(new CheckBox.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((CheckBox)v).isChecked()) {
                    // TODO : CheckBox is checked.
                    checkBoxChecker = 1;
                } else {
                    // TODO : CheckBox is unchecked.
                }
            }
        });

        check3 = rootView.findViewById(R.id.check3);
        check3.setOnClickListener(new CheckBox.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((CheckBox)v).isChecked()) {
                    // TODO : CheckBox is checked.
                    checkBoxChecker = 1;
                } else {
                    // TODO : CheckBox is unchecked.
                }
            }
        });

        addHomework.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String subjectName = editText1.getText().toString();
                String homeworkName = editText2.getText().toString();

                if (subjectName.length() == 0) {
                    Toast.makeText(getContext(), "과목명을 다시 입력해주세요", Toast.LENGTH_LONG).show();
                }
                else if (homeworkName.length() == 0) {
                    Toast.makeText(getContext(), "과제명을 다시 입력해주세요", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(getContext(), "과제 등록 완료", Toast.LENGTH_LONG).show();
                    Bundle bundle = new Bundle();  // bundle으로 값 전달
                    bundle.putString("subjectName", editText1.getText().toString()); // bundle에 넘길 값 저장
                    SecondFragment fragment2 = new SecondFragment();  // SecondFragment 선언
                    fragment2.setArguments(bundle);  // bundle을 SecondFragment로 보낼 준비
                    MainActivity activity = (MainActivity)getActivity();
                    activity.changeFragment(1);
                }
            }
        });
        return rootView;
    }

    public void changeDateFormat() {
        String format = "YYYY/MM/dd";
        SimpleDateFormat simpleDateFormat = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            simpleDateFormat = new SimpleDateFormat(format, Locale.KOREA);
        }
        calendarButton.setText(simpleDateFormat.format(calendar.getTime()));
    }
}