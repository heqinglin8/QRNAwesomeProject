/**
 * @providesModule SafeTextInputState
 */
import { findNodeHandle, NativeModules } from 'react-native';
// const TextInputState = require('TextInputState');
// _focusTarget && TextInputState.blurTextInput(_focusTarget);

var _focusTarget;

function findNumericNodeHandleFiber(componentOrHandle) {
    componentOrHandle = findNodeHandle(componentOrHandle);
    return null == componentOrHandle || "number" === typeof componentOrHandle
        ? componentOrHandle
        : componentOrHandle._nativeTag;
}

export function updateFocusTarget(target, focus) {
    _focusTarget = focus ? target : null;
}

export function blurSafeTextInput() {
    if (!_focusTarget) {
        return;
    }
    try {
        const reactTag = Number(findNumericNodeHandleFiber(_focusTarget));
        _focusTarget && NativeModules.FRNSupportModule &&
            NativeModules.FRNSupportModule.dismissSafeKeyboard(reactTag);
    } catch(err) {
        console.warn(`blueSafeTextInput error...${err.toString()}`);
        _focusTarget = null;
    }
}

export function hasSafeInputActived() {
    return _focusTarget !== null && _focusTarget !== undefined;
}