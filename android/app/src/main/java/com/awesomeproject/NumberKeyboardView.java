package com.awesomeproject;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Random;


/**
 * Created by jun.xu on 2014/4/25.
 */
public class NumberKeyboardView extends Dialog implements View.OnClickListener, View.OnFocusChangeListener,DialogInterface.OnDismissListener {

    public static final int MODE_NORMAL = 0;
    public static final int MODE_RANDOM = 1;

    private Context mContext;
    private int[] mNumberBase;
    private int mMode = MODE_NORMAL;
    private boolean mDisplayX;
    private EditText editText;
    private LinearLayout contentView;
    private View mBaseView;
    private int mInitialScrollY;

    public NumberKeyboardView(Context context) {
        super(context);
        this.mContext = context;
    }

    public void init(int mode, boolean displayX, EditText editText) {
        this.mMode = mode;
        this.mDisplayX = displayX;
        this.editText = editText;
        contentView = new LinearLayout(this.getContext());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.CENTER;
        contentView.setLayoutParams(layoutParams);
        contentView.setOrientation(LinearLayout.VERTICAL);
        contentView.setOnFocusChangeListener(this);
        setContentView(contentView);
        generateNumberKeyboard();
        initWindow();
        setCanceledOnTouchOutside(true);
//        setOnShowListener(this);
        setOnDismissListener(this);
    }

    public void setBaseView (View mBaseView){
        this.mBaseView = mBaseView;
        mInitialScrollY = this.mBaseView.getScrollY();
    }

    private void initWindow() {
        Window localWindow = getWindow();
        WindowManager.LayoutParams lp = localWindow.getAttributes();
        lp.gravity = Gravity.BOTTOM;
        lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
        lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        localWindow.setAttributes(lp);
        localWindow.setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN |
                        WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN |
                        WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE |
                        WindowManager.LayoutParams.FLAG_DIM_BEHIND);
    }

    /**
     * 根据模式获取键盘的数字数据
     */
    private void generateNumberBase() {
        int NUMBER_COUNT = 10;
        int[] data = new int[NUMBER_COUNT];
        data[0] = 1;
        data[1] = 2;
        data[2] = 3;
        data[3] = 4;
        data[4] = 5;
        data[5] = 6;
        data[6] = 7;
        data[7] = 8;
        data[8] = 9;
        data[9] = 0;
        if (mMode == MODE_RANDOM) {
            Random localRandom = new Random();
            for (int j = 0; j < NUMBER_COUNT; j++) {
                int k = Math.abs(localRandom.nextInt()) % NUMBER_COUNT;
                int i = data[k];
                data[k] = data[0];
                data[0] = i;
            }
        }
        mNumberBase = data;
    }

    private void generateNumberKeyboard() {
        generateNumberBase();
        for (int k = 0; k < 4; k++) {
            LinearLayout localLinearLayout = new LinearLayout(this.getContext());
            localLinearLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            localLinearLayout.setBackgroundColor(Color.parseColor("#8B8B8B"));
//            for (int n = 0; n < 3; n++) {
//                TextView keyButton = generateKeyButton();
//                keyButton.setOnClickListener(this);
//                if (k == 3) {
//                    switch (n) {
//                        case 0:
//                            if (mDisplayX) {
//                                keyButton.setText("X");
//                            } else {
//                                keyButton.setEnabled(false);
//                            }
//                            break;
//                        case 1:
//                            keyButton.setText(String.valueOf(this.mNumberBase[mNumberBase.length - 1]));
//                            break;
//                        case 2:
//                            ImageView deleteButton = generateKeyDeleteButton();
//                            deleteButton.setOnClickListener(this);
//                            localLinearLayout.addView(deleteButton);
//                            continue;
//                    }
//                } else {
//                    keyButton.setText(String.valueOf(this.mNumberBase[(k * 3 + n)]));
//                }
//                localLinearLayout.addView(keyButton);
//            }
            this.contentView.addView(localLinearLayout);
        }
    }

//    private TextView generateKeyButton() {
//        TextView keyboardBtn = new TextView(this.getContext());
//        int internalWidth = BitmapHelper.dip2px(0.5f);
//        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
//        layoutParams.gravity = Gravity.CENTER;
//        layoutParams.setMargins(internalWidth, internalWidth, internalWidth, internalWidth);
//        keyboardBtn.setLayoutParams(layoutParams);
//        int padding = BitmapHelper.dip2px(10);
//        keyboardBtn.setPadding(padding, padding, padding, padding);
//        keyboardBtn.setGravity(Gravity.CENTER);
//        keyboardBtn.setBackgroundResource(R.drawable.atom_flight_keyboard_selector);
//        keyboardBtn.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 24.0f);
//        keyboardBtn.setTextColor(Color.BLACK);
//        return keyboardBtn;
//    }

