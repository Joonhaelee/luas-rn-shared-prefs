import RnSharedPrefs from './NativeRnSharedPrefs';

export function multiply(a: number, b: number): number {
  return RnSharedPrefs.multiply(a, b);
}
