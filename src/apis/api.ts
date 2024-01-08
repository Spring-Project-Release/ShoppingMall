//import axios from "axios";
import axios, { AxiosRequestConfig } from 'axios';
import { ILoginFormData, ISignupFormData } from "./interface";

export const postLoginData = async (data: ILoginFormData): Promise<boolean> => {
  let url = `${process.env.REACT_APP_BASE_URL}/user/login`;
  const config: AxiosRequestConfig = {
    withCredentials: true, // withCredentials 옵션 추가
  };

  return await axios
    .post(url, data, config)
    .then((response) => {
      console.log(response);
      if (response.status === 200) {
        sessionStorage.setItem("userInfo", JSON.stringify(response.data));
        return true;
        // 성공시 true
      } else {
        throw new Error("로그인 실패");
      }
    })
    .catch((error) => {
      if (!error.message) {
        throw new Error("서버 끊김");
      }

      throw error;
    });
};

export const postSignupData = async (
  data: ISignupFormData
): Promise<boolean> => {
  let url = `${process.env.REACT_APP_BASE_URL}/user/signup`;
  return await axios
    .post(url, data)
    .then((response) => {
      console.log(response);
      if (response.status === 200) {
        return true;
      } else throw new Error("회원가입 실패");
    })
    .catch((error) => {
      if (!error.message) {
        throw new Error("서버 연결 끊김");
      }

      throw error;
    });
};

export const getDuplicateId = async (memberId: string): Promise<boolean> => {
  let url = `${process.env.REACT_APP_BASE_URL}/user/duplicate`;
  return await axios.get(url, {
    params: {
      memberId,
    },
  });
};
