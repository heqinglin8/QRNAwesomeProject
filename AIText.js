

import { Platform, Text, View,StyleSheet } from 'react-native';
import React, { Component } from 'react';
const isAndroid = Platform.OS==='android';
export default class AIText extends Component {
    
    constructor(props){
        super(props);
        this.props = props;
        console.log('hql','constructor',props);
    }
    render() {
     let {style} = this.props || {};
     let {fontSize,color} = style || {};
     if(fontSize){
         fontSize = isAndroid ? fontSize * 0.9 : fontSize;
         style = {
             ...style,
             fontSize
         };
     };
     style = {
         ...style,
         ...styles.text,
     };
     return <Text style = {style}>{this.props?this.props.children:''}</Text>
    }
}


const styles = StyleSheet.create({

    text: {
      includeFontPadding:false
    }
  });