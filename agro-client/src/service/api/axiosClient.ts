import axios from 'axios';
import AsyncStorage from '@react-native-async-storage/async-storage';

// Khai báo base URL cho API
const axiosClient = axios.create({
    baseURL: 'http://10.0.2.2:8083/api', // url chạy bằng simulator
    headers: {
        'Content-Type': 'application/json',
    },
    timeout: 10000, // Timeout sau 10 giây
});

// Interceptor để tự động thêm Token vào request
axiosClient.interceptors.request.use(
    async (config) => {
        const token = await AsyncStorage.getItem('token'); // Lấy token từ AsyncStorage
        if (token) {
            config.headers.Authorization = `Bearer ${token}`;
        }
        return config;
    },
    (error) => {
        return Promise.reject(error);
    }
);

// Interceptor để xử lý lỗi từ API
axiosClient.interceptors.response.use(
    (response) => {
        return response.data; // Chỉ trả về data, bỏ qua response headers
    },
    async (error) => {
        if (error.response) {
            if (error.response.status === 401) {
                // Token hết hạn, có thể điều hướng đến màn hình Login
                console.log('Unauthorized! Redirecting to login...');
                await AsyncStorage.removeItem('token');
            }
        }
        return Promise.reject(error);
    }
);

export default axiosClient;
