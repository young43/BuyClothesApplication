package com.example.buyapplication;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;


public class ProductFragment extends Fragment implements View.OnClickListener {

    private ViewGroup rootView;
    private RelativeLayout layout1, layout2, layout3;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProductFragment() {
        // Required empty public constructor
    }



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
        rootView = (ViewGroup) inflater.inflate(R.layout.fragment_product, container, false);

        layout1 = (RelativeLayout) rootView.findViewById(R.id.layout_product1);
        layout2 = (RelativeLayout) rootView.findViewById(R.id.layout_product2);
        layout3 = (RelativeLayout) rootView.findViewById(R.id.layout_product3);

        layout1.setOnClickListener(this);
        layout2.setOnClickListener(this);
        layout3.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View v) {

        String title = "";
        String price = "";

        switch (v.getId()) {
            case R.id.layout_product1:
                title = ((TextView) rootView.findViewById(R.id.textv_product_title1)).getText().toString();
                price = ((TextView) rootView.findViewById(R.id.textv_product_price1)).getText().toString();
                break;

            case R.id.layout_product2:
                title = ((TextView) rootView.findViewById(R.id.textv_product_title2)).getText().toString();
                price = ((TextView) rootView.findViewById(R.id.textv_product_price2)).getText().toString();
                break;

            case R.id.layout_product3:
                title = ((TextView) rootView.findViewById(R.id.textv_product_title3)).getText().toString();
                price = ((TextView) rootView.findViewById(R.id.textv_product_price3)).getText().toString();
                break;

            default:
                break;

        }


        CustomDialog customDialog = new CustomDialog(getActivity(), new CustomDialog.DialogListener() {

            @Override
            public void onPositiveClicked(int index, String title, String price) {
                MainActivity activity = (MainActivity) getActivity();

                if(index != 0){
                    Fragment fragment = null;
                    Bundle args = new Bundle();


                    if(index == 1){
                        args.putString("title", title);
                        args.putString("price", price);

                        fragment = new CartFragment();
                        fragment.setArguments(args);
                    }else{
                        ArrayList<HashMap<String, Object>> mapList = new ArrayList<>();
                        HashMap<String, Object> map = new HashMap<>();
                        map.put("title", title);
                        map.put("cnt", 1);
                        map.put("price", price);
                        mapList.add(map);

                        args.putSerializable("data", mapList);

                        fragment = new BuyFragment();
                        fragment.setArguments(args);
                    }

                    activity.setFrag(index, fragment);
                }

            }

            @Override
            public void onNegativeClicked() { }
        });

        customDialog.callFunction(title, price);

    }
}



