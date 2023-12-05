import styled from "styled-components";
import Navigation from "../components/Navigation";
import DetailBar from "../components/DetailBar";
import { useForm } from "react-hook-form";
import { ISignupFormData } from "../apis/interface";
import { useNavigate } from "react-router-dom";
import Footer from "../components/Footer";
import axios from "axios";
import useScrollReset from "../utils/useScrollReset";
import Hood from "../components/Hood";
import { theme } from "../utils/colors";
import { getDuplicateId, postSignupData } from "../apis/api";

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
    height: auto;
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
      background-color: ${(props) => theme.gray};

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

const LineId = styled.div`
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  align-items: center;
  width: 100%;
`;

const Checker = styled.div`
  width: 40%;
  height: 48px;

  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  padding: 4px 8px;

  border: 1px solid lightgray;
  border-left: 0;
  cursor: pointer;
  background-color: ${(props) => theme.gray};
`;

const BASE_URL = "http://localhost:8080"; // 서버 주소 설정

export default function Login() {
  const {
    register,
    handleSubmit,
    formState: { errors },
    getValues,
  } = useForm<ISignupFormData>();

  const reset = useScrollReset();

  const onValid = async (data: ISignupFormData) => {
    // 서버로 요청을 보내는 부분
    try {
      let success = await postSignupData(data);
      if (success) {
        console.log("회원가입 성공");
        reset("/");
      }
    } catch (error) {
      if (error instanceof Error) {
        console.error(error);
      }
    } finally {
    }
  };

  const checkDuplicated = async () => {
    const id = getValues("memberId");

    if (id) {
      try {
        const access = await getDuplicateId(id);
        console.log(access);
      } catch (error) {
      } finally {
      }
    }
  };

  return (
    <Container>
      <Hood title="회원 가입" />
      <Navigation />
      <DetailBar />
      <Main>
        <form onSubmit={handleSubmit(onValid)}>
          <h1>회원 가입</h1>

          <Line>
            <label htmlFor="memberId">아이디</label>

            <LineId>
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
              <Checker onClick={checkDuplicated}>
                <p>중복 확인</p>
              </Checker>
            </LineId>
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

          <Line>
            <label htmlFor="memberPasswordCheck">비밀번호 확인</label>
            <input
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
          </Line>

          <Line>
            <label htmlFor="memberEmail">이메일</label>
            <input
              {...register("memberEmail", {
                required: "이메일 입력해주세요.",
                minLength: {
                  value: 8,
                  message: "비밀번호는 8글자 이상입니다.",
                },
              })}
              id="memberEmail"
              name="memberEmail"
              type="text"
              placeholder="이 메 일"
            />
          </Line>

          <Line>
            <label htmlFor="memberPhone">전화번호</label>
            <input
              {...register("memberPhone", {
                required: "전화번호를 입력해주세요.",
                minLength: {
                  value: 8,
                  message: "전화번호 형식이 맞지 않습니다.",
                },
              })}
              id="memberPhone"
              name="memberPhone"
              type="text"
              placeholder="전화번호"
            />
          </Line>

          <Line>
            <label htmlFor="memberAddress">주소</label>
            <input
              {...register("memberAddress", {
                required: "주소를 입력해주세요.",
                minLength: {
                  value: 8,
                  message: "주소는 8글자 이상입니다.",
                },
              })}
              id="memberAddress"
              name="memberAddress"
              type="text"
              placeholder="주소"
            />
          </Line>

          <button>회원 가입</button>
        </form>

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
