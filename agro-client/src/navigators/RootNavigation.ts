import { createNavigationContainerRef, CommonActions } from '@react-navigation/native';
import { showMessage } from 'react-native-flash-message';

// Định nghĩa kiểu cho các màn hình trong stack
type RootStackParamList = {
    LoginScreen: undefined;
    HomeScreen: undefined;
};

// Tạo navigationRef với kiểu dữ liệu đúng
export const navigationRef = createNavigationContainerRef<RootStackParamList>();

export function navigate<RouteName extends keyof RootStackParamList>(
    name: RouteName,
    params?: RootStackParamList[RouteName]
) {
    if (navigationRef.isReady()) {
        navigationRef.navigate(name, params);
    }
}

export async function resetToLogin() {
    showMessage({
        message: 'Login session expired',
        description: 'Please log in again!',
        type: 'danger',
        icon: 'danger',
        duration: 3000,
    });

    // Chuyển về màn hình Login
    if (navigationRef.isReady()) {
        navigationRef.dispatch(
            CommonActions.reset({
                index: 0,
                routes: [{ name: 'LoginScreen' }],
            })
        );
    }
}
