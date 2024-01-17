import { useRecoilState } from "recoil";
import useScrollReset from "../utils/useScrollReset";
import { useEffect, useState } from "react";
import { loginState, userInfoState } from "../utils/atoms";
import { getLogoutData } from "../apis/api";

export default function DetailBar() {
  const reset = useScrollReset();
  const [isMemberName, setIsMemberName] = useState<string | null>();
  const [isLogin, setIsLogin] = useRecoilState(loginState);
  const [userInfo, setUserInfo] = useRecoilState(userInfoState);

  const onMove = (event: React.MouseEvent<HTMLElement>) => {
    reset(`/${event.currentTarget.id}`);
  };

  const onLogout = async () => {
    try {
      if (isLogin && userInfo) {
        const response = await getLogoutData(userInfo.memberId);
        console.log(response);
      }
    } catch (error) {
    } finally {
      setIsLogin(false);
      setUserInfo(null);
      setIsMemberName(null);
    }
  };

  useEffect(() => {
    if (userInfo) {
      // console.log(userInfo.memberName);
      setIsMemberName(userInfo.memberName);
    }
  }, []);

  return (
    <div className="w-full h-10 border-b border-gray-200 sticky top-0 z-50 flex flex-row justify-start items-center bg-white">
      <div className="w-10/12 h-3/5 flex flex-row justify-start items-center">
        {isLogin ? (
          <div
            onClick={onLogout}
            id={"logout"}
            className="cursor-pointer h-full w-2/12 flex flex-col justify-center items-center border-r border-gray-200"
          >
            <p>로그 아웃</p>
          </div>
        ) : (
          <div
            onClick={onMove}
            id={"login"}
            className="cursor-pointer h-full w-2/12 flex flex-col justify-center items-center border-r border-gray-200"
          >
            <p>로 그 인</p>
          </div>
        )}
        <div
          onClick={onMove}
          id={"mypage"}
          className="h-full w-2/12 flex flex-col justify-center items-center border-r border-gray-200"
        >
          마이 페이지
        </div>
        <div
          onClick={onMove}
          id={"recent"}
          className="h-full w-2/12 flex flex-col justify-center items-center border-r border-gray-200"
        >
          최근 본 상품
        </div>
        <div
          onClick={onMove}
          id={"cart"}
          className="h-full w-2/12 flex flex-col justify-center items-center border-r border-gray-200"
        >
          장바구니
        </div>
        <div
          onClick={onMove}
          id={"cs"}
          className="h-full w-2/12 flex flex-col justify-center items-center border-r border-gray-200 border-none"
        >
          고객센터
        </div>
      </div>

      {isLogin && isMemberName && <div>{isMemberName} 님, 어서오세요.</div>}
    </div>
  );
}
