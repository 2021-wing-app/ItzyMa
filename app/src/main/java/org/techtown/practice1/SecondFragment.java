package org.techtown.practice1;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class SecondFragment extends Fragment {
    View view;
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
                MainActivity activity = (MainActivity) getActivity();
                activity.changeFragment(2);
            }
        });

        textView2 = view.findViewById(R.id.textView2);
        if (getArguments() != null) {
            subjectName = getArguments().getString("subjectName");  // ThirdFragment에서 받아온 값 넣기
            textView2.setText(subjectName);
        }

        return rootView;
    }
}