import { atom } from "recoil";

interface IUserInfo {
  memberAddress: string;
  memberEmail: string;
  memberGrade: null | string;
  memberId: string;
  memberName: string;
  memberPassword: null | string;
  memberPhone: string;
  memberType: null | string;
}

export const userInfoState = atom<IUserInfo | null>({
  key: "userInfoState",
  default: null,
});

export const loginState = atom({
  key: "loginState",
  default: false,
});
