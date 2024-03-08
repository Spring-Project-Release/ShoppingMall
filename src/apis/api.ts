import axios from "axios";
import { ILoginFormData } from "./interface";
import { IReviewProps } from "../components/Review/ReviewAdd";

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

/**
 *
 * @returns 전체 아이템 리스트 반환
 */
export function getItemList() {
  let url = `${process.env.REACT_APP_BASE_URL}/items`;
  return api.get(url);
}

/**
 *
 * @param category 해당 카테고리에 맞는 아이템 리스트 반환
 */
export function getItemCategory(category: string) {
  let url = `${process.env.REACT_APP_BASE_URL}/items/category/${category}`;

  return api.get(url, {
    params: {
      category,
    },
  });
}

export function getItemIsSoldOut() {
  let url = `${process.env.REACT_APP_BASE_URL}/items/soldout/`;
}

/**
 *
 * @param itemId 아이템 번호
 * @returns
 */
export function getItem(itemId: string) {
  let url = `${process.env.REACT_APP_BASE_URL}/items/${itemId}`;
  return api.get(url);
}

export function postCreateReview(data: IReviewProps) {
  let url = `${process.env.REACT_APP_BASE_URL}/reviews/regist`;

  return api.post(url, {
    body: data,
  });
}
