import React, { useState } from 'react';
import { View, Text, TextInput, FlatList, TouchableOpacity, StyleSheet, Image } from 'react-native';
import { Ionicons } from '@expo/vector-icons';

const SearchScreen = () => {
    const [searchQuery, setSearchQuery] = useState('');
    const [results, setResults] = useState<{ id: string; name: string; price: string; image: string; }[]>([]);
    const [isSearching, setIsSearching] = useState(false);

    const sampleData = [
        { id: '1', name: 'Apple', price: '$1.00', image: 'https://5.imimg.com/data5/SELLER/Default/2022/9/YR/EC/LJ/90370493/fresh-red-apple-fruit-500x500.jpg' },
        { id: '2', name: 'Banana', price: '$0.50', image: 'https://res.cloudinary.com/syndigo/image/fetch/f_jpg/https://assets.edgenet.com/1a679ea7-d52f-412e-afd5-3b5511963dc4%3Fsize=600x600' },
        { id: '3', name: 'Orange', price: '$0.80', image: 'https://media.officedepot.com/images/f_auto,q_auto,e_sharpen,h_450/products/7053384/7053384_o02/7053384' },
        { id: '4', name: 'Mango', price: '$1.50', image: 'https://media.istockphoto.com/id/2148796519/photo/fresh-alphonso-mango-fruit-white-background.jpg?s=612x612&w=0&k=20&c=0bPTgPVGCfv1a_LigBD3_QOEIEjQsacdQ5PcEPXCETk=' },
        { id: '5', name: 'Grapes', price: '$2.00', image: 'https://5.imimg.com/data5/SELLER/Default/2023/6/319559224/UQ/TJ/FO/37116244/fresh-black-grapes-fruit.jpg' },
        { id: '6', name: 'Pineapple', price: '$3.00', image: 'https://static.scientificamerican.com/sciam/cache/file/4356A21C-9B93-4EC9-9D80998F7FB1FBF2_source.jpg?w=1200' },
        { id: '7', name: 'Strawberry', price: '$2.50', image: 'https://4.imimg.com/data4/RT/SF/ANDROID-40754382/product-500x500.jpeg' },
        { id: '8', name: 'Watermelon', price: '$4.00', image: 'https://5.imimg.com/data5/IJ/WY/MY-5553683/fresh-watermelon-500x500.jpg' },
    ];

    const handleSearch = (query: string) => {
        setSearchQuery(query);
        if (query.trim() === '') {
            setResults([]);
            setIsSearching(false);
            return;
        }
        setIsSearching(true);
        const filteredResults = sampleData.filter(item => 
            item.name.toLowerCase().includes(query.toLowerCase())
        );
        setResults(filteredResults);
    };

    return (
        <View style={styles.container}>
            <View style={styles.searchContainer}>
                <Ionicons name="search" size={20} color="gray" style={styles.searchIcon} />
                <TextInput
                    style={styles.searchInput}
                    placeholder="Nhập từ khóa tìm kiếm..."
                    value={searchQuery}
                    onChangeText={handleSearch}
                />
            </View>

            {isSearching && results.length === 0 ? (
                <Text style={styles.noResults}>Không tìm thấy kết quả.</Text>
            ) : (
                <FlatList
                    data={isSearching ? results : sampleData}
                    keyExtractor={(item) => item.id}
                    numColumns={2}
                    renderItem={({ item }) => (
                        <TouchableOpacity style={styles.productItem}>
                            <Image source={{ uri: item.image }} style={styles.productImage} />
                            <Text style={styles.productName}>{item.name}</Text>
                            <Text style={styles.productPrice}>{item.price}</Text>
                        </TouchableOpacity>
                    )}
                />
            )}
        </View>
    );
};

const styles = StyleSheet.create({
    container: {
        flex: 1,
        padding: 20,
        backgroundColor: '#fff',
    },
    searchContainer: {
        flexDirection: 'row',
        alignItems: 'center',
        borderWidth: 1,
        borderColor: '#ccc',
        borderRadius: 8,
        paddingHorizontal: 15,
        height: 50,
        marginBottom: 15,
    },
    searchIcon: {
        marginRight: 10,
    },
    searchInput: {
        flex: 1,
        fontSize: 16,
    },
    noResults: {
        textAlign: 'center',
        marginTop: 20,
        fontSize: 16,
        color: 'gray',
    },
    productItem: {
        flex: 1,
        alignItems: 'center',
        padding: 10,
        margin: 5,
        borderRadius: 10,
        backgroundColor: '#f9f9f9',
        elevation: 2,
    },
    productImage: {
        width: 100,
        height: 100,
        borderRadius: 10,
    },
    productName: {
        fontSize: 16,
        fontWeight: 'bold',
        marginTop: 5,
    },
    productPrice: {
        fontSize: 14,
        color: 'green',
        marginTop: 3,
    },
});

export default SearchScreen;