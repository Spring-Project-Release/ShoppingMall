import useScrollReset from "../utils/useScrollReset";
import { useEffect, useState } from "react";

export default function DetailBar() {
  const reset = useScrollReset();
  const [isMemberName, setIsMemberName] = useState();
  const [isLogin, setIsLogin] = useState(false);

  const onMove = (event: React.MouseEvent<HTMLElement>) => {
    console.log(event.currentTarget.id);
    // console.log-test

    reset(`/${event.currentTarget.id}`);
  };

  useEffect(() => {
    const memberInfo = sessionStorage.getItem("userInfo");
    if (memberInfo) {
      const parsingData = JSON.parse(memberInfo);
      if (parsingData && parsingData.memberName) {
        setIsMemberName(parsingData.memberName);
      }
    }
  }, []);

  return (
    <div className="w-full h-10 border-b border-gray-200 sticky top-0 z-50 flex flex-row justify-start items-center bg-white">
      <div className="w-11/12 h-3/5 flex flex-row justify-start items-center">
        <div
          onClick={onMove}
          id={"login"}
          className="h-full w-2/12 flex flex-col justify-center items-center border-r border-gray-200"
        >
          <p>로 그 인</p>
        </div>
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

      {isMemberName && <div>{isMemberName} 님, 어서오세요.</div>}
    </div>
  );
}
