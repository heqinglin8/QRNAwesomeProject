package com.awesomeproject;


import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.text.InputFilter;
import android.text.InputType;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.react.bridge.ReactContext;
import com.facebook.react.uimanager.UIManagerModule;
import com.facebook.react.uimanager.events.EventDispatcher;

import java.lang.reflect.Method;


/**
 * Created by steven.shen on 15/7/23.
 */
public class SafeEditText extends ClearableEditText {

    private NumberKeyboardView numberKeyboardView;
    private int inputMode = NumberKeyboardView.MODE_NORMAL;

    public static final int TYPE_ID = 0x1;
    public static final int TYPE_OTHER = 0x2;

    private int type;
    private boolean isStrictMode = false;
    private float down;
//    private static final float MIN_DISTANCE = Dimen.dpToPx(1);
    private static final float MIN_DISTANCE = 3;

    private Method mShowSoftInputOnFocus = getMethod(EditText.class, "setShowSoftInputOnFocus", boolean.class);
    private InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);

    private EventDispatcher mEventDispatcher = null;
    private boolean needFormat = false;
    private int maxLength;
    private String valueFromRN = null;
    private int setDefaultTextNum = 0;
    private String defaultValueFromRN = null;
    private int clearIconSize = -1;

    public SafeEditText(Context context) {
        super(context);
        init();
    }

    public SafeEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void setStrictMode(boolean isStrictMode){
        this.isStrictMode = isStrictMode;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setBaseView (View mBaseView){
        numberKeyboardView.setBaseView(mBaseView);
    }

    private void init() {
        if(getContext() instanceof ReactContext){
            mEventDispatcher = ((ReactContext) getContext()).
                    getNativeModule(UIManagerModule.class).getEventDispatcher();
        }


        numberKeyboardView = new NumberKeyboardView(this.getContext());
        numberKeyboardView.init(inputMode, true, this);

        mShowSoftInputOnFocus = getMethod(EditText.class, "setShowSoftInputOnFocus", boolean.class);

        setInputType(getInputType() | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        setFocusableInTouchMode(true);

        setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type == TYPE_ID) {
                    setCursorVisible(true);
                } else {
                    showSoftInput();
                }
            }
        });

        setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (type == TYPE_ID) {
                    setCursorVisible(true);
                } else {
                    showSoftInput();
                }
                return false;
            }
        });

        reflexSetShowSoftInputOnFocus(false);

        setSelection(getText().length());
    }

    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
        String msg = focused?"获得了焦点":"失去焦点";
        Toast.makeText(getContext(),msg,Toast.LENGTH_LONG).show();
        if (type == TYPE_ID) {
            if (focused) {
                hideKeyboard();
                showKeyboard();
            } else {
                dismissKeyboard();
            }
        } else {
            if(focused){
                showSoftInput();
            } else {
                hideSoftInput();
            }
        }
        //触发js中事件
        if(mEventDispatcher != null) {
            mEventDispatcher.dispatchEvent(new QRCTSafeEditEvent(getId(), focused ? QRCTSafeEditEvent.ON_FOCUS : QRCTSafeEditEvent.ON_BLUR, this.getText().toString()));
        }
    }

    private void showSoftInput() {
        if(imm == null) {
            return;
        }
        imm.showSoftInput(SafeEditText.this, InputMethodManager.SHOW_IMPLICIT);
    }

    private void hideSoftInput(){
        if(imm == null) {
            return;
        }
        imm.hideSoftInputFromWindow(getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (type == TYPE_ID) {
            final boolean ret = super.onTouchEvent(event);
            if(!isStrictMode){
                if (!hasFocus() && requestFocus()) {
                    hideKeyboard();
                    showKeyboard();
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    hideKeyboard();
                    showKeyboard();
                }
            }else {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        down = event.getY();
                        break;
                    case MotionEvent.ACTION_UP:
                        if(Math.abs(event.getY()-down)<MIN_DISTANCE){
                            if (!hasFocus() && requestFocus()) {
                                hideKeyboard();
                                showKeyboard();
                            }
                        }
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        break;
                }
            }

            return ret;
        } else {
            return super.onTouchEvent(event);
        }
    }

    private void reflexSetShowSoftInputOnFocus(boolean show) {
        if (mShowSoftInputOnFocus != null) {
            invokeMethod(mShowSoftInputOnFocus, this, show);
        } else {
            hideKeyboard();
        }
    }

    private void hideKeyboard() {
        final InputMethodManager imm = ((InputMethodManager) getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE));
        if (imm != null && imm.isActive(this)) {
            imm.hideSoftInputFromWindow(getApplicationWindowToken(), 0);
        }
    }

    public boolean isKeyboardShown() {
        return numberKeyboardView.isShowing();
    }

    public void dismissKeyboard() {
        if (numberKeyboardView != null) {
            numberKeyboardView.dismiss();
        }
    }

    public void dismissKeyboard(final Activity activity) {
        this.dismissKeyboard();
        //移除焦点
        activity.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                if (activity != null) {
                    ViewGroup parentView = (ViewGroup) getParent();
                    parentView.setFocusableInTouchMode(true);
                    parentView.setFocusable(true);
                    clearFocus();
                }
            }
        });
    }


    public void showKeyboard() {
        if (numberKeyboardView != null) {
            numberKeyboardView.show();
        }
    }

    public Method getMethod(Class<?> cls, String methodName, Class<?>... parametersType) {
        Class<?> sCls = cls.getSuperclass();
        while (sCls != Object.class) {
            try {
                return sCls.getDeclaredMethod(methodName, parametersType);
            } catch (NoSuchMethodException e) {
            }
            sCls = sCls.getSuperclass();
        }
        return null;
    }

    public Object invokeMethod(Method method, Object receiver, Object... args) {
        try {
            return method.invoke(receiver, args);
        } catch (Exception e) {
        }
        return null;
    }

    @Override
    public void onTextChanged(CharSequence text, int start, int before, int count) {
        super.onTextChanged(text, start, before, count);

        //触发js中事件
        if(mEventDispatcher != null) {
            mEventDispatcher.dispatchEvent(new QRCTSafeEditEvent(getId(), QRCTSafeEditEvent.ON_CHANGE_TEXT, this.getText().toString()));
        }
    }

    public void setMaxLength(int maxLength){
        this.setMaxEms(maxLength);
        this.maxLength = maxLength;
    }

    public void setDefaultValue(String defaultValue){
        this.defaultValueFromRN = defaultValue;
    }

    public void setValue(String value){
        this.valueFromRN = value;
    }

    public void setPlaceholder(String placeholder){
        this.setHint(placeholder);
    }

    public void setFontSize(int fontSize){
        this.setTextSize(TypedValue.COMPLEX_UNIT_DIP,fontSize);
    }

    public void setFontColor(String fontColor){
        this.setTextColor(Color.parseColor(fontColor));
    }

