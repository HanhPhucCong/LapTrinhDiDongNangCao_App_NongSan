import axiosClient from './axiosClient';

const profileService = {
    getAllActive() {
        const url = '/api/user/my-profile';
        return axiosClient.get(url);
    },
    async updateProfile(formData: FormData) {
        try {
            const response = await axiosClient.put('/api/user/update-profile', formData, {
                headers: {
                    'Content-Type': 'multipart/form-data',
                },
            });
            return { success: true, data: response };
        } catch (error: any) {
            console.log(e)
            return { success: false, message: error.response?.data?.message || 'Cập nhật không thành công' };
        }
    },
};

export default profileService;
