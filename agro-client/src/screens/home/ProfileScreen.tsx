import { useFocusEffect } from '@react-navigation/native';
import React, { useState, useEffect, useCallback } from 'react';
import { View, Text, Image, ActivityIndicator, StyleSheet, ScrollView, TouchableOpacity } from 'react-native';
import profileService from '../../service/api/profileService';
import AsyncStorage from '@react-native-async-storage/async-storage';
import { useNavigation } from '@react-navigation/native';

type User = {
    id: number;
    createdAt: string;
    updatedAt: string;
    isActive: boolean;
    isDeleted: boolean;
    fullName: string;
    email: string;
    phoneNumber?: string | null;
    address?: string | null;
    dateOfBirth?: string | null;
    avatarUrl?: string | null;
    role: string;
    isEmailVerified: boolean;
};
const ProfileScreen = ({ navigation }: any) => {
    const [loading, setLoading] = useState<boolean>(true);
    const [userData, setUserData] = useState<User | null>(null);
    const getToken = async () => {
        const token = await AsyncStorage.getItem('token');
        const refreshToken = await AsyncStorage.getItem('refreshToken');
        return { token, refreshToken };
    };

    const fetchUserData = async () => {
        setLoading(true);
        try {
            const data = await profileService.getAllActive();
            setUserData(data.data);
        } catch (error) {
            console.error('Error fetching user data:', error);
        } finally {
            setLoading(false);
        }
    };

    useFocusEffect(
        useCallback(() => {
            fetchUserData();
        }, [])
    );

    return (
        <ScrollView contentContainerStyle={styles.container}>
            {loading ? (
                <ActivityIndicator size='large' color='#007bff' />
            ) : userData ? (
                <View style={styles.profileCard}>
                    <Image
                        source={{ uri: userData.avatarUrl || 'https://via.placeholder.com/120' }}
                        style={styles.avatar}
                    />
                    <Text style={styles.name}>{userData.fullName}</Text>
                    <Text style={styles.email}>{userData.email}</Text>

                    <View style={styles.infoContainer}>
                        <InfoRow label='Số điện thoại' value={userData.phoneNumber || 'Chưa cập nhật'} />
                        <InfoRow label='Địa chỉ' value={userData.address || 'Chưa cập nhật'} />
                        <InfoRow label='Ngày sinh' value={userData.dateOfBirth?.split('T')[0] || 'Chưa cập nhật'} />
                        <InfoRow label='Đơn hàng đang vận chuyển' value={'Chưa có'} />
                        <InfoRow label='Lịch sử mua hàng' value={'Chưa có'} />
                    </View>
                    <TouchableOpacity
                        style={styles.editButton}
                        onPress={() => navigation.navigate('EditProfile', { userData })}
                    >
                        <Text style={styles.editButtonText}>Chỉnh sửa hồ sơ</Text>
                    </TouchableOpacity>
                </View>
            ) : (
                <Text style={styles.errorText}>Không thể tải dữ liệu người dùng.</Text>
            )}
        </ScrollView>
    );
};

type InfoRowProps = {
    label: string;
    value: string;
};

const InfoRow: React.FC<InfoRowProps> = ({ label, value }) => (
    <View style={styles.infoRow}>
        <Text style={styles.infoLabel}>{label}:</Text>
        <Text style={styles.infoValue}>{value}</Text>
    </View>
);

const styles = StyleSheet.create({
    container: { flexGrow: 1, justifyContent: 'center', alignItems: 'center', padding: 20, backgroundColor: '#fff' },
    profileCard: { width: '100%', maxWidth: 400, alignItems: 'center', padding: 20 },
    avatar: { width: 120, height: 120, borderRadius: 60, marginBottom: 15 },
    name: { fontSize: 26, fontWeight: '600', color: '#222', marginBottom: 5 },
    email: { fontSize: 18, color: '#666', marginBottom: 20 },
    infoContainer: { width: '100%', marginTop: 10 },
    infoRow: {
        flexDirection: 'row',
        justifyContent: 'space-between',
        alignItems: 'center',
        paddingVertical: 10,
        borderBottomWidth: 0.5,
        borderBottomColor: '#ccc',
    },
    infoLabel: { fontSize: 16, fontWeight: '500', color: '#444' },
    infoValue: { fontSize: 16, color: '#222' },
    errorText: { color: 'red', fontSize: 16, marginTop: 20 },
    editButton: { backgroundColor: '#007bff', padding: 10, borderRadius: 8, marginTop: 20 },
    editButtonText: { color: '#fff', fontSize: 16, fontWeight: 'bold' },
});
export default ProfileScreen;