//    public void setStylePadding(int stylePadding){
//        this.setPadding(BitmapHelper.dip2px(stylePadding), BitmapHelper.dip2px(stylePadding), BitmapHelper.dip2px(stylePadding), BitmapHelper.dip2px(stylePadding));
//    }

    public void setNeedFormat(boolean needFormat){
        this.needFormat = needFormat;
    }

//    public void setClearIconSize(int size){
//        if (this.clearIconSize == -1) {
//            //重新设置删除按钮的尺寸
//            Bitmap oldBmp = BitmapFactory.decodeResource(getResources(), R.drawable.atom_flight_delete_icon_normal);
//            Bitmap newBmp = Bitmap.createScaledBitmap(oldBmp, BitmapHelper.dip2px(size), BitmapHelper.dip2px(size), true);
//            this.clearableDrawable = new BitmapDrawable(getResources(), newBmp);
//            this.clearableDrawable.setBounds(0,0,BitmapHelper.dip2px(size),BitmapHelper.dip2px(size));
//            this.clearIconSize = size;
//        }
//    }


    public void onAfterUpdateTransaction(){
//        IDCardTextWatcher idCardTextWatcher = new IDCardTextWatcher();
//        if(needFormat && maxLength > 0){
//            //不设置maxLength，格式化的逻辑不起作用
//            idCardTextWatcher.setEditText(this, maxLength);
//            idCardTextWatcher.setSpaceType(IDCardTextWatcher.SpaceType.IDCardNumberTypeForRN);
//        }
        if(maxLength > 0) {
            setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});
        }


        if(valueFromRN != null){
            this.setText(valueFromRN);
            this.setSelection(this.getText().toString().length());
        }

        //控制设置默认值，组件创建以后，只能设置一次
        //否则，rn中render调用时会多次设置，造成在中间输入内容时，光标移到最后的问题
        if(defaultValueFromRN != null){
            if(setDefaultTextNum == 0){
                this.setText(defaultValueFromRN);
                this.setSelection(this.getText().toString().length());
            }
            setDefaultTextNum ++;
        }

        //若是default为空，则将编辑框内容清空，为了使rn中操作了'清除'能清空内容
        //这时因为无内容，所以不会存在光标错位的问题
        if("".equals(defaultValueFromRN)){
            this.setText("");
        }
    }
}