/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 *
 * @format
 * @flow
 */

import React, { Component } from 'react';
import { Platform, StyleSheet, Text, View,TextInput,PixelRatio } from 'react-native';
// var requireNativeComponent = require('requireNativeComponent');
// var QRCTSafeEdit = requireNativeComponent('QRCTSafeEdit');
// import SafeTextInput from './SafeTextInput';
import { Keyboard,TouchableOpacity } from 'react-native';
import QText from './view/QText'
import ScreenUtil from './utils/ScreenUtil';

console.log('hql','ScreenUtil=',ScreenUtil);
const instructions = Platform.select({
  ios: 'Press Cmd+R to reload,\n' + 'Cmd+D or shake for dev menu',
  android:
    'Double tap R on your keyboard to reload,\n' +
    'Shake or press menu button for dev menu',
});
const isAndroid = Platform.OS==='android';
export default class App extends Component {
  render() {
    return (
      <View style={styles.container}>
      {/* <QText style={ {fontSize:30} }> 徐爽 hello</QText>
        <TouchableOpacity onPress={this.onSelectCertificate.bind(this)}>
        <Text style={styles.welcome} >点击关闭键盘</Text>
        <Text style={styles.welcome} >welcome to</Text>
        <Text style={styles.welcome} >react native</Text>
         </TouchableOpacity>
        <TouchableOpacity onPress={this.logTextProps.bind(this)}>
        <Text 
        style={styles.instructions}
        ref={this._setTextRef}
        >To get started, edit App.js</Text>
        </TouchableOpacity>
        <Text style={styles.instructions}>{instructions}</Text> */}

        <Text style={{fontSize: 30}}>没适配,本机像素：{PixelRatio.get()}</Text>
                <Text style={{fontSize: ScreenUtil.setSpText(30)}}>已适配</Text>
                <View style={{
                    height: 50, width: 240, backgroundColor: 'green'
                }}>
               <Text style={{fontSize: ScreenUtil.setSpText(30)}}>没有适配的view</Text> 
                </View>
                <View style={{
                    height: ScreenUtil.scaleHeight(50),
                    width: ScreenUtil.scaleSize(240),
                    backgroundColor: 'red'
                }}>
               <Text style={{fontSize: ScreenUtil.setSpText(30)}}>适配后的view</Text>
              </View>

           {/* <TextInput
              style={[styles.input]}
              label=''
              name='cardNo'
              ref="safeInputIDCardNo"
              fontSize={15}
              fontColor={'#333333'}
              stylePadding={0}
              safeKeyboard={true}
              needFormat={true}
              // maxLength={maxLength}
              defaultValue={'何清林 TextInput'}
              autoFocus={false}
              placeholder='名字'
              clearIconSize={15}
              returnKeyType={'done'}
          />

       <TextInput
              style={[styles.input]}
              label=''
              name='cardNo'
              ref="safeInputIDCardNo"
              fontSize={15}
              fontColor={'#333333'}
              stylePadding={0}
              safeKeyboard={true}
              needFormat={true}
              // maxLength={maxLength}
              defaultValue={'何清林1 TextInput'}
              autoFocus={false}
              placeholder='名字'
              clearIconSize={15}
              returnKeyType={'done'}
          /> */}

            {/* <SafeTextInput
              style={[styles.input]}
              label=''
              name='cardNo'
              ref="safeInputIDCardNo"
              fontSize={15}
              fontColor={'#333333'}
              stylePadding={0}
              safeKeyboard={false}
              needFormat={true}
              // maxLength={maxLength}
              defaultValue={'证件 SafeTextInput'}
              autoFocus={false}
              placeholder='请填写证件号码'
              clearIconSize={15}
              returnKeyType={'done'}
          /> */}
      </View>
    );
  }

  onFocus() {
    alert('onFocus');
  }

  onBlur() {
    alert('onBlur');
  }

  onSelectCertificate(){
    Keyboard.dismiss();
  }
  
  logTextProps(args){
    console.log('hql','text=',this.ref);
  }

  _setTextRef(ref) {
    this._textRef = ref;
    console.log('hql','text=',this._textRef);
  }

}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#FFFFFF',
  },
  welcome: {
    fontSize: isAndroid ? 30 * 0.9 : 30,
    color:'#212121',
    textAlign: 'center',
    margin: 10,
    backgroundColor:'#f00',
    includeFontPadding:false
  },
  instructions: {
    textAlign: 'center',
    color: '#333333',
    marginBottom: 5
  },
  input: {
    // flex: 1,
    fontSize: 16,
    height:40,
    width:150,
    padding: 0,
    color: '#333333',
}
});
