import React, { useEffect, useState } from 'react';
import { SplashScreen } from './src/screens';
import { NavigationContainer } from '@react-navigation/native';
import AuthNavigator from './src/navigators/AuthNavigator';
import { StatusBar } from 'react-native';
import MainNavigator from './src/navigators/MainNavigator';
import { useAsyncStorage } from '@react-native-async-storage/async-storage';
const App = () => {
  // Sử dụng useState để lưu thời gian 1.5 giây
  const [isShowSplash, setIsShowSplash] = useState(true);
  const [accessToken, setAccessToken] = useState('');
  const { getItem, setItem } = useAsyncStorage('assetToken');

  useEffect(() => {
    const timeout = setTimeout(() => {
      setIsShowSplash(false);
    }, 1500);

    return () => clearTimeout(timeout);
  }, []);

  useEffect(() => {
    checkLogin();
  }, []);

  // Hàm kiểm tra đăng nhập
  const checkLogin = async () => {
    const token = await getItem();
    console.log(token);
    // Kiểm tra token và lưu vào state
    token && setAccessToken(token);
  };
  // Điều chỉnh thanh trạng thái
  return (
    <>
      <StatusBar barStyle={'dark-content'} translucent backgroundColor={'transparent'} />
      {isShowSplash ? (
        <SplashScreen />
      ) : (
        <NavigationContainer>
          {accessToken ? <MainNavigator /> : <AuthNavigator />}
        </NavigationContainer>
      )}
    </>
  );
};

export default App;