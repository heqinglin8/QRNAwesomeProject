package com.awesomeproject.manager;

import com.awesomeproject.QEditText;
import com.awesomeproject.QRCTSafeEditEvent;
import com.awesomeproject.SafeEditText;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.annotations.ReactProp;

import java.util.Map;

import javax.annotation.Nullable;

/**
 * Created by heqinglin on 18/7/10.
 */

public class QRCTEditManager extends SimpleViewManager<QEditText> {

    @Override
    public String getName() {
        return "QEditText";
    }

    @Override
    protected QEditText createViewInstance(ThemedReactContext themedReactContext) {
        return new QEditText(themedReactContext);
    }

//    @Nullable
//    @Override
//    public Map<String, Object> getExportedCustomDirectEventTypeConstants() {
//        MapBuilder.Builder builder = MapBuilder.builder();
//        builder.put(QRCTSafeEditEvent.ON_CHANGE_TEXT, MapBuilder.of("registrationName", QRCTSafeEditEvent.ON_CHANGE_TEXT));
//        builder.put(QRCTSafeEditEvent.ON_FOCUS, MapBuilder.of("registrationName",QRCTSafeEditEvent.ON_FOCUS));
//        builder.put(QRCTSafeEditEvent.ON_BLUR, MapBuilder.of("registrationName",QRCTSafeEditEvent.ON_BLUR));
//
//        return builder.build();
//    }


    @Override
    public void receiveCommand(QEditText root, int commandId, @Nullable ReadableArray args) {
        super.receiveCommand(root, commandId, args);
    }
}
