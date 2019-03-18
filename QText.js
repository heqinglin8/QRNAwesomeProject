

import { Platform, Text, View,StyleSheet } from 'react-native';
import React, { Component } from 'react';
const isAndroid = Platform.OS==='android';
export default class QText extends Component {
    
    constructor(props){
        super(props);
        this.props = props;
        console.log('hql','constructor',props);
    }
    render() {
        //  外面有fontSize 才乘以
     let {style} = this.props || {};
     let {fontSize} = style || {};
     let fontSizeRate =  isAndroid ? 0.9 : 1;
     style = {
         ...style,
         ...styles.text,
         fontSize : fontSize ? fontSize * fontSizeRate : null
     };
     console.log('hql','style=',style);
     return <Text style = {style}>{this.props?this.props.children:''}</Text>
    }
}

const styles = StyleSheet.create({
    text: {
      includeFontPadding:false
    }
  });