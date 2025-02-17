import React, { useState, useEffect } from 'react';
import { View, Text, Image, ActivityIndicator, StyleSheet, ScrollView, TouchableOpacity, TextInput } from 'react-native';

const API_URL = 'http://localhost:8081/api/user/my-profile';
const BEARER_TOKEN = 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJoYW5ocGh1Y29uZzI4MTIyMDAzQGdtYWlsLmNvbSIsImlhdCI6MTczOTc3ODI1MCwiZXhwIjoxNzM5Nzc5NjkwfQ.HljuB2BDU5MEUMZKAlHOZXQC1NTZJFlMGfKX1nCDghM';

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

const ProfileScreen: React.FC = () => {
  const [loading, setLoading] = useState<boolean>(true);
  const [userData, setUserData] = useState<User | null>(null);
  const [isEditing, setIsEditing] = useState<boolean>(false);
  const [editedData, setEditedData] = useState<User | null>(null);

  useEffect(() => {
    fetch(API_URL, {
      method: 'GET',
      headers: {
        'Authorization': `Bearer ${BEARER_TOKEN}`,
        'Content-Type': 'application/json',
      },
    })
      .then((response) => response.json())
      .then((data) => {
        setUserData(data.Data);
        setEditedData(data.Data);
      })
      .catch((error) => console.error('Error fetching user data:', error))
      .finally(() => setLoading(false));
  }, []);

  const handleEditPress = () => {
    setIsEditing(true);
  };

  const handleSavePress = () => {
    if (editedData) {
      setUserData(editedData);
      setIsEditing(false);
    }
  };

  const handleChange = (field: keyof User, value: string) => {
    if (editedData) {
      setEditedData({ ...editedData, [field]: value });
    }
  };

  return (
    <ScrollView contentContainerStyle={styles.container}>
      {loading ? (
        <ActivityIndicator size="large" color="#007bff" />
      ) : userData ? (
        <View style={styles.profileContainer}>
          <Image source={{ uri: userData.avatarUrl || 'https://via.placeholder.com/120' }} style={styles.avatar} />
          {isEditing ? (
            <TextInput
              style={styles.input}
              value={editedData?.fullName || ''}
              onChangeText={(text) => handleChange('fullName', text)}
            />
          ) : (
            <Text style={styles.name}>{userData.fullName}</Text>
          )}
          <Text style={styles.email}>{userData.email}</Text>

          <View style={styles.infoContainer}>
            <InfoRow label="Số điện thoại" value={userData.phoneNumber || 'Chưa cập nhật'} isEditing={isEditing} onChange={(text) => handleChange('phoneNumber', text)} />
            <InfoRow label="Địa chỉ" value={userData.address || 'Chưa cập nhật'} isEditing={isEditing} onChange={(text) => handleChange('address', text)} />
            <InfoRow label="Ngày sinh" value={userData.dateOfBirth?.split('T')[0] || 'Chưa cập nhật'} isEditing={isEditing} onChange={(text) => handleChange('dateOfBirth', text)} />
          </View>

          {isEditing ? (
            <TouchableOpacity style={styles.saveButton} onPress={handleSavePress}>
              <Text style={styles.buttonText}>Lưu</Text>
            </TouchableOpacity>
          ) : (
            <TouchableOpacity style={styles.editButton} onPress={handleEditPress}>
              <Text style={styles.buttonText}>Chỉnh Sửa thông tin</Text>
            </TouchableOpacity>
          )}
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
  isEditing?: boolean;
  onChange?: (text: string) => void;
};

const InfoRow: React.FC<InfoRowProps> = ({ label, value, isEditing = false, onChange }) => (
  <View style={styles.infoRow}>
    <Text style={styles.infoLabel}>{label}:</Text>
    {isEditing ? (
      <TextInput style={styles.input} value={value} onChangeText={onChange} />
    ) : (
      <Text style={styles.infoValue}>{value}</Text>
    )}
  </View>
);

const styles = StyleSheet.create({
  container: { flexGrow: 1, justifyContent: 'center', alignItems: 'center', padding: 20, backgroundColor: '#f2f2f2' },
  profileContainer: { width: '100%', maxWidth: 400, alignItems: 'center', padding: 20, backgroundColor: '#fff', borderRadius: 15, shadowColor: '#000', shadowOpacity: 0.1, shadowRadius: 10, shadowOffset: { width: 0, height: 5 }, elevation: 5 },
  avatar: { width: 120, height: 120, borderRadius: 60, marginBottom: 15, borderWidth: 3, borderColor: '#007bff' },
  name: { fontSize: 24, fontWeight: 'bold', color: '#333', marginBottom: 5 },
  email: { fontSize: 16, color: '#555', marginBottom: 15 },
  infoContainer: { width: '100%', marginTop: 10 },
  infoRow: { flexDirection: 'row', justifyContent: 'space-between', alignItems: 'center', paddingVertical: 8, borderBottomWidth: 1, borderBottomColor: '#ddd' },
  infoLabel: { fontSize: 16, fontWeight: 'bold', color: '#555' },
  infoValue: { fontSize: 16, color: '#333' },
  input: { flex: 1, fontSize: 16, color: '#333', borderBottomWidth: 1, borderBottomColor: '#007bff', paddingVertical: 5, minWidth: 100 },
  editButton: { marginTop: 20, backgroundColor: '#007bff', paddingVertical: 12, paddingHorizontal: 40, borderRadius: 25, elevation: 3 },
  saveButton: { marginTop: 20, backgroundColor: '#28a745', paddingVertical: 12, paddingHorizontal: 40, borderRadius: 25, elevation: 3 },
  buttonText: { fontSize: 16, fontWeight: 'bold', color: '#fff' },
  errorText: { color: 'red', fontSize: 16, marginTop: 20 }
});

export default ProfileScreen;