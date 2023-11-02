import axios from "axios";
import { ILoginFormData } from "./interface";
import { error } from "console";

export const getLoginData = (data: ILoginFormData) => {
  const url = "http://localhost:8080/user/login";

  axios
    .post(url, data)
    .then((response) => {
      console.log("LOGIN POST SUCCESS!!");
      return response;
    })
    .catch((error) => {
      console.log(error);
    });
};
