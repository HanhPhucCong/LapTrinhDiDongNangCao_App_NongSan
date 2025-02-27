import axiosClient from './axiosClient';

const passwordService = {
    async sendOtp(email: string) {
        try {
            const response = await axiosClient.post('/api/v1/auth/get-verify', { email });
            return { success: true, data: response.data };
        } catch (error: any) {
            return { success: false, message: error.response?.data?.message || 'Gửi OTP thất bại' };
        }
    },

    async changePassword(userId: string, confirmPassword: string, resetPasswordCode: string) {
        try {
            const response = await axiosClient.patch(`/api/v1/auth/renew-password/${userId}`, {
                password: confirmPassword,
                comfirmPassword: confirmPassword,
                resetPasswordCode
            });
            return { success: true, data: response.data };
        } catch (error: any) {
            console.log(error)
            return { success: false, message: error.response?.data?.message || 'Đổi mật khẩu thất bại' };
        }
    }
};

export default passwordService;
