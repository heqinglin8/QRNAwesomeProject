package com.awesomeproject;

import com.facebook.react.common.MapBuilder;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.annotations.ReactProp;

import java.util.Map;

import javax.annotation.Nullable;

/**
 * Created by yameiwang on 18/7/10.
 */

public class QRCTSafeEditManager extends SimpleViewManager<SafeEditText> {

    @Override
    public String getName() {
        return "QRCTSafeEdit";
    }

    @Override
    protected SafeEditText createViewInstance(ThemedReactContext themedReactContext) {
        return new SafeEditText(themedReactContext);
    }

    @ReactProp(name = "maxLength")
    public void setMaxLength(SafeEditText safeEditText, int maxLength){
        safeEditText.setMaxLength(maxLength);
    }

    @ReactProp(name = "defaultValue")
    public void setDefaultValue(SafeEditText safeEditText, String defaultValue){
        safeEditText.setDefaultValue(defaultValue);
    }

    @ReactProp(name = "value")
    public void setValue(SafeEditText safeEditText, String value){
        safeEditText.setValue(value);
    }

    @ReactProp(name = "placeholder")
    public void setPlaceholder(SafeEditText safeEditText, String placeholder){
        safeEditText.setPlaceholder(placeholder);
    }

    @ReactProp(name = "fontSize")
    public void setFontSize(SafeEditText safeEditText, int fontSize){
        safeEditText.setFontSize(fontSize);
    }

    @ReactProp(name = "fontColor")
    public void setFontColor(SafeEditText safeEditText, String fontColor){
        safeEditText.setFontColor(fontColor);
    }

//    @ReactProp(name = "stylePadding")
//    public void setStylePadding(SafeEditText safeEditText, int stylePadding){
//        safeEditText.setStylePadding(stylePadding);
//    }

    @ReactProp(name = "safeKeyboard")
    public void setSafeKeyboard(SafeEditText safeEditText, boolean safeKeyboard){
        safeEditText.setType(safeKeyboard ? safeEditText.TYPE_ID : safeEditText.TYPE_OTHER);
    }

    @ReactProp(name = "needFormat")
    public void setNeedFormat(SafeEditText safeEditText, boolean needFormat){
        safeEditText.setNeedFormat(needFormat);
    }

//    @ReactProp(name = "clearIconSize")
//    public void setClearIconSize(SafeEditText safeEditText, int size){
//        safeEditText.setClearIconSize(size);
//    }

    @Override
    protected void onAfterUpdateTransaction(SafeEditText safeEditText) {
        super.onAfterUpdateTransaction(safeEditText);
        safeEditText.onAfterUpdateTransaction();
    }

    @Nullable
    @Override
    public Map<String, Object> getExportedCustomDirectEventTypeConstants() {
        MapBuilder.Builder builder = MapBuilder.builder();
        builder.put(QRCTSafeEditEvent.ON_CHANGE_TEXT, MapBuilder.of("registrationName", QRCTSafeEditEvent.ON_CHANGE_TEXT));
        builder.put(QRCTSafeEditEvent.ON_FOCUS, MapBuilder.of("registrationName",QRCTSafeEditEvent.ON_FOCUS));
        builder.put(QRCTSafeEditEvent.ON_BLUR, MapBuilder.of("registrationName",QRCTSafeEditEvent.ON_BLUR));

        return builder.build();
    }
}
