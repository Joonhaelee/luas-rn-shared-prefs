import NativeRNSharedPrefs from './NativeRNSharedPrefs';

export function init(prefsName: string) {
  return NativeRNSharedPrefs.init(prefsName);
}

export function getItem(key: string) {
  return NativeRNSharedPrefs.getItem(key);
}

export function setItem(key: string, value: string) {
  NativeRNSharedPrefs.setItem(key, value);
}

export function deleteItem(key: string) {
  return NativeRNSharedPrefs.deleteItem(key);
}

export function getKeys() {
  return NativeRNSharedPrefs.getKeys();
}

export function getItems() {
  return NativeRNSharedPrefs.getItems();
}

export function clear() {
  return NativeRNSharedPrefs.clear();
}
