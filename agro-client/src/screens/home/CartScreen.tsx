import React from 'react';
import { View, Text, Button } from 'react-native';
import AsyncStorage from '@react-native-async-storage/async-storage';

const CartScreen = () => {
  return (
    <View style={{ flex: 1, justifyContent: 'center', alignItems: 'center' }}>
      <Text>CartScreen</Text>
      <Button
        title="Logout"
        onPress={async () => {
          await AsyncStorage.clear();
        }}
      />
    </View>
  );
};

export default CartScreen;