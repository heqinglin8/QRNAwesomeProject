package com.awesomeproject;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v4.view.MotionEventCompat;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.EditText;


/**
 * 可清空的EditText
 * 
 * @author steven.shen
 * 
 */
public class ClearableEditText extends EditText implements OnFocusChangeListener, TextWatcher {

    Drawable clearableDrawable;
    private Drawable[] mCompoundDrawables;
    private boolean isClearableHit;
    private boolean isFoucs;
	private boolean isVisible;
    private boolean isToUpperCase;

    /** hint text style **/
    public final int textStyleHint;

    public final int textStyle;

    private Drawable clearIconDrawable;

    public OnFocusChangeListener onFocusChangeListener;

    public ClearableEditText(Context context) {
        this(context, null);
    }

    public ClearableEditText(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.atom_flight_ClearableEditText);
        textStyleHint = a.getInt(R.styleable.atom_flight_ClearableEditText_atom_flight_textStyleHint, Typeface.NORMAL);
        textStyle = a.getInt(R.styleable.atom_flight_ClearableEditText_android_textStyle, Typeface.NORMAL);
        clearIconDrawable = a.getDrawable(R.styleable.atom_flight_ClearableEditText_atom_flight_clearableIcon);
        a.recycle();
        // drawable right
        clearableDrawable = getCompoundDrawables()[2];
        if (!isInEditMode()) {
            if (clearableDrawable == null) {
                if(clearIconDrawable == null){
                    clearIconDrawable = getResources().getDrawable(R.drawable.atom_flight_delete_icon);
                }
                clearableDrawable = clearIconDrawable;
            }
            clearableDrawable.setBounds(0, 0, clearableDrawable.getIntrinsicWidth(),
                    clearableDrawable.getIntrinsicHeight());
        }
        addTextChangedListener(this);
        setOnFocusChangeListener(this);
    }

    /**
     * We expose this method as public because calling setError(null) on Gingerbread devices will hide the cancel (and
     * other) drawables. You can call showOrHideCancel() after you call setError(null) to reset the drawables.
     */
    public void showOrHideCancel() {
        setCancelVisible(getText().length() > 0);
    }

    private void setCancelVisible(boolean visible) {
        if (mCompoundDrawables == null) {
            mCompoundDrawables = getCompoundDrawables();
        }
		isVisible = visible;
        if (visible) {
            setCompoundDrawablesWithIntrinsicBounds(mCompoundDrawables[0], mCompoundDrawables[1], clearableDrawable,
                    mCompoundDrawables[3]);
        } else {
            setCompoundDrawablesWithIntrinsicBounds(mCompoundDrawables[0], mCompoundDrawables[1],
                    mCompoundDrawables[2], mCompoundDrawables[3]);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        final int action = MotionEventCompat.getActionMasked(event);
        switch (action) {
        case MotionEvent.ACTION_DOWN:
            isClearableHit = clearableDrawable != null && isVisible
                    && event.getX() > getWidth() - getPaddingRight() - clearableDrawable.getIntrinsicWidth();
            break;
        case MotionEvent.ACTION_UP:
            if (isClearableHit) {
                setText("");
                setCancelVisible(false);
            }
            break;
        default:
            break;
        }
        return super.onTouchEvent(event);
    }

    public void setToUpperCase(boolean isToUpperCase) {
        this.isToUpperCase = isToUpperCase;
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        isFoucs = hasFocus;
        focusChange(v, hasFocus);
    }

    public void focusChange(View v, boolean hasFocus) {
        if (hasFocus) {
            showOrHideCancel();
        } else {
            setCancelVisible(false);
        }

        if(isToUpperCase && v instanceof EditText){
            final EditText ed = (EditText) v;
                Editable s = ed.getText();
                if (s != null) {
                    if (!s.toString().equals(s.toString().toUpperCase())) {
                        ed.postDelayed(new Runnable() {
                            public void run() {
                                int postion = Math.min(ed.getSelectionStart(), ed.getText().toString().length());
                                ed.setText(ed.getText().toString().toUpperCase());
                                Selection.setSelection(ed.getText(), postion);
                            }
                        }, 200);
                    }
                }
        }
        if(onFocusChangeListener != null){
            onFocusChangeListener.onFocusChange(v,hasFocus);
        }
    }

    public void addOnFocusChangeListener(OnFocusChangeListener onFocusChangeListener){
        this.onFocusChangeListener = onFocusChangeListener;
    }

    @Override
    public void onTextChanged(CharSequence text, int start, int before, int count) {
        if (isFoucs) {
            showOrHideCancel();
        }
        setTypeface(null, getText().length() > 0 ? textStyle : textStyleHint);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void afterTextChanged(Editable s) {
    }

    public boolean getFocus(){
        return isFoucs;
    }

    public void addTextWatch(){
        addTextChangedListener(this);
    }
}
