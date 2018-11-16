package com.awesomeproject;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.events.Event;
import com.facebook.react.uimanager.events.RCTEventEmitter;

/**
 * Created by yameiwang on 18/7/10.
 */

public class QRCTSafeEditEvent extends Event<QRCTSafeEditEvent> {

    public static final String ON_CHANGE_TEXT = "onChangeInput";
    public static final String ON_FOCUS = "onGetFocus";
    public static final String ON_BLUR = "onRemoveFocus";

    private String mEventName;
    private String inputValue;

    public QRCTSafeEditEvent(int viewId, String eventName, String value){
        super(viewId);
        this.mEventName =eventName;
        this.inputValue = value;
    }

    @Override
    public String getEventName() {
        return mEventName;
    }

    @Override
    public void dispatch(RCTEventEmitter rctEventEmitter) {
        if(ON_CHANGE_TEXT.equals(mEventName) || ON_FOCUS.equals(mEventName) || ON_BLUR.equals(mEventName)) {
            WritableMap paramMap = Arguments.createMap();
            paramMap.putString("text", new String(inputValue));
            rctEventEmitter.receiveEvent(getViewTag(), getEventName(), paramMap);
        }
    }
}
