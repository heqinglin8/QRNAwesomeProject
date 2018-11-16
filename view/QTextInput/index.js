/**
 * Created by yameiwang on 18/7/11.
 * @providesModule SafeTextInput
 */

'use strict';

var requireNativeComponent = require('requireNativeComponent');
var QRCTSafeEdit = requireNativeComponent('QRCTSafeEdit');
// var QEditText = requireNativeComponent('QEditText');
import React, { Component } from 'react';
import PropTypes from 'prop-types';

export default class QTextInput extends Component {
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

    // _inputRef;
    // _focusSubscription;
    // _lastNativeText;
    // _lastNativeSelection;

    constructor(props) {
        super(props);
    }

    render() {
        return (
            <QRCTSafeEdit
                {...this.props}
                onGetFocus={this.onFocus}
                onRemoveFocus={this.onBlur}
            />

            // <QEditText
            //  {...this.props}
            //  ref={this._setNativeRef}
            //  onChange={this._onChange}
            //  />
        );
    }

    // _onChange(event) {
    //     // Make sure to fire the mostRecentEventCount first so it is already set on
    //     // native when the text value is set.
    //     if (this._inputRef) {
    //       this._inputRef.setNativeProps({
    //         mostRecentEventCount: event.nativeEvent.eventCount,
    //       });
    //     }
    
    //     const text = event.nativeEvent.text;
    //     this.props.onChange && this.props.onChange(event);
    //     this.props.onChangeText && this.props.onChangeText(text);
    
    //     if (!this._inputRef) {
    //       // calling `this.props.onChange` or `this.props.onChangeText`
    //       // may clean up the input itself. Exits here.
    //       return;
    //     }
    
    //     this._lastNativeText = text;
    //     this.forceUpdate();
    //   }

    //   _setNativeRef(ref) {
    //     this._inputRef = ref;
    //   }


      // componentDidMount() {
      //   this._lastNativeText = this.props.value;
      //   const tag = ReactNative.findNodeHandle(this._inputRef);
      //   if (tag != null) {
      //     // tag is null only in unit tests
      //     TextInputState.registerInput(tag);
      //   }
    
      //   if (this.context.focusEmitter) {
      //     this._focusSubscription = this.context.focusEmitter.addListener(
      //       'focus',
      //       el => {
      //         if (this === el) {
      //           this.requestAnimationFrame(this.focus);
      //         } else if (this.isFocused()) {
      //           this.blur();
      //         }
      //       },
      //     );
      //     if (this.props.autoFocus) {
      //       this.context.onFocusRequested(this);
      //     }
      //   } else {
      //     if (this.props.autoFocus) {
      //       this.requestAnimationFrame(this.focus);
      //     }
      //   }
      // }
      
}

