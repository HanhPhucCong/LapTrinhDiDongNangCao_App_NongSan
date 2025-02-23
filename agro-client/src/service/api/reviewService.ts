import axiosClient from './axiosClient';

const reviewService = {
    getAllActive() {
        const url = '/api/review/get-all-active/2';
        return axiosClient.get(url);
    },
};

export default reviewService;
