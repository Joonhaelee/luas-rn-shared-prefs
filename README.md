# @luas/rn-shared-prefs

react native simple share preference with turbo module

- for android only

## Installation

```sh
npm install @luas/rn-shared-prefs
or
yarn add @luas/rn-shared-prefs
```

## Usage in native (kotlin)

#### listening any value changed

```kotlin
# on MainActivity.kt or others
  var sharedPreferences: SharedPreferences? = null
  // 리스너를 멤버 변수로 저장 (중요: 약한 참조 방지)
  var listener: SharedPreferences.OnSharedPreferenceChangeListener? = null;

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    this.sharedPreferences = this.getSharedPreferences("PrefsName", Context.MODE_PRIVATE);
    this.listener = SharedPreferences.OnSharedPreferenceChangeListener { sharedPreferences, key ->
      Log.d("RNSharedPrefsExample", "PrefsName:${key} changed: ${sharedPreferences.getString(key, "")}")
    }
    sharedPreferences?.registerOnSharedPreferenceChangeListener(listener)
  }

  override fun onDestroy() {
    sharedPreferences?.unregisterOnSharedPreferenceChangeListener(listener)

    super.onDestroy()
  }

```

## Usage in react native

```typescript
import React from 'react';
import {
  Text,
  View,
  StyleSheet,
  ScrollView,
  Pressable,
  Alert,
} from 'react-native';
import {
  init,
  setItem,
  getItem,
  deleteItem,
  getKeys,
  getItems,
  clear,
} from '@luas/rn-shared-prefs';

export default function App() {
  const [initialized, setInitialized] = React.useState<boolean>(false);
  const [seq, setSeq] = React.useState<number>(0);
  return (
    <View style={styles.container}>
      <Text style={styles.text}>{`${
        initialized ? 'initialized' : 'NOT initialized'
      }`}</Text>
      {seq ? (
        <Text style={styles.text}>{`latest set key: "key${seq}"`}</Text>
      ) : undefined}
      <ScrollView contentContainerStyle={styles.container}>
        <Pressable
          onPress={() => {
            init('PrefsName');
            setInitialized(true);
          }}
          style={styles.button}
        >
          <Text style={styles.buttonText}>init</Text>
        </Pressable>
        <Pressable
          onPress={() => {
            setItem(`key${seq + 1}`, `${Math.floor(Math.random() * 1000000)}`);
            setSeq((prev) => prev + 1);
          }}
          style={styles.button}
        >
          <Text style={styles.buttonText}>setItem</Text>
        </Pressable>
        <Pressable
          onPress={() => {
            const v = getItem(`key${seq}}`);
            Alert.alert(`getItem() returned ${v}`);
          }}
          style={styles.button}
        >
          <Text style={styles.buttonText}>getItem</Text>
        </Pressable>
        <Pressable
          onPress={() => {
            deleteItem(`key${seq}}`);
          }}
          style={styles.button}
        >
          <Text style={styles.buttonText}>deleteItem</Text>
        </Pressable>
        <Pressable
          onPress={() => {
            const keys = getKeys();
            console.log('getKeys()', keys);
            Alert.alert(`getKeys() returned`, `${keys.join(`\n`)}`);
          }}
          style={styles.button}
        >
          <Text style={styles.buttonText}>getKeys</Text>
        </Pressable>
        <Pressable
          onPress={() => {
            const entries = getItems();
            console.log('getItems()', entries);
            Alert.alert(
              `getItems() returned`,
              `${entries
                .map((entry) => `${entry.key}: ${entry.value}`)
                .join(`\n`)}`
            );
          }}
          style={styles.button}
        >
          <Text style={styles.buttonText}>getItems</Text>
        </Pressable>
        <Pressable
          onPress={() => {
            clear();
            setSeq(0);
          }}
          style={styles.button}
        >
          <Text style={styles.buttonText}>clear</Text>
        </Pressable>
      </ScrollView>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: 'stretch',
    padding: 16,
  },
  button: {
    backgroundColor: '#2196F3',
    alignItems: 'center',
    justifyContent: 'center',
    minHeight: 46,
    paddingVertical: 8,
    marginVertical: 8,
  },
  buttonText: {
    fontSize: 16,
    color: '#FFF',
    textAlign: 'center',
  },
  text: {
    fontSize: 16,
    color: '#00F',
    textAlign: 'center',
    marginVertical: 8,
  },
});
```

## Contributing

- [Development workflow](CONTRIBUTING.md#development-workflow)
- [Sending a pull request](CONTRIBUTING.md#sending-a-pull-request)
- [Code of conduct](CODE_OF_CONDUCT.md)

## License

MIT

---

Made with [create-react-native-library](https://github.com/callstack/react-native-builder-bob)
