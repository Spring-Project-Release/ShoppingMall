import { useForm } from "react-hook-form";
import { ILoginFormData } from "../apis/interface";
import { postLoginData } from "../apis/api";
import useScrollReset from "../utils/useScrollReset";
import Hood from "../components/Hood";
import Container from "../components/Container";
import { useSetRecoilState } from "recoil";
import { loginState, userInfoState } from "../utils/atoms";

export default function Login() {
  const setUserInfo = useSetRecoilState(userInfoState);
  const setIsLogin = useSetRecoilState(loginState);

  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm<ILoginFormData>();

  const reset = useScrollReset();

  const onValid = async (data: ILoginFormData) => {
    try {
      const response = await postLoginData(data);
      console.log(response);
      if (response.status === 200) {
        setUserInfo(response.data);
        setIsLogin(true);
        reset("/");
      }
    } catch (error) {
    } finally {
    }
  };

  const onMove = () => {
    reset("/signup");
  };

  return (
    <Container>
      <Hood title="로그인" />
      <div className="mt-12 w-full h-auto px-[8vh] flex flex-col justify-center items-center">
        <form
          onSubmit={handleSubmit(onValid)}
          className="w-1/3 h-[60vh] flex flex-col justify-start items-center"
        >
          <h1 className="text-2xl font-bold">로그인</h1>

          <span className="mt-3 w-full flex flex-col justify-between items-start gap-2">
            <label htmlFor="memberId" className="font-bold text-lg">
              아이디
            </label>
            <input
              className="border border-gray-300 h-12 w-full px-1 py-2 focus:bg-lime-200"
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
          </span>
          <span className="mt-3 w-full flex flex-col justify-between items-start gap-2">
            <label htmlFor="memberPassword" className="font-bold text-lg">
              비밀번호
            </label>
            <input
              className="border border-gray-300 h-12 w-full px-1 py-2 focus:bg-lime-200"
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
          </span>

          <button className="border border-gray-300 h-14 w-full px-1 py-2 cursor-pointer mt-12 focus:border-yellow-100">
            로 그 인
          </button>
        </form>

        {/*   font-size: 16px;
  margin-bottom: 16px;
  transition: color 0.3s linear;
  cursor: pointer;

  &:hover {
    color: ${(props) => theme.green};
  } */}
        <div
          onClick={onMove}
          className="text-base mb-4 transition-color duration-300 ease-linear cursor-pointer hover:text-lime-500"
        >
          회원가입
        </div>

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
      </div>
    </Container>
  );
}
