import React, { useState, useEffect } from 'react';
import { View, Text, Button, ActivityIndicator, StyleSheet, ScrollView } from 'react-native';
import AsyncStorage from '@react-native-async-storage/async-storage';

const ProfileScreen = () => {
  // Dữ liệu giả
  const userData = {
    id: 3,
    createdAt: '2025-02-17T09:44:47.057096',
    updatedAt: '2025-02-17T09:46:02.872226',
    isActive: true,
    isDeleted: false,
    fullName: 'Huynh Minh Tri',
    email: 'hanhphucong28122003@gmail.com',
    phoneNumber: null,
    address: null,
    dateOfBirth: null,
    avatarUrl: null,
    role: 'CUSTOMER',
    isEmailVerified: true
  };

  const [loading, setLoading] = useState(false);

  useEffect(() => {
    // Giả lập quá trình tải dữ liệu
    setLoading(true);
    setTimeout(() => {
      setLoading(false);
    }, 1000); // Đợi 1 giây để giả lập việc tải dữ liệu
  }, []);

  return (
    <ScrollView contentContainerStyle={styles.container}>
      {loading ? (
        <ActivityIndicator size="large" color="#0000ff" />
      ) : (
        <View style={styles.profileContainer}>
          <Text style={styles.heading}>Profile Information</Text>
          <View style={styles.infoContainer}>
            <Text style={styles.infoText}>Full Name: <Text style={styles.boldText}>{userData.fullName}</Text></Text>
            <Text style={styles.infoText}>Email: <Text style={styles.boldText}>{userData.email}</Text></Text>
            <Text style={styles.infoText}>Role: <Text style={styles.boldText}>{userData.role}</Text></Text>
            {/* Các thông tin khác */}
          </View>
          <Button
            title="Logout"
            onPress={async () => {
              await AsyncStorage.clear();
            }}
            color="#d9534f"
          />
        </View>
      )}
    </ScrollView>
  );
};

const styles = StyleSheet.create({
  container: {
    flexGrow: 1,
    justifyContent: 'center',
    alignItems: 'center',
    padding: 20,
  },
  profileContainer: {
    width: '100%',
    maxWidth: 400,
    alignItems: 'center',
    padding: 20,
    backgroundColor: '#fff',
    borderRadius: 10,
    shadowColor: '#000',
    shadowOpacity: 0.1,
    shadowRadius: 10,
    shadowOffset: { width: 0, height: 10 },
    elevation: 5,
  },
  heading: {
    fontSize: 24,
    fontWeight: 'bold',
    marginBottom: 20,
  },
  infoContainer: {
    width: '100%',
    marginBottom: 20,
  },
  infoText: {
    fontSize: 16,
    marginVertical: 5,
  },
  boldText: {
    fontWeight: 'bold',
  },
});

export default ProfileScreen;
