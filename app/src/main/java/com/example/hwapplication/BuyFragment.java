package com.example.hwapplication;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;


public class BuyFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ArrayList<HashMap<String, String>> buyList;
    private ListView listview ;
    private BuyListViewAdapter adapter;

    private EditText editAddress, editNumber;

    public BuyFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static BuyFragment newInstance(String param1, String param2) {
        BuyFragment fragment = new BuyFragment();
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
        buyList = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_buy, container, false);

        if(getArguments() != null){
            ArrayList<HashMap<String, Object>> data = (ArrayList<HashMap<String, Object>>) getArguments().getSerializable("data");
            for(HashMap<String, Object> map: data){
                String title = (String) map.get("title");
                int price = Integer.parseInt((String) map.get("price"));
                int cnt = (Integer) map.get("cnt");
                int totalPrice = price * cnt;

                HashMap<String, String> tmp = new HashMap<>();
                tmp.put("title", title);
                tmp.put("cnt", cnt+"");
                tmp.put("totalPrice", totalPrice+"");

                buyList.add(tmp);
            }

        }


        // Adapter 생성
        adapter = new BuyListViewAdapter(getActivity(), android.R.layout.simple_list_item_multiple_choice);

        // 리스트뷰 참조 및 Adapter달기
        listview = (ListView) rootView.findViewById(R.id.list_buy);
        listview.setAdapter(adapter);

        int total_price = 0;

        for(HashMap<String, String> m : buyList){
            String title = m.get("title");
            String cnt = m.get("cnt");
            String totalPrice = m.get("totalPrice");

            total_price += Integer.parseInt(totalPrice);

            // 첫 번째 아이템 추가.
            adapter.addItem(title, totalPrice, cnt);

        }
        setListViewHeightBasedOnChildren(listview);     // 스크롤뷰 크기 조정

        final TextView textTotalPrice = (TextView) rootView.findViewById(R.id.textv_total_price);
        textTotalPrice.setText(total_price+"원");

        editAddress = (EditText) rootView.findViewById(R.id.edit_address);
        editNumber = (EditText) rootView.findViewById(R.id.edit_phoneNumber);

        Button payButton = (Button) rootView.findViewById(R.id.button_payment);
        payButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String address = editAddress.getText().toString();
                String number = editNumber.getText().toString();
                String totalPrice = textTotalPrice.getText().toString();

                if(totalPrice.equals("0원")){
                    Toast.makeText(getActivity(), "결제할 상품이 없습니다.", Toast.LENGTH_LONG).show();
                    return;
                }

                if(address.length()==0 || number.length()==0){
                    Toast.makeText(getActivity(), "주소 및 번호를 입력하셔야합니다.", Toast.LENGTH_LONG).show();
                    return;
                }

                // 결제가 성공하면 다시 첫화면으로 돌아감.
                MainActivity activity = (MainActivity) getActivity();
                activity.setFrag(0, null);

                Toast.makeText(getActivity(), "결제가 완료되었습니다.", Toast.LENGTH_LONG).show();
            }
        });


        return rootView;
    }


    public void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.AT_MOST);
        int desiredHeight = 150;

        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(View.MeasureSpec.makeMeasureSpec(desiredWidth, View.MeasureSpec.AT_MOST),
                    View.MeasureSpec.makeMeasureSpec(desiredHeight, View.MeasureSpec.AT_MOST));

            totalHeight += listItem.getMeasuredHeight();
        }
        if(totalHeight > 800)
            totalHeight = listView.getHeight();

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

}