package org.techtown.practice1;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class HomeworkAdapter extends RecyclerView.Adapter<HomeworkAdapter.ViewHolder> implements OnHomeworkItemClickListener {

    // Homework 객체를 담는 ArrayList 자료형의 items 변수 만듦
    ArrayList<Homework> items = new ArrayList<Homework>();

    OnHomeworkItemClickListener listener;
    int layoutType = 0;

    @NonNull
    @Override
    // 뷰홀더 객체가 만들어질 때 자동 호출
    // homework_item.xml을 이용해 뷰 객체를 만들고 이를 새로운 뷰홀더 객체에 담아 반환
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        // 인플레이션을 통해 뷰 객체 만듦
        View itemView = inflater.inflate(R.layout.homework_item, viewGroup, false);

        // 뷰홀더 객체를 생성하며 뷰 객체(itemView)를 전달하고, 뷰홀더 객체 반환
        return new ViewHolder(itemView, this, layoutType);
    }

    @Override
    // 뷰홀더 객체가 재사용될 때 자동으로 호출
    // (메모리 절약을 위해 사용자가 스크롤하여 보이지 않게 된 뷰 객체를 새로 보이는 쪽에 재사용)
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Homework item = items.get(position);
        holder.setItem(item);
        //holder.setLayoutType(layoutType);
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

    public void setOnItemClickListener(OnHomeworkItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onItemClick(ViewHolder holder, View view, int position) {
        if (listener != null) {
            listener.onItemClick(holder, view, position);
        }
    }

    public void switchLayout(int position) {
        layoutType = position;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        //LinearLayout layout1;
        //LinearLayout layout2;

        TextView textView;
        TextView textView2;
        TextView textView3;

        public ViewHolder(View itemView, final OnHomeworkItemClickListener listener, int layoutType) {
            super(itemView);

            //layout1 = itemView.findViewById(R.id.layout1);
            //layout2 = itemView.findViewById(R.id.layout2);

            textView = itemView.findViewById(R.id.textView);
            textView2 = itemView.findViewById(R.id.textView2);
            textView3 = itemView.findViewById(R.id.textView3);

            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();

                    if (listener != null) {
                        listener.onItemClick(ViewHolder.this, v, position);
                    }
                }
            });

            //setLayoutType(layoutType);
        }

        public void setItem(Homework item) {

            textView.setText(item.getDeadline());
            textView2.setText(item.getSubjectName());
            textView3.setText(item.getHomeworkName());
        }

        /*
        public void setLayoutType(int layoutType) {
            if (layoutType == 0) {
                layout1.setVisibility(View.VISIBLE);
                layout2.setVisibility(View.GONE);
            } else if (layoutType == 1) {
                layout1.setVisibility(View.GONE);
                layout2.setVisibility(View.VISIBLE);
            }
        }

         */
    }
}