//    private ImageView generateKeyDeleteButton() {
//        ImageView keyboardBtn = new ImageView(this.getContext());
//        int internalWidth = BitmapHelper.dip2px(0.5f);
//        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
//        layoutParams.gravity = Gravity.CENTER;
//        layoutParams.setMargins(internalWidth, internalWidth, internalWidth, internalWidth);
//        keyboardBtn.setLayoutParams(layoutParams);
//        //keyboardBtn.setBackgroundColor(Color.WHITE);
//        keyboardBtn.setBackgroundResource(R.drawable.atom_flight_keyboard_selector);
//        keyboardBtn.setImageResource(R.drawable.atom_flight_btn_input_delete);
//        keyboardBtn.setScaleType(ImageView.ScaleType.CENTER);
//        keyboardBtn.setTag("del");
//        return keyboardBtn;
//    }


    @Override
    public void onClick(View v) {
        String tag = (String) v.getTag();
        Editable editable = editText.getText();
        int start = editText.getSelectionStart();
        if (v instanceof TextView) {
            String content = ((TextView) v).getText().toString();
            if (!TextUtils.isEmpty(content) && TextUtils.isDigitsOnly(content)) {
                editable.insert(start, content);
            } else if ("X".equalsIgnoreCase(content)) {
                editable.insert(start, "X");
            } else if (v instanceof Button) {
                if ("done".equals(tag)) {
                    this.dismiss();
                    InputMethodManager inputManager = (InputMethodManager) this.getContext().getSystemService(
                            Context.INPUT_METHOD_SERVICE);
                    inputManager.hideSoftInputFromWindow(this.editText.getWindowToken(), 0);
                }
            }
        } else if (v instanceof ImageView) {
            if ("del".equalsIgnoreCase(tag)) {
                if (editable != null && editable.length() > 0 && start > 0) {
                    editable.delete(start - 1, start);
                }
            }
        }
        //editText.setSelection(editText.getText().toString().length());
    }

    @Override
    public void show() {
        InputMethodManager inputManager = (InputMethodManager) this.getContext().getSystemService(
                Context.INPUT_METHOD_SERVICE);
        Context context = this.editText.getContext();
        if (context instanceof Activity) {
            Activity activity = (Activity) context;
            if (activity.getCurrentFocus() != null) {
                inputManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
            } else {
                inputManager.hideSoftInputFromWindow(this.editText.getWindowToken(), 0);
            }
        } else {
            inputManager.hideSoftInputFromWindow(this.editText.getWindowToken(), 0);
        }

        super.show();
        //new AndroidBug5497Workaround(mContext).possiblyResizeChildOfContent(this.getWindow().getDecorView().getHeight() + BitmapHelper.dip2px(25));
    }



    @Override
    public void dismiss() {
        InputMethodManager inputManager = (InputMethodManager) this.getContext().getSystemService(
                Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(this.editText.getWindowToken(), 0);
        super.dismiss();
        //new AndroidBug5497Workaround(mContext).possiblyResizeChildOfContent(BitmapHelper.dip2px(25));
    }

//    @Override
//    public void onShow(DialogInterface dialog) {
//        if (mBaseView != null) {
//            int[] location = new int[2];
//            editText.getLocationOnScreen(location);
//            int moveHeight = Dimen.SCREEN_HEIGHT - getWindow().getDecorView().getHeight()
//                    - (location[1] + editText.getHeight()+BitmapHelper.dip2px(3));
//            if (moveHeight < 0) {
//                mBaseView.scrollBy(0, -moveHeight);
//            }
//        }
//    }


    @Override
    public void onFocusChange(View v, boolean hasFocus) {

//		InputMethodManager inputManager = (InputMethodManager) this.getContext().getSystemService(
//				Context.INPUT_METHOD_SERVICE);
//		inputManager.hideSoftInputFromWindow(this.editText.getWindowToken(),0);
//		Window localWindow = this.getWindow();
//		localWindow.setGravity(Gravity.BOTTOM);
//		localWindow.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//		localWindow.setAttributes(localWindow.getAttributes());
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        /*if (this.mBaseView != null) {
            this.mBaseView.scrollTo(0, this.mInitialScrollY);
        }*/
    }
}

