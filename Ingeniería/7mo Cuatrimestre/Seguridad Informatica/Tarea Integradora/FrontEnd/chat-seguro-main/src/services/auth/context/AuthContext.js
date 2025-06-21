import React, { createContext, useEffect, useState } from "react";
import { API_URL } from "../../../utils/constants";

export const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
  const [userInfo, setUserInfo] = useState({});
  const [isLoading, setIsLoading] = useState(false);
  const [splashLoading, setSplashLoading] = useState(false);

  const login = (username, password) => {
    setIsLoading(true);
    const params = new URLSearchParams();
    params.append("grant_type", "");
    params.append("username", username);
    params.append("password", password);
    params.append("scope", "");
    params.append("client_id", "");
    params.append("client_secret", "");
    fetch(`${API_URL}/auth/login/`, {
      method: "POST",
      headers: {
        "Content-Type": "application/x-www-form-urlencoded",
      },
      body: params.toString(),
    })
      .then((response) => response.json())
      .then((data) => {
        if (data.access_token) {
          const userInfo = {
            username,
            access_token: data.access_token,
          };
          localStorage.setItem("userInfo", JSON.stringify(userInfo));
          setUserInfo(userInfo);
        } else {
          alert("Usuario o contraseña incorrectos");
        }
        setIsLoading(false);
      })
      .catch((error) => {
        alert("Usuario o contraseña incorrectos");
        setIsLoading(false);
      });
  };

  const logout = () => {
    setIsLoading(true);
    localStorage.removeItem("userInfo");
    setUserInfo({});
    setIsLoading(false);
  };

  const isLoggedIn = () => {
    setSplashLoading(true);
    const userInfo = localStorage.getItem("userInfo");
    if (userInfo) {
      setUserInfo(JSON.parse(userInfo));
    }
    setSplashLoading(false);
  };

  useEffect(() => {
    isLoggedIn();
  }, []);

  return (
    <AuthContext.Provider
      value={{
        isLoading,
        userInfo,
        splashLoading,
        login,
        logout,
      }}
    >
      {children}
    </AuthContext.Provider>
  );
};
