const BASE_URL = 'http://csci5308vm9.research.cs.dal.ca:8080';
const SOCKET_BASE_URL = 'ws://csci5308vm9.research.cs.dal.ca:8080';
const APIS = {
    API_REGISTER: `${BASE_URL}/register`,
    API_LOGIN: `${BASE_URL}/login`,
    API_CHAT: `${BASE_URL}/chat`,
    API_USER: `${BASE_URL}/user`,
    API_USER_INFO_FROM_TOKEN: `${BASE_URL}/user_info`,
    API_NOTIFICATIONS: `${BASE_URL}/notifications`,
    API_FORGOT_PWD: `${BASE_URL}/forgotpassword`,
    SOCKET_CHAT: `${SOCKET_BASE_URL}/chatSocket`,
    SOCKET_NOTIFICATIONS: `${SOCKET_BASE_URL}/notificationSocket`,
};
export default APIS;
export { BASE_URL };