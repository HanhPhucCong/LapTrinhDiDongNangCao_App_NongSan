import axiosClient from './axiosClient';

const authService = {
    login: (email: string, password: string) => {
        return axiosClient.post('/v1/auth/signin', { email, password });
    },
    register: (fullName: string, email: string, password: string, passwordConfirm: string) => {
        return axiosClient.post('/v1/auth/signup', { fullName, email, password, passwordConfirm });
    },
    verify(code: string, userId: string) {
        return axiosClient.post(`/v1/auth/signup/${userId}`, { code });
    },
    getVerify(email: string) {
        return axiosClient.post('/v1/auth/get-verify', { email });
    },
    resetPassword(password: string, comfirmPassword: string, resetPasswordCode: string, userId: string) {
        return axiosClient.patch(`/v1/auth/renew-password/${userId}`, { password, comfirmPassword, resetPasswordCode });
    },
};

export default authService;
