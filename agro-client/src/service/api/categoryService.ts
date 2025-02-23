import axiosClient from './axiosClient';

const categoryService = {
    getAll() {
        const url = '/admin/categories';
        return axiosClient.get(url);
    },
};

export default categoryService;
