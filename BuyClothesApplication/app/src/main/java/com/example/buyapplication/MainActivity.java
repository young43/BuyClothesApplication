package com.example.buyapplication;import androidx.annotation.NonNull;import androidx.appcompat.app.AppCompatActivity;import androidx.fragment.app.Fragment;import androidx.fragment.app.FragmentManager;import androidx.fragment.app.FragmentTransaction;import android.os.Bundle;import android.view.MenuItem;import com.google.android.material.bottomnavigation.BottomNavigationView;public class MainActivity extends AppCompatActivity{    private ProductFragment mainFragment;    private BottomNavigationView bottomNavigationView;    private FragmentManager fm;    private FragmentTransaction ft;    private Fragment frag1;    private Fragment frag2;    private Fragment frag3;    private int flag_purchase;    @Override    protected void onCreate(Bundle savedInstanceState) {        super.onCreate(savedInstanceState);        setContentView(R.layout.activity_main);        bottomNavigationView = findViewById(R.id.bottomNavi);        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {            @Override            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {                switch (menuItem.getItemId()) {                    case R.id.action_main:                        setFrag(0, null);                        break;                    case R.id.action_cart:                        setFrag(1,null);                        break;                    case R.id.action_purchase:                        setFrag(2, null);                        break;                }                return true;            }        });        frag1 = new ProductFragment();        frag2 = new CartFragment();        frag3 = new BuyFragment();        setFrag(0, null); // 첫 프래그먼트 화면 지정    }    public void setFrag(int n, Fragment frag) {        fm = getSupportFragmentManager();        ft = fm.beginTransaction();        frag1 = new ProductFragment();        frag2 = new CartFragment();        frag3 = new BuyFragment();        switch (n) {            case 0: // 메인                if(frag != null)                    frag1 = frag;                bottomNavigationView.getMenu().findItem(R.id.action_main).setChecked(true);                ft.replace(R.id.Main_Frame, frag1);                ft.commit();                break;            case 1: // 장바구니                if(frag != null)                    frag2 = frag;                bottomNavigationView.getMenu().findItem(R.id.action_cart).setChecked(true);                ft.replace(R.id.Main_Frame, frag2);                ft.commit();                break;            case 2: // 구매하기                if(frag != null)                    frag3 = frag;                bottomNavigationView.getMenu().findItem(R.id.action_purchase).setChecked(true);                ft.replace(R.id.Main_Frame, frag3);                ft.commit();                break;        }    }}