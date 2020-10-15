package com.example.buyapplication;

import android.content.Context;
import android.database.DataSetObservable;
import android.database.DataSetObserver;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class BuyListViewAdapter extends ArrayAdapter {
    // Adapter에 추가된 데이터를 저장하기 위한 ArrayList
    private ArrayList<BuyListViewItem> listViewItemList = new ArrayList<BuyListViewItem>() ;

    int iCurrentSelection = 0;

    public BuyListViewAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }


    // Adapter에 사용되는 데이터의 개수를 리턴. : 필수 구현
    @Override
    public int getCount() {
        return listViewItemList.size() ;
    }

    // position에 위치한 데이터를 화면에 출력하는데 사용될 View를 리턴. : 필수 구현
    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_item_buy, parent, false);
        }

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        TextView titleTextView = (TextView) convertView.findViewById(R.id.textv_buy_product);
        TextView descTextView = (TextView) convertView.findViewById(R.id.textv_buy_price);

        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        final BuyListViewItem listViewItem = listViewItemList.get(position);

        // 아이템 내 각 위젯에 데이터 반영
        titleTextView.setText(listViewItem.getTitle());
        descTextView.setText(listViewItem.getDesc());

        // Spinner(수량 설정)
        Spinner cntSpinner = (Spinner) convertView.findViewById(R.id.spinner_cnt);
        ArrayAdapter cntAdapter = ArrayAdapter.createFromResource(context, R.array.product_cnt, android.R.layout.simple_spinner_item);
        cntAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cntSpinner.setAdapter(cntAdapter);
        cntSpinner.setSelection(Integer.parseInt(listViewItem.getCntStr())-1);

        iCurrentSelection = cntSpinner.getSelectedItemPosition();
        cntSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Spinner 값이 바뀔때마다 가격 변동 처리.
                // 변경감지는 그 이전 수량과 현재 선택된 수량이 다르면 인지.
                if(Integer.parseInt(listViewItem.getCntStr())-1 != position){
                    int totalPrice = (position+1) * Integer.parseInt(listViewItem.getPriceStr());
                    listViewItem.setCntStr((position+1)+"");
                    listViewItem.setDesc(totalPrice+"");
                    BuyListViewAdapter.super.notifyDataSetChanged();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        return convertView;
    }

    // 지정한 위치(position)에 있는 데이터와 관계된 아이템(row)의 ID를 리턴. : 필수 구현
    @Override
    public long getItemId(int position) {
        return position ;
    }

    // 지정한 위치(position)에 있는 데이터 리턴 : 필수 구현
    @Override
    public Object getItem(int position) {
        return listViewItemList.get(position) ;
    }

    // 아이템 데이터 추가를 위한 함수. 개발자가 원하는대로 작성 가능.
    public void addItem(String title, String desc, String cnt, String price) {
        BuyListViewItem item = new BuyListViewItem();

        item.setTitle(title);
        item.setDesc(desc);
        item.setCntStr(cnt);
        item.setPricestr(price);

        listViewItemList.add(item);
    }

    public void removeItem(int position){
        Log.e("testActivity", listViewItemList.get(position).getTitle());
        listViewItemList.remove(position);
    }

    public void removeItem(ListViewItem item){
        listViewItemList.remove(item);
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }
}
