import styled from "styled-components";
import Navigation from "../components/Navigation";
import DetailBar from "../components/DetailBar";
import { useForm } from "react-hook-form";
import { ILoginFormData } from "../apis/interface";
import { postLoginData } from "../apis/api";
import { useNavigate } from "react-router-dom";
import Footer from "../components/Footer";
import axios from "axios";
import useScrollReset from "../utils/useScrollReset";
import Hood from "../components/Hood";
import { theme } from "../utils/colors";

const Container = styled.div`
  height: auto;
`;

const Main = styled.div`
  /* background-color: red; */
  width: 100%;
  height: auto;
  padding: 8vh 0;

  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;

  form {
    /* background-color: blue; */
    width: 30%;
    height: 60vh;
    display: flex;
    flex-direction: column;
    justify-content: start;
    align-items: center;

    button {
      border: 1px solid lightgray;
      height: 56px;
      width: 100%;
      padding: 4px 8px;
      cursor: pointer;

      margin-top: 48px;

      &:focus {
        border: 1px solid greenyellow;
      }
    }

    span {
      margin-top: 12px;
    }
  }
`;

const Line = styled.span`
  width: 100%;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  align-items: start;
  /* background-color: gray; */

  gap: 8px;

  input {
    border: 1px solid lightgray;
    height: 48px;
    width: calc(100% - 18px);
    padding: 4px 8px;

    &:focus {
      border-color: greenyellow;
    }
  }

  label {
    text-align: center;
    font-size: 18px;
    font-weight: bold;
  }
`;

const SignupButton = styled.p`
  font-size: 16px;
  margin-bottom: 16px;
  transition: color 0.3s linear;
  cursor: pointer;

  &:hover {
    color: ${(props) => theme.green};
  }
`;

export default function Login() {
  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm<ILoginFormData>();

  const reset = useScrollReset();

  const onValid = async (data: ILoginFormData) => {
    // 서버로 요청을 보내는 부분
    try {
      const success = await postLoginData(data);
      if (success) {
        // 성공 시 페이지 전환
        reset("/");
        console.log("로그인 성공");
      }
    } catch (error) {
      if (error instanceof Error) {
        // Error 형식의 에러 발생 시
        console.error(error);
      }
    } finally {
    }
  };

  const onMove = () => {
    reset("/signup");
  };

  return (
    <Container>
      <Hood title="로그인" />
      <Navigation />
      <DetailBar />
      <Main>
        <form onSubmit={handleSubmit(onValid)}>
          <h1>로그인</h1>

          <Line>
            <label htmlFor="memberId">아이디</label>
            <input
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
            />
          </Line>
          <Line>
            <label htmlFor="memberPassword">비밀번호</label>
            <input
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
          </Line>

          <button>로 그 인</button>
        </form>

        <SignupButton onClick={onMove}>회원가입</SignupButton>

        <span
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
            : " "}
        </span>
      </Main>
      <Footer />
    </Container>
  );
}
