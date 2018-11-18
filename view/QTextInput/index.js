/**
 * Created by yameiwang on 18/7/11.
 * @providesModule SafeTextInput
 */

'use strict';

var requireNativeComponent = require('requireNativeComponent');
var QEditText = requireNativeComponent('QEditText');
import TextInputState from './TextInputState';
import React, { Component } from 'react';
import PropTypes from 'prop-types';
const ReactNative = require('ReactNative');
const EventEmitter = require('EventEmitter');
const TouchableWithoutFeedback = require('TouchableWithoutFeedback');

export default class QTextInput extends Component {

    static State = {
        currentlyFocusedField: TextInputState.currentlyFocusedField,
        focusTextInput: TextInputState.focusTextInput,
        blurTextInput: TextInputState.blurTextInput,
    }
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

        clearIconMarginRight: PropTypes.number,

        autoFocus: PropTypes.bool
    };

   static contextTypes = {
        onFocusRequested: PropTypes.func,
        focusEmitter:PropTypes.instanceOf(EventEmitter),
      }
    _inputRef;
    _focusSubscription;
    _lastNativeText;
    _lastNativeSelection;

    constructor(props) {
        super(props);
        this._onPress = this._onPress.bind(this);
        this._onFocus = this._onFocus.bind(this);
        this._onBlur = this._onBlur.bind(this);
    }

    render() {
        return (
            <TouchableWithoutFeedback
                onPress={this._onPress}
                onChange={this._onChange}
                onFocus={this._onFocus}
                onBlur={this._onBlur}
            >
                <QEditText
                    // ref={this._setNativeRef}
                    ref={(r) => {
                        // this._inputRef = r
                        this._setNativeRef(r);
                    }
                    }
                    {...this.props}
                />
            </TouchableWithoutFeedback>
        );
    }

    _onChange(event) {
        // Make sure to fire the mostRecentEventCount first so it is already set on
        // native when the text value is set.
        if (this._inputRef) {
            this._inputRef.setNativeProps({
                mostRecentEventCount: event.nativeEvent.eventCount,
            });
        }

        const text = event.nativeEvent.text;
        this.props.onChange && this.props.onChange(event);
        this.props.onChangeText && this.props.onChangeText(text);

        if (!this._inputRef) {
            // calling `this.props.onChange` or `this.props.onChangeText`
            // may clean up the input itself. Exits here.
            return;
        }

        this._lastNativeText = text;
        this.forceUpdate();
    }

    _setNativeRef(ref) {
        this._inputRef = ref;
    }


    componentDidMount() {
        this._lastNativeText = this.props.value;
        const tag = ReactNative.findNodeHandle(this._inputRef);
        if (tag != null) {
            // tag is null only in unit tests
            TextInputState.registerInput(tag);
        }
        if (this.context.focusEmitter) {
            this._focusSubscription = this.context.focusEmitter.addListener(
                'focus',
                el => {
                    if (this === el) {
                        this.requestAnimationFrame(this.focus);
                    } else if (this.isFocused()) {
                        this.blur();
                    }
                },
            );
            if (this.props.autoFocus) {
                this.context.onFocusRequested(this);
            }
        } else {
            if (this.props.autoFocus) {
                this.requestAnimationFrame(this.focus);
            }
        }
    }

    _onFocus(event) {
        alert('qtextinput _onFocus');
        if (this.props.onFocus) {
            this.props.onFocus(event);
        }

        if (this.props.selectionState) {
            this.props.selectionState.focus();
        }
    }

    _onBlur(event) {
        alert('qtextinput _onBlur');
        this.blur();
        if (this.props.onBlur) {
            this.props.onBlur(event);
        }

        if (this.props.selectionState) {
            this.props.selectionState.blur();
        }
    }

    _onPress(event) {
        if (this.props.editable || this.props.editable === undefined) {
            this.focus();
        }
    }

    focus() {
        const tag = ReactNative.findNodeHandle(this._inputRef);
        if (tag != null) {
            // tag is null only in unit tests
            TextInputState.focusTextInput(tag);
        }
    }

    componentWillUnmount() {
        this._focusSubscription && this._focusSubscription.remove();
        if (this.isFocused()) {
          this.blur();
        }
        const tag = ReactNative.findNodeHandle(this._inputRef);
        if (tag != null) {
          TextInputState.unregisterInput(tag);
        }
      }

      isFocused() {
        return (
          TextInputState.currentlyFocusedField() ===
          ReactNative.findNodeHandle(this._inputRef)
        );
      }

}

