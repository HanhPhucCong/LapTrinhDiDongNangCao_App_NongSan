import React, { useState } from 'react';
import { View, Text, TextInput, TouchableOpacity, StyleSheet, Alert, Image, ActivityIndicator } from 'react-native';
import { useNavigation } from '@react-navigation/native';
import * as ImagePicker from 'expo-image-picker';

const BEARER_TOKEN = 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJoYW5ocGh1Y29uZzI4MTIyMDAzQGdtYWlsLmNvbSIsImlhdCI6MTczOTk2MTI1MSwiZXhwIjoxNzM5OTYyNjkxfQ.hMLrZxjPZdruwdnCK7iizB7UEXlnWT7GX2anQaEFsv0';

const EditProfileScreen = ({ route }: any) => {
    const navigation = useNavigation();
    const { userData } = route.params;

    const [fullName, setFullName] = useState(userData.fullName);
    const [phoneNumber, setPhoneNumber] = useState(userData.phoneNumber || '');
    const [rawPhoneNumber, setRawPhoneNumber] = useState<string>(''); 
    const [address, setAddress] = useState(userData.address || '');
    const [dateOfBirth, setDateOfBirth] = useState(userData.dateOfBirth?.split('T')[0] || '');
    const [newImageUri, setNewImageUri] = useState('');
    const [loading, setLoading] = useState(false);
    const [errorMessage, setErrorMessage] = useState('');
    const [dateErrorMessage, setDateErrorMessage] = useState('');

    const handleSave = async () => {
        setLoading(true);
        try {
            const formData = new FormData();
            formData.append('profileRequest', new Blob([JSON.stringify({
                fullName,
                phoneNumber: phoneNumber.toString(),
                address,
                dateOfBirth: `${dateOfBirth}T00:00:00`,
                avatarUrl: userData.avatarUrl
            })], { type: 'application/json' }));
            if (newImageUri) {
                try {
                    let fileName = `image_${Date.now()}.png`;
                    let fileExt = 'png';
                    let blob: Blob;

                    if (newImageUri.startsWith('data:image')) {
                        console.log("📌 Ảnh đang ở dạng Base64");
                        const mimeTypeMatch = newImageUri.match(/^data:(image\/[a-z]+);base64,/);
                        if (!mimeTypeMatch) {
                            throw new Error("Không thể xác định định dạng ảnh");
                        }

                        const mimeType = mimeTypeMatch[1];
                        fileExt = mimeType.split('/')[1];
                        fileName = `image_${Date.now()}.${fileExt}`;
                        const base64Data = newImageUri.replace(/^data:image\/[a-z]+;base64,/, '');
                        const byteCharacters = atob(base64Data);
                        const byteNumbers = new Array(byteCharacters.length);
                        for (let i = 0; i < byteCharacters.length; i++) {
                            byteNumbers[i] = byteCharacters.charCodeAt(i);
                        }
                        const byteArray = new Uint8Array(byteNumbers);
                        blob = new Blob([byteArray], { type: mimeType });

                    } else {
                        console.log("📌 Ảnh đang ở dạng file URI:", newImageUri);

                        const response = await fetch(newImageUri);
                        blob = await response.blob();
                        const mimeType = blob.type;
                        const mimeToExt: Record<string, string> = {
                            "image/jpeg": "jpg",
                            "image/png": "png",
                            "image/gif": "gif",
                            "image/bmp": "bmp",
                        };

                        fileExt = mimeToExt[mimeType] || 'png';
                        fileName = `image_${Date.now()}.${fileExt}`;
                    }

                    console.log(`📝 File chuẩn bị gửi: ${fileName}, MIME: ${blob.type}`);
                    formData.append('file', blob, fileName);

                } catch (error) {
                    console.error("❌ Lỗi khi xử lý ảnh:", error);
                    Alert.alert('Lỗi', 'Không thể xử lý ảnh. Vui lòng thử lại.');
                    setLoading(false);
                    return;
                }
            }
            const response = await fetch('http://localhost:8083/api/user/update-profile', {
                method: 'PUT',
                headers: {
                    'Authorization': `Bearer ${BEARER_TOKEN}`,
                },
                body: formData,
            });

            const result = await response.json();
            setLoading(false);

            if (response.ok) {
                Alert.alert('Thành công', 'Cập nhật thông tin thành công!');
                navigation.goBack();
            } else {
                Alert.alert('Lỗi', result.message || 'Cập nhật không thành công');
            }
        } catch (error) {
            setLoading(false);
            console.error('Lỗi khi cập nhật hồ sơ:', error);
            Alert.alert('Lỗi', 'Có lỗi xảy ra khi cập nhật hồ sơ');
        }
    };
    const pickImage = async () => {
        let result = await ImagePicker.launchImageLibraryAsync({
            mediaTypes: ImagePicker.MediaTypeOptions.Images,
            allowsEditing: true,
            aspect: [1, 1],
            quality: 1,
        });

        if (!result.canceled) {
            setNewImageUri(result.assets[0].uri);
        }
    };
    const handlePhoneChange = (text: string) => {
        const formattedText = text.replace(/[^0-9]/g, '');
        if (formattedText.length > 10) {
            setErrorMessage('Số điện thoại không hợp lệ (YYYY-MM-DD)');
        } else {
            setErrorMessage('');
        }
        setPhoneNumber(formattedText);
    };
    const handleDateChange = (text: string) => {
        let formattedText = text.replace(/[^0-9-]/g, '');
        if (formattedText.length > 10) {
            setDateErrorMessage('Ngày sinh không hợp lệ');
        } else {
            setDateErrorMessage('');
        }
        if (/^\d{4}-\d{2}-\d{2}$/.test(formattedText)) {
            const [year, month, day] = formattedText.split('-').map(Number);
            const isValidDate = !isNaN(year) && !isNaN(month) && !isNaN(day) &&
                                month >= 1 && month <= 12 &&
                                day >= 1 && day <= 31;
    
            if (!isValidDate) {
                setDateErrorMessage('Định dạng không đúng (YYYY-MM-DD)');
            } else {
                setDateErrorMessage('');
            }
        } else if (formattedText.length === 10) {
            setDateErrorMessage('Định dạng không đúng (YYYY-MM-DD)');
        }
        setDateOfBirth(formattedText);
    };
    
    return (
        <View style={styles.container}>
            <TouchableOpacity onPress={pickImage} style={styles.avatarContainer}>
                <Image source={{ uri: newImageUri || userData.avatarUrl || 'https://via.placeholder.com/120' }} style={styles.avatar} />
                <Text style={styles.changeAvatarText}>Chọn ảnh</Text>
            </TouchableOpacity>

            <Text style={styles.label}>Họ và tên:</Text>
            <TextInput style={styles.input} value={fullName} onChangeText={setFullName} />

            <Text style={styles.label}>Số điện thoại:</Text>
            <TextInput
                style={styles.input}
                value={phoneNumber}
                onChangeText={handlePhoneChange}
                keyboardType="numeric"
                placeholder="Nhập số điện thoại"
            />
            {errorMessage ? <Text style={styles.errorText}>{errorMessage}</Text> : null}

            <Text style={styles.label}>Địa chỉ:</Text>
            <TextInput style={styles.input} value={address} onChangeText={setAddress} />

            <Text style={styles.label}>Ngày sinh:</Text>
            <TextInput
                style={styles.input}
                value={dateOfBirth}
                onChangeText={handleDateChange}
                placeholder="YYYY-MM-DD"
                keyboardType="numeric"
            />
            {dateErrorMessage ? <Text style={styles.errorText}>{dateErrorMessage}</Text> : null}


            <TouchableOpacity style={styles.saveButton} onPress={handleSave} disabled={loading}>
                {loading ? <ActivityIndicator color="#fff" /> : <Text style={styles.saveButtonText}>Lưu</Text>}
            </TouchableOpacity>
            <TouchableOpacity style={styles.backButton} onPress={() => navigation.goBack()} disabled={loading}>
                {loading ? <ActivityIndicator color="#fff" /> : <Text style={styles.saveButtonText}>Quay lại</Text>}
            </TouchableOpacity>

        </View>
    );
};

