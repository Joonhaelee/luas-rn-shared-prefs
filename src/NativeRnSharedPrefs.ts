import { TurboModuleRegistry, type TurboModule } from 'react-native';

export interface Spec extends TurboModule {
  init(prefsName: string): void;
  setItem(key: string, value: string): void;
  getItem(key: string): string | undefined;
  deleteItem(key: string): void;
  getKeys(): Array<string>;
  getItems(): Array<{ key: string; value: string }>;
  clear(): void;

  isWidgetPlaced(pkg: string, widgetCls: string): Promise<boolean>;
  pinWidget(
    pkg: string,
    widgetCls: string,
    widgetLayoutResouceName?: string
  ): Promise<boolean>;
}

export default TurboModuleRegistry.getEnforcing<Spec>('NativeRNSharedPrefs');
