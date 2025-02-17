import React, { useState, useEffect } from 'react';
import { View, Text, Image, Button, ActivityIndicator, StyleSheet, ScrollView } from 'react-native';
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
    phoneNumber: "0367679213",
    address: "Hồ Chí Minh",
    dateOfBirth: "1995-05-20T14:30",
    avatarUrl: "https://lh3.googleusercontent.com/a/ACg8ocKcsVAukaeyo4gDmJQX5wTtciMVoUiLdc7daPa5fgr4w21qExc=s192-c-mo",
    role: 'CUSTOMER',
    isEmailVerified: true
  };
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    setTimeout(() => {
      setLoading(false);
    }, 1000);
  }, []);

  return (
    <ScrollView contentContainerStyle={styles.container}>
      {loading ? (
        <ActivityIndicator size="large" color="#0000ff" />
      ) : (
        <View style={styles.profileContainer}>
          <Image source={{ uri: userData.avatarUrl }} style={styles.avatar} />
          <Text style={styles.name}>{userData.fullName}</Text>
          <Text style={styles.email}>{userData.email}</Text>
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
    backgroundColor: '#f8f9fa',
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
  avatar: {
    width: 100,
    height: 100,
    borderRadius: 50,
    marginBottom: 15,
  },
  name: {
    fontSize: 22,
    fontWeight: 'bold',
    color: '#333',
    marginBottom: 5,
  },
  email: {
    fontSize: 16,
    color: '#666',
    marginBottom: 20,
  },
});

export default ProfileScreen;