import axios from "axios";
import { ILoginFormData, ISignupFormData } from "./interface";
import { error } from "console";

const BASE_URL = `http://localhost:8080`;

export const postLoginData = async (data: ILoginFormData): Promise<boolean> => {
  let url = `${BASE_URL}/login`;
  return await axios
    .post(url, data)
    .then((response) => {
      if (response.data.status === 200) {
        const accessToken = response.data.accessToken;
        sessionStorage.setItem("memberInfo", accessToken);
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
  let url = `${BASE_URL}/user`;
  return await axios
    .post(url, data)
    .then((response) => {
      if (response.data.status === 200) {
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
  let url = `${BASE_URL}/user/duplicate`;
  return await axios.get(url, {
    params: {
      memberId,
    },
  });
};
