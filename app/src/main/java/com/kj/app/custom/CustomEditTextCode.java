package com.kj.app.custom;

import android.content.Context;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kj.app.R;

public class CustomEditTextCode extends RelativeLayout implements TextWatcher, View.OnKeyListener {

    private EditText ed;
    private TextView[] textViews;
    private StringBuffer stringBuffer = new StringBuffer();
    private int count;
    private String inputString;
    private String inputContent;

    public CustomEditTextCode(Context context) {
        this(context,null);
    }

    public CustomEditTextCode(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CustomEditTextCode(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        textViews=new TextView[4];
        View.inflate(context, R.layout.custom_ed,this);
        ed = findViewById(R.id.custom_ed);
        textViews[0]=findViewById(R.id.custom_codetvone);
        textViews[1]=findViewById(R.id.custom_codetvtwo);
        textViews[2]=findViewById(R.id.custom_codetvthree);
        textViews[3]=findViewById(R.id.custom_codetvfour);

        ed.setCursorVisible(false);    //隐藏光标

        ChangeLintener();  //EditText监听

    }

    private void ChangeLintener() {
        ed.addTextChangedListener(this);   //输入监听
        ed.setOnKeyListener(this);        //back 监听
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
         if (!s.toString().equals("")){  //判断输入不为空
             if (stringBuffer.length()>3){
                 ed.setText("");
                 return;
             }else {
                 stringBuffer.append(s);
                 ed.setText("");
                 count = stringBuffer.length();  //长度
                 inputString = stringBuffer.toString();
                 if (stringBuffer.length()==4){  //当长度等于4时，调用完成输入时的监听

                 }
             }
             for (int i= 0;i< stringBuffer.length();i++){
                     textViews[i].setText(String.valueOf(inputString.charAt(i)));
                     textViews[i].setBackgroundResource(R.drawable.custom_edcodebg_yes);
                  }

         }
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_DEL
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (onKeyDelete()) return true;
            return true;
        }
        return false;
    }

    private boolean onKeyDelete() {
        if (count == 0) {
            count = 4;
            return true;
        }
        if (stringBuffer.length() > 0) {
            //删除相应位置的字符
            stringBuffer.delete((count - 1), count);
            count--;
            //   Log.e(TAG, "afterTextChanged: stringBuffer is " + stringBuffer);
            inputContent = stringBuffer.toString();
            textViews[stringBuffer.length()].setText("");
            textViews[stringBuffer.length()].setBackgroundResource(R.drawable.custom_edcodebg_no);  //删除的背景

        }
        return false;
    }
}
