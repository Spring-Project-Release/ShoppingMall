import styled from "styled-components";
import Navigation from "../components/Navigation";
import DetailBar from "../components/DetailBar";
import { useForm } from "react-hook-form";
import { ILoginFormData } from "../apis/interface";
import { getLoginData } from "../apis/api";
import { useNavigate } from "react-router-dom";
import Footer from "../components/Footer";

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
        <form onSubmit={handleSubmit(onValid)}>
          <h1>로그인</h1>

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
              placeholder="아이디"
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
              type="password"
              placeholder="비밀번호"
            />
          </Line>

          <button>로 그 인</button>
        </form>
        <span
          style={{
            color: "tomato",
            fontSize: "18px",
            fontWeight: "bold",
          }}
        >
          {errors?.loginId?.message
            ? errors?.loginId?.message
            : errors?.loginPassword?.message
            ? errors?.loginPassword?.message
            : " "}
        </span>
      </Main>
      <Footer />
    </Container>
  );
}
