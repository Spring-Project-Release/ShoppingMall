import styled from "styled-components";
import Navigation from "../components/Navigation";
import DetailBar from "../components/DetailBar";
import { useForm } from "react-hook-form";
import { ISignupFormData } from "../apis/interface";
import { getLoginData } from "../apis/api";
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
    const url = "http://localhost:8080/user/login";
    console.log(data);

    axios
      .post(url, data)
      .then((response) => {
        if (response.data) {
          sessionStorage.setItem("memberInfo", response.data);
          console.log(response.data);
          // test
        }
      })
      .catch((error) => console.log(error));
  };

  const onIdCheck = () => {
    const memberId = getValues("memberId");
    // 멤버 아이디 가져오기
  };

  const checkDuplicated = async (id: string) => {
    const url = "http://localhost:8080/user/idCheck";

    //axios.get(url, { params: { memberId: id } });
    // 잠시 보류
    // 데이터를 일단 주고 넘길 것인지 error 처리 할 것인지 백앤드와 상의
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
              <Checker onClick={onIdCheck}>
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
