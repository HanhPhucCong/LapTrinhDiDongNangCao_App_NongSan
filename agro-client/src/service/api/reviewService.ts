import axiosClient from './axiosClient';

const reviewService = {
    getAllActive() {
        const url = '/review/get-all-active/1';
        return axiosClient.get(url);
    },
};

export default reviewService;
