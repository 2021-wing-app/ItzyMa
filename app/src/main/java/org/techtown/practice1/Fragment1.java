package org.techtown.practice1;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Fragment1 extends Fragment {
    private static final String TAG = "Fragment1";

    Button addButton;
    HomeworkAdapter adapter;
    RecyclerView recyclerView;
    ArrayList<Homework> homeworkArrayList;

    Context context;
    OnTabItemSelectedListener listener;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_1, container, false);

        initUI(rootView);

        // 리스트 데이터 로딩
        loadHomeworkListData();

        return rootView;
    }

    private void initUI(ViewGroup rootView) {

        recyclerView = rootView.findViewById(R.id.recyclerView);

        // 레이아웃 매니저: recyclerView가 보일 기본 형태 설정
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        addButton = rootView.findViewById(R.id.addButton); // addButton을 눌렀을 때 id 생성
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Bundle bundle = new Bundle(); // 번들을 통해 값 전달
                bundle.putInt("id", Fragment2.id); //번들에 넘길 값 저장

                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                Fragment2 fragment2 = new Fragment2();
                fragment2.setArguments(bundle); //번들을 프래그먼트2로 보낼 준비
                transaction.replace(R.id.container, fragment2);
                transaction.commit();
                Fragment2.id++; // id 1증가
            }
        });

        // recyclerView에 HomeworkAdapter 객체 셋팅
        adapter = new HomeworkAdapter();
        recyclerView.setAdapter(adapter);  // recyclerView에 어댑터 설정

        adapter.setOnItemClickListener(new OnHomeworkItemClickListener() {
            @Override
            public void onItemClick(HomeworkAdapter.ViewHolder holder, View view, int position) {
                Homework item = adapter.getItem(position);
                int _ID = item.get_ID();

                Log.d(TAG, "아이템 선택됨 : " + item.get_id());

                if (listener != null) {
                    listener.showFragment2(item, _ID);
                }
            }
        });
    }

    /**
     * 리스트 데이터 로딩
     */
    public int loadHomeworkListData() {
        AppConstants.println("loadHomeworkListData called.");

        String sql = "select _id, DEADLINE, SUBJECTNAME, HOMEWORKNAME, ALARM_TIME, ID from " + HomeworkDatabase.TABLE_HOMEWORK;

        int recordCount = -1;
        HomeworkDatabase database = HomeworkDatabase.getInstance(context);
        if (database != null) {
            Cursor outCursor = database.rawQuery(sql);

            recordCount = outCursor.getCount();
            AppConstants.println("record count : " + recordCount + "\n");

            ArrayList<Homework> items = new ArrayList<Homework>();

            for (int i = 0; i < recordCount; i++) {
                outCursor.moveToNext();

                int _id = outCursor.getInt(0);
                String deadline = outCursor.getString(1);
                String subjectName = outCursor.getString(2);
                String homeworkName = outCursor.getString(3);
                String alarm_time = outCursor.getString(4);
                int ID = outCursor.getInt(5);

                AppConstants.println("#" + i + " -> " + _id + ", " + deadline + ", " +
                        subjectName + ", " + homeworkName + ", " + alarm_time);

                items.add(new Homework(_id, deadline, subjectName, homeworkName, alarm_time, ID));
            }

            outCursor.close();

            adapter.setItems(items);
            adapter.notifyDataSetChanged();

        }

        return recordCount;
    }
}