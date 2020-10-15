package com.example.buyapplication;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class BuyListViewAdapter extends ArrayAdapter {
    // Adapter에 추가된 데이터를 저장하기 위한 ArrayList
    private ArrayList<BuyListItem> listViewItemList = new ArrayList<BuyListItem>() ;

    public BuyListViewAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }

    // ListViewAdapter의 생성자


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
        TextView cntTextView = (TextView) convertView.findViewById(R.id.textv_buy_count);

        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        BuyListItem listViewItem = listViewItemList.get(position);

        // 아이템 내 각 위젯에 데이터 반영
        titleTextView.setText(listViewItem.getTitle());
        descTextView.setText(listViewItem.getDesc());
        cntTextView.setText(listViewItem.getCntStr());

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
    public void addItem(String title, String desc, String cnt) {
        BuyListItem item = new BuyListItem();

        item.setTitle(title);
        item.setDesc(desc);
        item.setCntStr(cnt);

        listViewItemList.add(item);
    }

    public void removeItem(int position){
        Log.e("testActivity", listViewItemList.get(position).getTitle());
        listViewItemList.remove(position);
    }

    public void removeItem(ListViewItem item){
        listViewItemList.remove(item);
    }



}
