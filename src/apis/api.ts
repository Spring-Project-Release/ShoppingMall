import axios from "axios";
import { ILoginFormData } from "./interface";

const api = axios.create({
  withCredentials: true,
});

export function postLoginData(data: ILoginFormData) {
  let url = `${process.env.REACT_APP_BASE_URL}/user/login`;
  return api.post(url, data);
}

export function getLogoutData(memberId: string) {
  let url = `${process.env.REACT_APP_BASE_URL}/user/logout`;
  return api.get(url, {
    params: {
      memberId,
    },
  });
}
