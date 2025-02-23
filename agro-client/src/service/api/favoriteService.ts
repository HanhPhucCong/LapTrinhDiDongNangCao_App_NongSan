import axiosClient from './axiosClient';

const favoriteService = {
    addToFavorite(productId: number) {
        const url = `/api/favorite/add/${productId}`;
        return axiosClient.put(url);
    },
};

export default favoriteService;
