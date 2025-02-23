import axiosClient from './axiosClient';

const cartService = {
    addToCart(productId: number) {
        const url = `/api/cart/increment/${productId}`;
        return axiosClient.put(url);
    },
};

export default cartService;
