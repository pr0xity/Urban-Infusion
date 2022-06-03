import {sendApiRequest, USERS_API_PATHNAME} from "../tools.js";

export function sendGetAllUsersRequest() {
  return sendUserRequest("", "GET");
}

export function sendGetNewUsersRequest() {
  return sendUserRequest("new", "GET")
}

export function sendUpdateUserRequest(userId, body, successCallback) {
  return sendUserRequest(userId, "PUT", body, successCallback);
}

function sendUserRequest(pathVariable, method, body, successCallback, unauthorizedCallback, errorCallback) {
  return sendApiRequest(`${USERS_API_PATHNAME}/${pathVariable}`, method, body, successCallback, unauthorizedCallback, errorCallback)
}