const styles = StyleSheet.create({
    container: {
        flex: 1,
        padding: 20,
        backgroundColor: '#F4F6F9',
        alignItems: 'center',
    },
    avatarContainer: {
        alignItems: 'center',
        marginBottom: 20,
    },
    avatar: {
        width: 130,
        height: 130,
        borderRadius: 65,
        borderWidth: 3,
        borderColor: '#007bff',
    },
    changeAvatarText: {
        marginTop: 8,
        color: '#007bff',
        fontSize: 15,
        fontWeight: '500',
    },
    label: {
        fontSize: 16,
        fontWeight: '600',
        marginTop: 12,
        color: '#444',
        alignSelf: 'flex-start',
    },
    input: {
        borderWidth: 1,
        borderColor: '#ddd',
        borderRadius: 12,
        padding: 14,
        marginTop: 6,
        width: '100%',
        backgroundColor: '#fff',
        fontSize: 16,
        shadowColor: '#000',
        shadowOpacity: 0.05,
        shadowOffset: { width: 0, height: 2 },
        shadowRadius: 4,
        elevation: 2,
    },
    saveButton: {
        backgroundColor: '#007bff',
        padding: 16,
        borderRadius: 12,
        alignItems: 'center',
        marginTop: 25,
        width: '100%',
        shadowColor: '#000',
        shadowOpacity: 0.1,
        shadowOffset: { width: 0, height: 2 },
        shadowRadius: 4,
        elevation: 3,
    },
    saveButtonText: {
        color: '#fff',
        fontSize: 18,
        fontWeight: 'bold',
    },
    backButton: {
        backgroundColor: '#6c757d',
        paddingVertical: 14,
        paddingHorizontal: 20,
        borderRadius: 12,
        alignItems: 'center',
        justifyContent: 'center',
        flexDirection: 'row',
        marginTop: 12,
        width: '100%',
        shadowColor: '#000',
        shadowOpacity: 0.1,
        shadowOffset: { width: 0, height: 2 },
        shadowRadius: 4,
        elevation: 3,
    },
    backButtonText: {
        color: '#fff',
        fontSize: 18,
        fontWeight: 'bold',
    },
    errorText: {
        color: 'red',
        fontSize: 11,
        marginTop: 5,
    },
});


export default EditProfileScreen;