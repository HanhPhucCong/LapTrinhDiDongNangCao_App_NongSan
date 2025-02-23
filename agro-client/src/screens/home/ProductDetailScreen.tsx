import React, { useState } from 'react';
import { View, Text, Image, StyleSheet, ScrollView, TouchableOpacity, Dimensions } from 'react-native';
import { RouteProp, useRoute, useNavigation } from '@react-navigation/native';
import Icon from 'react-native-vector-icons/AntDesign';
import formatCurrency from '../../utils/formatCurrency';
import cartService from '../../service/api/cartService';
import favoriteService from '../../service/api/favoriteService';
import { showMessage, hideMessage } from 'react-native-flash-message';

// lấy chiều rộng màn hình để làm slideshow, css các kiểu
const { width } = Dimensions.get('window');

interface Product {
    id: number;
    name: string;
    description: string;
    price: number;
    unit: string;
    imageUrls: string[];
    quantity: number;
}

type ProductDetailRouteProp = RouteProp<{ ProductDetail: { product: Product } }, 'ProductDetail'>;

const ProductDetailScreen = () => {
    const route = useRoute<ProductDetailRouteProp>();
    const { product } = route.params;
    const navigation = useNavigation();

    const [activeSlide, setActiveSlide] = useState(0);

    // event.nativeEvent.contentOffset.x: toạ đọ X hiện tại
    const handleScroll = (event: any) => {
        const slideIndex = Math.round(event.nativeEvent.contentOffset.x / width);
        setActiveSlide(slideIndex);
    };

    const handleAddToCart = async () => {
        if (product.quantity === 0) {
            showMessage({
                message: 'This product is out of stock!',
                type: 'danger',
                backgroundColor: '#dc3545',
            });
            return;
        }

        try {
            await cartService.addToCart(product.id);
            showMessage({
                message: 'Added to cart successfully!',
                type: 'success',
                backgroundColor: '#28a745',
            });
        } catch (error: any) {
            showMessage({
                message: error.response?.data?.message || 'Failed to add to cart!',
                type: 'danger',
                backgroundColor: '#dc3545',
            });
        }
    };

    const handleAddToFavorite = async () => {
        try {
            await favoriteService.addToFavorite(product.id);
            showMessage({
                message: 'Added to favorites successfully!',
                type: 'success',
                backgroundColor: '#ff5733',
            });
        } catch (error: any) {
            showMessage({
                message: error.response?.data?.message || 'Failed to add to favorites!',
                type: 'danger',
                backgroundColor: '#dc3545',
            });
        }
    };

    return (
        <ScrollView style={styles.container}>
            {/* Nút Back */}
            <TouchableOpacity style={styles.backButton} onPress={() => navigation.goBack()}>
                <Icon name='arrowleft' size={24} color='#000' />
            </TouchableOpacity>

            {/* Slideshow hình ảnh (kéo tay) */}
            <ScrollView
                horizontal
                pagingEnabled
                showsHorizontalScrollIndicator={false}
                onScroll={handleScroll}
                scrollEventThrottle={16}
                style={styles.imageContainer}
            >
                {product.imageUrls.map((image, index) => (
                    <Image key={index} source={{ uri: image }} style={styles.image} />
                ))}
            </ScrollView>

            {/* Pagination (dấu chấm) */}
            <View style={styles.pagination}>
                {product.imageUrls.map((_, index) => (
                    <View key={index} style={[styles.dot, activeSlide === index && styles.activeDot]} />
                ))}
            </View>

            {/* Thông tin sản phẩm */}
            <View style={styles.infoContainer}>
                <Text style={styles.name}>{product.name}</Text>
                <Text style={styles.price}>
                    {formatCurrency(product.price)} <Text style={styles.unit}>/ {product.unit}</Text>
                </Text>
                <Text style={[styles.quantity, product.quantity === 0 && styles.outOfStock]}>
                    {product.quantity > 0 ? `In Stock: ${product.quantity}` : 'Out of stock'}
                </Text>
                <Text style={styles.description}>{product.description}</Text>
            </View>

            {/* Nút chức năng */}
            <View style={styles.buttonContainer}>
                <TouchableOpacity style={styles.favoriteButton} onPress={handleAddToFavorite}>
                    <Icon name='hearto' size={20} color='#ff5733' />
                    <Text style={styles.buttonText}>Favorite</Text>
                </TouchableOpacity>
                <TouchableOpacity
                    style={[styles.cartButton, product.quantity === 0 && styles.disabledButton]}
                    onPress={handleAddToCart}
                    disabled={product.quantity === 0}
                >
                    <Icon name='shoppingcart' size={20} color='#fff' />
                    <Text style={styles.buttonTextWhite}>Add to Cart</Text>
                </TouchableOpacity>
            </View>
        </ScrollView>
    );
};

const styles = StyleSheet.create({
    container: { flex: 1, backgroundColor: '#fff' },
    backButton: {
        position: 'absolute',
        top: 60,
        left: 16,
        backgroundColor: 'rgba(255, 255, 255, 0.7)',
        padding: 8,
        borderRadius: 50,
        zIndex: 10,
    },
    imageContainer: { width, height: 300 },
    image: { width, height: 300, resizeMode: 'cover' },
    pagination: { flexDirection: 'row', justifyContent: 'center', marginTop: 10 },
    dot: { width: 8, height: 8, borderRadius: 4, backgroundColor: '#ddd', marginHorizontal: 4 },
    activeDot: { backgroundColor: '#ff5733' },
    infoContainer: { paddingHorizontal: 16, marginTop: 20 },
    name: { fontSize: 22, fontWeight: 'bold', marginBottom: 10, textAlign: 'left' },
    price: { fontSize: 18, color: '#ff5733', marginBottom: 10, textAlign: 'left' },
    unit: { fontSize: 16, color: '#777' },
    description: { fontSize: 16, color: '#555', textAlign: 'left' },
    buttonContainer: { flexDirection: 'row', justifyContent: 'space-between', marginTop: 20, paddingHorizontal: 16 },
    favoriteButton: {
        flexDirection: 'row',
        alignItems: 'center',
        borderWidth: 1,
        borderColor: '#ff5733',
        paddingVertical: 10,
        paddingHorizontal: 20,
        borderRadius: 10,
    },
    cartButton: {
        flexDirection: 'row',
        alignItems: 'center',
        backgroundColor: '#ff5733',
        paddingVertical: 10,
        paddingHorizontal: 20,
        borderRadius: 10,
    },
    buttonText: { marginLeft: 8, fontSize: 16, color: '#ff5733' },
    buttonTextWhite: { marginLeft: 8, fontSize: 16, color: '#fff' },
    quantity: { fontSize: 14, color: '#555', marginBottom: 10 },
    outOfStock: { color: '#dc3545', fontWeight: 'bold' },
    disabledButton: { backgroundColor: '#ccc' },
});

export default ProductDetailScreen;
