import axios from "axios";
import { ILoginFormData } from "./interface";

export function postLoginData(data: ILoginFormData) {
  let url = `${process.env.REACT_APP_BASE_URL}/user/login`;
  return axios.post(url, data);
}

export function getLogoutData(memberId: string) {
  let url = `${process.env.REACT_APP_BASE_URL}/user/logout`;
  return axios.get(url, {
    params: {
      memberId,
    },
  });
}
