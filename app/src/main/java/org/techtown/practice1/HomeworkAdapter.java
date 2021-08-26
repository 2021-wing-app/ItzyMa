package org.techtown.practice1;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class HomeworkAdapter extends RecyclerView.Adapter<HomeworkAdapter.ViewHolder> {
    LayoutInflater inflater;
    // Homework 객체를 담는 ArrayList 자료형의 items 변수 만듦
    ArrayList<Homework> items = new ArrayList<Homework>();

    @NonNull
    @Override
    // 뷰홀더 객체가 만들어질 때 자동 호출
    // homework_item.xml을 이용해 뷰 객체를 만들고 이를 새로운 뷰홀더 객체에 담아 반환
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        inflater = LayoutInflater.from(viewGroup.getContext());
        // 인플레이션을 통해 뷰 객체 만듦
        View itemView = inflater.inflate(R.layout.homework_item, viewGroup, false);

        // 뷰홀더 객체를 생성하며 뷰 객체(itemView)를 전달하고, 뷰홀더 객체 반환
        ViewHolder holder = new ViewHolder(itemView);
        return holder;
    }

    @Override
    // 뷰홀더 객체가 재사용될 때 자동으로 호출
    // (메모리 절약을 위해 사용자가 스크롤하여 보이지 않게 된 뷰 객체를 새로 보이는 쪽에 재사용)
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int position) {
        Homework item = items.get(position);
        viewHolder.setItem(item);
    }

    // recyclerView에서 어댑터가 관리하는 아이템의 개수를 반환
    @Override
    public int getItemCount() { return items.size(); }

    // arrayList에 객체 추가
    public void addItem(Homework item) {
        items.add(item);
    }

    // arrayList 전체를 설정
    public void setItems(ArrayList<Homework> items) {
        this.items = items;
    }

    // arrayList에서 position 위치에 있는 객체를 반환
    public Homework getItem(int position) {
        return items.get(position);
    }

    // 특정 위치의 객체 설정
    public void setItem(int position, Homework item) {
        items.set(position, item);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        TextView textView2;
        TextView textView3;

        public ViewHolder(View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.textView);
            textView2 = itemView.findViewById(R.id.textView2);
            textView3 = itemView.findViewById(R.id.textView3);
        }

        public void setItem(Homework item) {
            textView.setText(item.getDeadline());
            textView2.setText(item.getSubjectName());
            textView3.setText(item.getHomeworkName());
        }
    }
}
