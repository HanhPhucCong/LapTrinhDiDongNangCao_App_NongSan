import React, { useState, useEffect } from 'react';
import { View, Image, TouchableOpacity, StyleSheet, Text } from 'react-native';
import { Sms } from 'iconsax-react-native';
import {
    ContainerComponent,
    SectionComponent,
    SpaceComponent,
    InputComponent,
    ButtonComponent,
    TextComponent,
} from '../../components';
import { appColors } from '../../constants/appColors';
import { Validate } from '../../utils/validate';
import { showMessage, hideMessage } from 'react-native-flash-message';
import Spinner from 'react-native-loading-spinner-overlay';
import { Keyboard } from 'react-native';
import authService from '../../service/api/authService';

const ForgotPasswordScreen = ({ navigation }: any) => {
    const [email, setEmail] = useState('');
    const [isDisabled, setIsDisabled] = useState(true);
    const [loading, setLoading] = useState(false);

    // Kiểm tra tính hợp lệ của email
    useEffect(() => {
        const emailValidation = Validate.email(email);
        if (!email || !emailValidation) {
            setIsDisabled(true);
        } else {
            setIsDisabled(false);
        }
    }, [email]);

    const handleSubmitEmail = async () => {
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
                const userId = getVerifyResponse.Data.id;
                navigation.navigate('ResetPasswordScreen', { email, userId });
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
                <Image
                    source={require('../../assets/images/text-logo.png')}
                    style={{ width: 250, height: 150, marginBottom: 30 }}
                />
            </SectionComponent>
            <SectionComponent>
                <TextComponent size={20} title text='Forgot Password' />
                <SpaceComponent height={21} />
                <InputComponent
                    value={email}
                    placeholder='Enter your email'
                    onChange={(val) => setEmail(val)}
                    allowClear
                    affix={<Sms size={22} color={appColors.gray} />}
                />
                <SpaceComponent height={16} />
                <ButtonComponent
                    text='Submit'
                    color={isDisabled ? appColors.gray : appColors.blueLink}
                    onPress={handleSubmitEmail}
                    type='primary'
                    disable={isDisabled}
                />
            </SectionComponent>
            <View style={styles.buttonContainer}>
                <Text style={styles.normalText}>You already have an account?</Text>
                <TouchableOpacity onPress={() => navigation.navigate('LoginScreen')}>
                    <Text style={[styles.linkText, { marginLeft: 5 }]}>Sign in</Text>
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

export default ForgotPasswordScreen;
