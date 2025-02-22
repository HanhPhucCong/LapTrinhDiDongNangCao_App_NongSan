import axios from 'axios';
import AsyncStorage from '@react-native-async-storage/async-storage';
import authService from './authService';
import { resetToLogin } from '../../navigators/RootNavigation';

// Tạo axios instance
const axiosClient = axios.create({
    baseURL: 'http://localhost:8083/api', // URL chạy trên simulator
    headers: {
        'Content-Type': 'application/json',
    },
    timeout: 10000,
});


// Biến kiểm soát refresh token để tránh gọi nhiều lần cùng lúc
let isRefreshing = false;
let refreshSubscribers: ((token: string) => void)[] = [];

// Hàm lưu token mới vào AsyncStorage
const saveToken = async (token: string, refreshToken: string) => {
    await AsyncStorage.setItem('token', token);
    await AsyncStorage.setItem('refreshToken', refreshToken);
};

// Hàm gọi API refresh token
const refreshAccessToken = async () => {
    try {
        const refreshToken = await AsyncStorage.getItem('refreshToken');
        if (!refreshToken) throw new Error('No refresh token available');

        console.log('🔄 Gửi request refresh token...');
        const response = await authService.refreshToken(refreshToken);
        console.log('✅ Refresh token thành công:', response.data);

        const newAccessToken = response.data.token;
        const newRefreshToken = response.data.refreshToken;
        await saveToken(newAccessToken, newRefreshToken);

        // Gửi lại các request bị chặn sau khi refresh thành công
        refreshSubscribers.forEach((callback) => callback(newAccessToken));
        refreshSubscribers = [];

        return newAccessToken;
    } catch (error) {
        console.log('❌ Refresh token thất bại:', error);
        await AsyncStorage.removeItem('token');
        await AsyncStorage.removeItem('refreshToken');
        resetToLogin();
        return null;
    } finally {
        isRefreshing = false;
    }
};

// Thêm token vào request
axiosClient.interceptors.request.use(
    async (config) => {
        const token = await AsyncStorage.getItem('token');
        if (token) {
            config.headers.Authorization = `Bearer ${token}`;
        }
        return config;
    },
    (error) => Promise.reject(error)
);

axiosClient.interceptors.response.use(
    (response) => response.data, // Chỉ lấy data, bỏ qua headers
    async (error) => {
        const originalRequest = error.config;

        // Lỗi 401 thì gọi refreshToken
        if (error.response?.status === 401 && !originalRequest._retry) {
            // Nếu token hết hạn, đánh dấu request để retry sau khi refresh thành công
            originalRequest._retry = true;

            if (isRefreshing) {
                return new Promise((resolve) => {
                    refreshSubscribers.push((newToken) => {
                        originalRequest.headers.Authorization = `Bearer ${newToken}`;
                        resolve(axiosClient(originalRequest));
                    });
                });
            }

            isRefreshing = true;
            const newToken = await refreshAccessToken();

            if (newToken) {
                originalRequest.headers.Authorization = `Bearer ${newToken}`;
                return axiosClient(originalRequest);
            }
        }

        return Promise.reject(error);
    }
);

export default axiosClient;
