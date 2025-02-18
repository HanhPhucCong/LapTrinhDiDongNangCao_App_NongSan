import React, { useState, useEffect } from 'react';
import { View, TextInput, TouchableOpacity, StyleSheet, Text } from 'react-native';
import { Lock } from 'iconsax-react-native';
import {
    ContainerComponent,
    SectionComponent,
    SpaceComponent,
    InputComponent,
    ButtonComponent,
    TextComponent,
} from '../../components';
import { appColors } from '../../constants/appColors';
import { showMessage, hideMessage } from 'react-native-flash-message';
import Spinner from 'react-native-loading-spinner-overlay';
import { Keyboard } from 'react-native';
import authService from '../../service/api/authService';

const ResetPasswordScreen = ({ route, navigation }: any) => {
    const { email, userId } = route.params;
    const [otp, setOtp] = useState('');
    const [newPassword, setNewPassword] = useState('');
    const [confirmPassword, setConfirmPassword] = useState('');
    const [isDisabled, setIsDisabled] = useState(true);
    const [loading, setLoading] = useState(false);

    // Kiểm tra tính hợp lệ của OTP và mật khẩu
    useEffect(() => {
        const isValid = otp.length === 6 && newPassword && newPassword === confirmPassword;
        setIsDisabled(!isValid);
    }, [otp, newPassword, confirmPassword]);

    // Hàm gọi API để reset mật khẩu
    const handleResetPassword = async () => {
        try {
            setLoading(true);
            Keyboard.dismiss();
            const resetPasswordResponse: any = await authService.resetPassword(
                newPassword,
                confirmPassword,
                otp,
                userId
            );
            if (resetPasswordResponse && resetPasswordResponse.Message) {
                showMessage({
                    message: 'Reset Password successfully!',
                    description: resetPasswordResponse.Message,
                    type: 'success',
                    hideStatusBar: true,
                    position: 'top',
                    duration: 4000,
                });
                navigation.navigate('LoginScreen');
            }
        } catch (error: any) {
            //console.error('Error during signup:', error.response || error.message); // Log chi tiết lỗi
            let errorMessage = 'Renew Password Failed!';

            // Kiểm tra lỗi từ phản hồi của server
            if (error.response && error.response.data) {
                const { Message } = error.response.data;
                if (Message) {
                    errorMessage = Message;
                }
            } else if (error.message) {
                errorMessage = error.message;
            }

            showMessage({
                message: 'Renew Password Failed',
                description: errorMessage,
                type: 'danger',
                hideStatusBar: true,
                position: 'top',
                duration: 4000,
            });
        } finally {
            setLoading(false);
        }
    };

    const handleResendOTP = async () => {
        try {
            setLoading(true);
            Keyboard.dismiss();
            const getVerifyResponse: any = await authService.getVerify(email);
            if (getVerifyResponse && getVerifyResponse.Message) {
                showMessage({
                    message: 'Get verify successfully!',
                    description: getVerifyResponse.Message,
                    type: 'success',
                    hideStatusBar: true,
                    position: 'top',
                    duration: 4000,
                });
            }
        } catch (error: any) {
            //console.error('Error during signup:', error.response || error.message); // Log chi tiết lỗi
            let errorMessage = 'Get verify failed!';

            // Kiểm tra lỗi từ phản hồi của server
            if (error.response && error.response.data) {
                const { Message } = error.response.data;
                if (Message) {
                    errorMessage = Message;
                }
            } else if (error.message) {
                errorMessage = error.message;
            }

            showMessage({
                message: 'Get verify Failed',
                description: errorMessage,
                type: 'danger',
                hideStatusBar: true,
                position: 'top',
                duration: 4000,
            });
        } finally {
            setLoading(false);
        }
    };

    return (
        <ContainerComponent isImageBackground isScroll>
            <Spinner visible={loading} textContent={'Processing...'} textStyle={styles.spinnerTextStyle} />
            <SectionComponent styles={{ justifyContent: 'center', alignItems: 'center', marginTop: 75 }}>
                <TextComponent size={24} title text='Reset Password' />
                <SpaceComponent height={21} />
                <InputComponent
                    value={otp}
                    placeholder='Enter OTP'
                    onChange={(val) => setOtp(val)}
                    allowClear
                    affix={<Lock size={22} color={appColors.gray} />}
                />
                <InputComponent
                    value={newPassword}
                    placeholder='New Password'
                    onChange={(val) => setNewPassword(val)}
                    isPassword
                    allowClear
                    affix={<Lock size={22} color={appColors.gray} />}
                />
                <InputComponent
                    value={confirmPassword}
                    placeholder='Confirm Password'
                    onChange={(val) => setConfirmPassword(val)}
                    isPassword
                    allowClear
                    affix={<Lock size={22} color={appColors.gray} />}
                />
                <SpaceComponent height={16} />
            </SectionComponent>

            <ButtonComponent
                text='Submit'
                onPress={handleResetPassword}
                color={isDisabled ? appColors.gray : appColors.blueLink}
                type='primary'
                disable={isDisabled}
            />
            <View style={styles.buttonContainer}>
                <Text style={styles.normalText}>You already have an account?</Text>
                <TouchableOpacity onPress={() => navigation.navigate('LoginScreen')}>
                    <Text style={[styles.linkText, { marginLeft: 5 }]}>Sign in</Text>
                </TouchableOpacity>
            </View>
            <View style={styles.buttonContainer}>
                <Text style={styles.normalText}>Didn't receive the code?</Text>
                <TouchableOpacity onPress={handleResendOTP}>
                    <Text style={[styles.linkText, { marginLeft: 5 }]}>Resend OTP</Text>
                </TouchableOpacity>
            </View>
        </ContainerComponent>
    );
};

const styles = StyleSheet.create({
    buttonContainer: {
        flexDirection: 'row',
        justifyContent: 'center',
        alignItems: 'center',
        marginTop: 16,
    },
    normalText: {
        fontSize: 16,
        color: appColors.grayTextBlur,
    },
    linkText: {
        fontSize: 16,
        color: appColors.blueLink,
    },
    spinnerTextStyle: {
        color: '#FFF',
        fontSize: 16,
        zIndex: 1,
    },
});

export default ResetPasswordScreen;
