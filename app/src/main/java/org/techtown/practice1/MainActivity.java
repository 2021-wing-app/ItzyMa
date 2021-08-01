package org.techtown.practice1;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;

public class MainActivity extends FragmentActivity {

    // 객체 선언
    FirstFragment firstFragment;
    SecondFragment secondFragment;
    ThirdFragment thirdFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 프래그먼트 초기화
        // firstFragment는 activity_main.xml에 추가 되었으므로 id를 사용해 찾아야 함
        firstFragment = (FirstFragment) getSupportFragmentManager().findFragmentById(R.id.mainFragment);
        // secondFragment와 thirdFragmen는 추가되지 않았으므로 new 연산자로 새로운 객체 만들어 변수에 할당
        secondFragment = new SecondFragment();
        thirdFragment = new ThirdFragment();
    }

    public void changeFragment(int index){
        if (index == 1) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container, secondFragment).commit();
        }
        else if (index == 2) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container, thirdFragment).commit();
        }
    }
}