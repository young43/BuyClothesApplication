package com.example.buyapplication;

import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;


public class CartFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    // 장바구니 리스트
    private static ArrayList<HashMap<String, String>> productList = new ArrayList<>();

    private ListView listview ;
    private ListViewAdapter adapter;

    public CartFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static CartFragment newInstance(String param1, String param2) {
        CartFragment fragment = new CartFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_cart, container, false);

        if(getArguments() != null){
            String product_title = getArguments().getString("title", "test");
            String product_price = getArguments().getString("price", "test");
            int imgSrc = getProductImagesrc(product_title);

            HashMap<String, String> map = new HashMap<>();
            map.put("title", product_title);
            map.put("price", product_price);
            map.put("img", imgSrc+"");

            productList.add(map);
        }

        // Adapter 생성
        adapter = new ListViewAdapter(getActivity(), android.R.layout.simple_list_item_multiple_choice);

        // 리스트뷰 참조 및 Adapter달기
        listview = (ListView) rootView.findViewById(R.id.list_cart);
        listview.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        listview.setAdapter(adapter);

        for(HashMap<String, String> m : productList){
            String title = m.get("title");
            String price = m.get("price");
            int src = Integer.parseInt(m.get("img"));

            // 첫 번째 아이템 추가.
            adapter.addItem(ContextCompat.getDrawable(getActivity(), src), title, price) ;
        }
        setListViewHeightBasedOnChildren(listview);     // 스크롤뷰 크기 조정

        // list item 전체선택 적용
        CheckBox allCheck = (CheckBox) rootView.findViewById(R.id.checkbox_all);
        allCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                    adapter.checkAllTrue();
                else
                    adapter.checkAllFalse();

                updateListView();
            }
        });

        Button deleteButton = (Button) rootView.findViewById(R.id.button_delete);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = adapter.getCount();

                for (int i = count-1; i >= 0; i--) {
                    ListViewItem item = (ListViewItem) adapter.getItem(i);
                    if(item.isChecked()){
                        removeListItem(i);
                    }
                }
                updateListView();
            }
        });

        Button purchaseButton = (Button) rootView.findViewById(R.id.button_purchase);
        if(productList.size() == 0){
            purchaseButton.setVisibility(View.GONE);
        }

        purchaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<HashMap<String, Object>> mapList = new ArrayList<>();
                HashMap<String, HashMap<String, Object>> map = new HashMap<>();


                int count = adapter.getCount();
                boolean flag_check = false;

                for (int i=0; i<count; i++) {
                    ListViewItem item = (ListViewItem) adapter.getItem(i);
                    if(item.isChecked()){
                        flag_check = true;

                        // 중복값을 제거하고, 각 상품의 갯수를 계산한다.
                        if(!map.containsKey(item.getTitle())){
                            HashMap<String, Object> tmp = new HashMap<>();
                            tmp.put("cnt", 1);
                            tmp.put("price", item.getDesc());

                            map.put(item.getTitle(), tmp);

                        }else{
                            HashMap<String, Object> tmp = map.get(item.getTitle());
                            int cnt = (Integer) tmp.get("cnt");
                            tmp.put("cnt", cnt+1);

                            map.put(item.getTitle(), tmp);
                        }

                    }
                }

                for (String key : map.keySet()) {
                    HashMap<String, Object> mapData = new HashMap<>();
                    mapData.put("title", key);
                    mapData.put("cnt", map.get(key).get("cnt"));
                    mapData.put("price", map.get(key).get("price"));

                    mapList.add(mapData);
                }

                if(flag_check && map.size() > 0){
                    Fragment fragment = new BuyFragment();
                    Bundle args = new Bundle();
                    args.putSerializable("data", mapList);
                    fragment.setArguments(args);

                    MainActivity activity = (MainActivity) getActivity();
                    activity.setFrag(2, fragment);
                }

            }
        });


        return rootView;
    }


    public void removeListItem(int index){
        productList.remove(index);
        adapter.removeItem(index);
    }

    public void updateListView(){
        adapter.notifyDataSetChanged();
        listview.setAdapter(adapter);
        setListViewHeightBasedOnChildren(listview);
    }

    public int getProductImagesrc(String title){
        int res = R.drawable.product1;
        switch (title){
            case "키르시 맨투맨":
                res = R.drawable.product1;
                break;

            case "커버낫 맨투맨":
                res = R.drawable.product2;
                break;

            case "칼하트 맨투맨":
                res = R.drawable.product3;
                break;
        }

        return res;
    }

    public void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.AT_MOST);

        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

}