const BASE_URL = "http://localhost:8080";
const SOCKET_BASE_URL = "ws://localhost:8080";
const APIS = {
  API_REGISTER: `${BASE_URL}/register`,
  API_LOGIN: `${BASE_URL}/login`,
  API_CHAT: `${BASE_URL}/chat`,
  API_USER_INFO_FROM_TOKEN: `${BASE_URL}/user_info`,
  API_NOTIFICATIONS: `${BASE_URL}/notifications`,
  API_FORGOT_PWD: `${BASE_URL}/forgotpassword`,
  SOCKET_CHAT: `${SOCKET_BASE_URL}/chatSocket`,
  SOCKET_NOTIFICATIONS: `${SOCKET_BASE_URL}/notificationSocket`,
  PAYMENT_CHARGE: `${BASE_URL}/payment/charge`,
  ORDERS: `${BASE_URL}/package/order/getall`,
  CANCELORDER: `${BASE_URL}/package/order/cancel`,
  STARTORDER: `${BASE_URL}/package/order/start`,
  ENDORDER: `${BASE_URL}/package/order/end`,
  ACCEPTREQUEST: `${BASE_URL}/package/request/accept`,
  REJECTREQUEST: `${BASE_URL}/package/request/reject`,
  GETREQUESTS: `${BASE_URL}/package/request/getall`,
};
export default APIS;
export { BASE_URL };
