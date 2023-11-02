import styled from "styled-components";
import Navigation from "../components/Navigation";
import DetailBar from "../components/DetailBar";
import { useForm } from "react-hook-form";
import { ILoginFormData } from "../apis/interface";
import { getLoginData } from "../apis/api";
import { useNavigate } from "react-router-dom";

const Container = styled.div`
  height: auto;
`;

const Main = styled.div`
  background-color: red;
  width: 100%;
  height: 100vh;

  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;

  form {
    display: flex;
    flex-direction: column;
  }
`;

const Line = styled.span`
  width: 100%;
  display: flex;
  flex-direction: row;
  justify-content: space-between;
`;

export default function Login() {
  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm<ILoginFormData>();

  const nav = useNavigate();

  const onValid = async (data: ILoginFormData) => {
    console.log(data);
    // console.log("profit!!");

    const response = await getLoginData(data);
    console.log(response);
    nav("/");
  };

  return (
    <Container>
      <Navigation />
      <DetailBar />
      <Main>
        <h2>로그인 하기</h2>
        <form onSubmit={handleSubmit(onValid)}>
          <Line>
            <label htmlFor="loginId">아이디</label>
            <input
              {...register("loginId", {
                required: "아이디를 입력해주세요.",
                minLength: {
                  value: 5,
                  message: "아이디는 5글자 이상입니다.",
                },
              })}
              id="loginId"
              name="loginId"
            />
          </Line>
          <Line>
            <label htmlFor="loginPassword">비밀번호</label>
            <input
              {...register("loginPassword", {
                required: "비밀번호를 입력해주세요.",
                minLength: {
                  value: 8,
                  message: "비밀번호는 8글자 이상입니다.",
                },
              })}
              id="loginPassword"
              name="loginPassword"
            />
          </Line>

          <button>로그인</button>
        </form>
        <span>
          {errors?.loginId?.message
            ? errors?.loginId?.message
            : errors?.loginPassword?.message
            ? errors?.loginPassword?.message
            : " "}
        </span>
      </Main>
    </Container>
  );
}
