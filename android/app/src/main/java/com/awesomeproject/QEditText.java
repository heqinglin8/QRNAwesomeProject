package com.awesomeproject;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class QEditText extends EditText {

    Context mContext;

    public QEditText(Context context) {
        super(context);
        init(context);
    }

    public QEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        Toast.makeText(context, "QEditText初始化", Toast.LENGTH_SHORT);
        this.mContext = context;
        setListener();
    }

    private void setListener() {
        setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                String text = b ? "获得焦点" : "失去焦点";
                Toast.makeText(mContext, text, Toast.LENGTH_SHORT);
            }
        });
    }

}
