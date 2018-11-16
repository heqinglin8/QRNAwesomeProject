/**
 * Created by yameiwang on 18/7/11.
 * @providesModule SafeTextInput
 */

'use strict';

var requireNativeComponent = require('requireNativeComponent');
var QRCTSafeEdit = requireNativeComponent('QRCTSafeEdit');
import React, { Component } from 'react';
import PropTypes from 'prop-types';

export default class SafeTextInput extends Component {
    static propTypes = {

        maxLength: PropTypes.number,

        defaultValue: PropTypes.string,

        value: PropTypes.string,

        placeholder: PropTypes.string,

        fontSize: PropTypes.number,

        fontColor: PropTypes.string,

        styleHeight: PropTypes.number,

        needFormat: PropTypes.bool,
        
        onChangeText: PropTypes.func,
        
        onFocus: PropTypes.func,
        
        onBlur: PropTypes.func,
        
        clearIconMarginRight:PropTypes.number,

        autoFocus: PropTypes.bool
    };

    constructor(props) {
        super(props);
        this.onFocus = this.onFocus.bind(this);
        this.onBlur = this.onBlur.bind(this);
    }

    render() {
        return (
            <QRCTSafeEdit
                {...this.props}
                onGetFocus={this.onFocus}
                onRemoveFocus={this.onBlur}
            />
        );
    }


    onFocus() {
        // alert('onFocus');
      }
    
      onBlur() {
        // alert('onBlur');
      }
      
}

