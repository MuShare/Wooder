import moment from "moment";

export const TOKEN = "token";
export const saveToken = token => {
  localStorage.setItem(TOKEN, token);
};

const parseJWT = () => {
  try {
    if (localStorage.getItem(TOKEN) === null) {
      return null;
    } else {
      var base64Url = localStorage.getItem(TOKEN).split(".")[1];
      var base64 = base64Url.replace("-", "+").replace("_", "/");
      return JSON.parse(window.atob(base64));
    }
  } catch (e) {
    return null;
  }
};

export const isExpired = () => {
  var parsedJWT = parseJWT();
  if (parsedJWT === null) {
    return true;
  } else {
    if (moment(parsedJWT.exp).isAfter(moment.now())) {
      return true;
    } else {
      return false;
    }
  }
};

export const getToken = () => {
  return localStorage.getItem(TOKEN);
};

export const handleError = response => {
  if (response.ok) {
    return response.json();
  } else {
    return Promise.reject(response);
  }
};
