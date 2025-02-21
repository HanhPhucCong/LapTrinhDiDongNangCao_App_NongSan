import React from 'react';
import { View, Text, Button } from 'react-native';
import AsyncStorage from '@react-native-async-storage/async-storage';
import { useNavigation } from '@react-navigation/native';
import { CommonActions } from '@react-navigation/native';
import reviewService from '../../service/api/reviewService';
import authService from '../../service/api/authService';
import { SpaceComponent } from '../../components';

const CartScreen = () => {
    const navigation = useNavigation();

    const handleGetReview = () => {
        fetchReviews();
    };

    const fetchReviews = async () => {
        try {
            const response = await reviewService.getAllActive();
            console.log('test call api,check reviews: ', response);
        } catch (err) {
            console.error('loi khi fetch review: ', err);
        }
    };

    const getToken = async () => {
        const token = await AsyncStorage.getItem('token');
        const refreshToken = await AsyncStorage.getItem('refreshToken');
        return { token, refreshToken };
    };

    const handleLogout = async () => {
        try {
            const { token, refreshToken } = await getToken();
            const signoutRequest: any = { token, refreshToken };
            await authService.signout(signoutRequest);

            await AsyncStorage.clear();

            navigation.dispatch(
                CommonActions.reset({
                    index: 0,
                    routes: [{ name: 'LoginScreen' }],
                })
            );
        } catch (err) {
            console.error('Signout failed: ', err);
        }
    };

    return (
        <View style={{ flex: 1, justifyContent: 'center', alignItems: 'center' }}>
            <Text>CartScreen</Text>
            <Button title='Logout' onPress={handleLogout} />
            <SpaceComponent height={16} />
            <Button title='get reviews' onPress={handleGetReview} />
        </View>
    );
};

export default CartScreen;
