package org.techtown.practice1;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class SecondFragment extends Fragment {
    TextView textView1, textView2, textView3;
    String deadline, subjectName, homeworkName;
    Button addButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_second, container, false);

        addButton = rootView.findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                ThirdFragment fragment3 = new ThirdFragment();
                transaction.replace(R.id.container, fragment3);
                transaction.commit();
            }
        });

        // 정보 받기
        textView1 = rootView.findViewById(R.id.textView1);
        textView2 = rootView.findViewById(R.id.textView2);
        textView3 = rootView.findViewById(R.id.textView3);

        Bundle bundle = getArguments(); // getArguments() 메소드로 번들 받기
        if (bundle != null) {
            deadline = bundle.getString("deadline");  // ThirdFragment에서 받아온 값 넣기
            textView1.setText("|" + deadline + " 까지|");

            subjectName = bundle.getString("subjectName");
            textView2.setText("|" + subjectName + "|");

            homeworkName = bundle.getString("homeworkName");
            textView3.setText(homeworkName);
        }

        return rootView;
    }
}