package org.techtown.practice1;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class SecondFragment extends Fragment {
    String text1, text2, text3;
    String deadline, subjectName, homeworkName;
    Button addButton;
    HomeworkAdapter adapter;
    RecyclerView recyclerView;

    Context context;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        this.context = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_second, container, false);

        recyclerView = rootView.findViewById(R.id.recyclerView);

        // 레이아웃 매니저: recyclerView가 보일 기본 형태 설정
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

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

        adapter = new HomeworkAdapter();
        recyclerView.setAdapter(adapter); // recyclerView에 어댑터 설정

        adapter.addItem(new Homework("1", "1", "1"));
        adapter.addItem(new Homework("2", "2", "2"));

        Bundle bundle = getArguments(); // getArguments() 메소드로 번들 받기
        if (bundle != null) {
            // ThirdFragment에서 받아온 값 넣기
            deadline = bundle.getString("deadline");
            text1 = "|" + deadline + " 까지|";

            subjectName = bundle.getString("subjectName");
            text2 = "|" + subjectName + "|";

            homeworkName = bundle.getString("homeworkName");
            text3 = homeworkName;
            adapter.addItem(new Homework(text1, text2, text3));
            adapter.notifyDataSetChanged();
        }
        return rootView;
    }
}