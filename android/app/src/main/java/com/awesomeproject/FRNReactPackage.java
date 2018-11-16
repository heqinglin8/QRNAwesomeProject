package com.awesomeproject;

import com.awesomeproject.manager.QRCTEditManager;
import com.awesomeproject.manager.QRCTSafeEditManager;
import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.ViewManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenxi.cui on 16/5/11.
 */
public class FRNReactPackage implements ReactPackage {

    @Override
    public List<ViewManager> createViewManagers(ReactApplicationContext reactContext) {
        List<ViewManager> viewManagerList=new ArrayList<>();
        viewManagerList.add(new QRCTSafeEditManager());
        viewManagerList.add(new QRCTEditManager());
//        viewManagerList.add(new QReactTextInputManager());
        return viewManagerList;

    }

    @Override
    public List<NativeModule> createNativeModules(
            ReactApplicationContext reactContext) {
        List<NativeModule> modules = new ArrayList<>();
        return modules;
    }
}