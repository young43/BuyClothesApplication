package com.example.buyapplication;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;


public class CustomDialog {

    public interface DialogListener {
        void onPositiveClicked(int index, String title, String price);
        void onNegativeClicked();
    }

    private Context context;
    private DialogListener myDialogListener;


    public CustomDialog(Context context, DialogListener myDialogListener) {
        this.context = context;
        this.myDialogListener = myDialogListener;
    }



    // 호출할 다이얼로그 함수를 정의한다.
    public void callFunction(String product, String price) {

        // 커스텀 다이얼로그를 정의하기위해 Dialog클래스를 생성한다.
        final Dialog dlg = new Dialog(context);

        // 액티비티의 타이틀바를 숨긴다.
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE);

        // 커스텀 다이얼로그의 레이아웃을 설정한다.
        dlg.setContentView(R.layout.custom_dialog);



        // 커스텀 다이얼로그의 각 위젯들을 정의한다.
        Button buttonPurchase = (Button) dlg.findViewById(R.id.button_purchase);
        Button buttonCart = (Button) dlg.findViewById(R.id.button_cart);
        ImageButton buttonCancle = (ImageButton) dlg.findViewById(R.id.button_cancle);

        // 선택한 상품명 표시
        TextView title = (TextView) dlg.findViewById(R.id.dialog_title);
        TextView tprice = (TextView) dlg.findViewById(R.id.textv_price);

        title.setText(product);
        tprice.setText(price);

        // 커스텀 다이얼로그를 노출한다.
        dlg.show();

        final String t_price = price.replace("원", "");
        final String t_title = product;

        final MainActivity activity = (MainActivity) context;
        buttonPurchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDialogListener.onPositiveClicked(2, t_title, t_price);
                dlg.dismiss();
                //activity.setFrag(2);
            }
        });

        buttonCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDialogListener.onPositiveClicked(1, t_title, t_price);
                dlg.dismiss();
                //activity.setFrag(1);
            }
        });

        buttonCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialogListener.onNegativeClicked();
                dlg.cancel();
            }
        });
    }
}


