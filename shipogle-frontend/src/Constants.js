const BASE_URL = 'http://localhost:8080';
const APIS = {
    API_REGISTER: `${BASE_URL}/register`,
    API_LOGIN: `${BASE_URL}/login`,
    API_CHAT: `${BASE_URL}/chat`,
    API_USER_INFO_FROM_TOKEN: `${BASE_URL}/user_info`,
};
export default APIS;
export { BASE_URL };