import { useForm } from "react-hook-form";
import { ISignupFormData } from "../apis/interface";
import useScrollReset from "../utils/useScrollReset";
import Hood from "../components/Hood";
import { useState } from "react";
import Container from "../components/Container";

export default function Login() {
  const [isDupCheck, setIsDupCheck] = useState<boolean>(false);
  const {
    register,
    handleSubmit,
    formState: { errors },
    getValues,
    setError,
    setFocus,
    clearErrors,
  } = useForm<ISignupFormData>();

  const reset = useScrollReset();

  const onValid = async (data: ISignupFormData) => {};

  const checkDuplicated = async () => {};

  return (
    <Container>
      <Hood title="회원 가입" />
      {/* MAIN */}
      <div className="w-full h-auto px-[8vh] flex flex-col justify-center items-center">
        <form
          className="w-1/3 h-auto flex flex-col justify-start items-center"
          onSubmit={handleSubmit(onValid)}
        >
          <h1 className="font-bold text-xl mt-12 mb-4">회원 가입</h1>

          {/* LINE */}
          <div className="mb-6 w-full flex flex-col justify-between items-start gap-2">
            <label className="font-bold text-lg" htmlFor="memberId">
              아이디
            </label>

            {/* LINE-ID */}
            <div className="flex flex-row justify-between items-center w-full">
              <input
                className="border border-gray-300 h-12 w-full px-1 py-2 focus:border-lime-500"
                {...register("memberId", {
                  required: "아이디를 입력해주세요.",
                  minLength: {
                    value: 5,
                    message: "아이디는 5글자 이상입니다.",
                  },
                })}
                id="memberId"
                name="memberId"
                placeholder="아이디"
                onChange={() => {
                  setIsDupCheck(false);
                  clearErrors("memberId");
                }}
              />
              <div
                className="w-2/5 h-12 flex flex-col justify-center items-center px-1 py-2 border border-gray-300 border-l-0 cursor-pointer"
                onClick={checkDuplicated}
              >
                <p>중복 확인</p>
              </div>
            </div>
          </div>
          {isDupCheck && (
            <span className="text-lime-500 font-bold text-lg">
              사용가능한 아이디입니다.
            </span>
          )}
          {errors?.memberId?.message && (
            <span className="text-red-500 font-bold text-lg">
              {errors.memberId.message}
            </span>
          )}
          <div className="mb-6 w-full flex flex-col justify-between items-start gap-2">
            <label className="font-bold text-lg" htmlFor="memberPassword">
              비밀번호
            </label>
            <input
              className="border border-gray-300 h-12 w-full px-1 py-2 focus:border-lime-500"
              {...register("memberPassword", {
                required: "비밀번호를 입력해주세요.",
                minLength: {
                  value: 8,
                  message: "비밀번호는 8글자 이상입니다.",
                },
              })}
              id="memberPassword"
              name="memberPassword"
              type="password"
              placeholder="비밀번호"
            />
          </div>
          {errors?.memberPassword?.message && (
            <span className="text-red-500 font-bold text-lg">
              {errors.memberPassword.message}
            </span>
          )}

          <div className="mb-6 w-full flex flex-col justify-between items-start gap-2">
            <label className="font-bold text-lg" htmlFor="memberPasswordCheck">
              비밀번호 확인
            </label>
            <input
              className="border border-gray-300 h-12 w-full px-1 py-2 focus:border-lime-500"
              {...register("memberPasswordCheck", {
                required: "비밀번호를 입력해주세요.",
                minLength: {
                  value: 8,
                  message: "비밀번호는 8글자 이상입니다.",
                },
              })}
              id="memberPasswordCheck"
              name="memberPasswordCheck"
              type="password"
              placeholder="비밀번호 확인"
            />
          </div>

          {errors?.memberPasswordCheck?.message && (
            <span className="text-red-500 font-bold text-lg">
              {errors.memberPasswordCheck.message}
            </span>
          )}

          <div className="mb-6 w-full flex flex-col justify-between items-start gap-2">
            <label className="font-bold text-lg" htmlFor="memberName">
              이름
            </label>
            <input
              className="border border-gray-300 h-12 w-full px-1 py-2 focus:border-lime-500"
              {...register("memberName", {
                required: "이름을 입력해주세요.",
                minLength: 2,
              })}
              id="memberName"
              name="memberName"
              type="text"
              placeholder="이름"
            />
          </div>
          {errors?.memberName?.message && (
            <span className="text-red-500 font-bold text-lg">
              {errors.memberName.message}
            </span>
          )}

          <div className="mb-6 w-full flex flex-col justify-between items-start gap-2">
            <label className="font-bold text-lg" htmlFor="memberEmail">
              이메일
            </label>
            <input
              className="border border-gray-300 h-12 w-full px-1 py-2 focus:border-lime-500"
              {...register("memberEmail", {
                required: "이메일 입력해주세요.",
                pattern: {
                  value: /^[^\s@]+@[^\s@]+\.[^\s@]+$/,
                  message: "이메일 형식이 아닙니다.",
                },
              })}
              id="memberEmail"
              name="memberEmail"
              type="text"
              placeholder="이 메 일"
            />
          </div>

          {errors?.memberEmail?.message && (
            <span className="text-red-500 font-bold text-lg">
              {errors.memberEmail.message}
            </span>
          )}

          <div className="mb-6 w-full flex flex-col justify-between items-start gap-2">
            <label className="font-bold text-lg" htmlFor="memberPhone">
              전화번호
            </label>
            <input
              className="border border-gray-300 h-12 w-full px-1 py-2 focus:border-lime-500"
              {...register("memberPhone", {
                required: "전화번호를 입력해주세요.",
                pattern: {
                  value: /^[+]*[(]{0,1}[0-9]{1,4}[)]{0,1}[-\s\./0-9]*$/,
                  message: "전화번호 형식이 아닙니다.",
                },
              })}
              id="memberPhone"
              name="memberPhone"
              type="text"
              placeholder="전화번호"
            />
          </div>
          {errors?.memberPhone?.message && (
            <span className="text-red-500 font-bold text-lg">
              {errors.memberPhone.message}
            </span>
          )}

          <div className="mb-6 w-full flex flex-col justify-between items-start gap-2">
            <label className="font-bold text-lg" htmlFor="memberAddress">
              주소
            </label>
            <input
              className="border border-gray-300 h-12 w-full px-1 py-2 focus:border-lime-500"
              {...register("memberAddress", {
                required: "주소를 입력해주세요.",
              })}
              id="memberAddress"
              name="memberAddress"
              type="text"
              placeholder="주소"
            />
          </div>
          {errors?.memberAddress?.message && (
            <span className="text-red-500 font-bold text-lg">
              {errors.memberAddress.message}
            </span>
          )}

          <button className="border border-gray-300 h-14 w-full px-1 py-2 cursor-pointer mt-12 focus:border-lime-500">
            회원 가입
          </button>
        </form>

        {/* <span
          style={{
            color: "tomato",
            fontSize: "18px",
            fontWeight: "bold",
          }}
        >
          {errors?.memberId?.message
            ? errors?.memberId?.message
            : errors?.memberPassword?.message
            ? errors?.memberPassword?.message
            : errors?.memberPasswordCheck?.message
            ? errors?.memberPasswordCheck?.message
            : errors?.memberAddress?.message
            ? errors?.memberAddress?.message
            : errors?.memberEmail?.message
            ? errors?.memberEmail?.message
            : errors?.memberName?.message
            ? errors?.memberName?.message
            : errors?.memberPhone?.message
            ? errors?.memberPhone?.message
            : " "}
        </span> */}
      </div>
    </Container>
  );
}
