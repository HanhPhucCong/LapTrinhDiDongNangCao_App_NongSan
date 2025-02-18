import React from 'react';
import { View, Text, Button } from 'react-native';
import AsyncStorage from '@react-native-async-storage/async-storage';
import { useNavigation } from '@react-navigation/native';
import { CommonActions } from '@react-navigation/native';

const HomeScreen = () => {
    const navigation = useNavigation();

    const handleLogout = async () => {
        await AsyncStorage.clear();
        navigation.dispatch(
            CommonActions.reset({
                index: 0,
                routes: [{ name: 'LoginScreen' }],
            })
        );
    };

    return (
        <View style={{ flex: 1, justifyContent: 'center', alignItems: 'center' }}>
            <Text>HomeScreen</Text>
            <Button title='Logout' onPress={handleLogout} />
        </View>
    );
};

export default HomeScreen;
