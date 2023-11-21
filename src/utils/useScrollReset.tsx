import { useNavigate } from "react-router-dom";

export default function useScrollReset() {
  const nav = useNavigate();

  const resetScrollAndNavigate = (destination: string) => {
    window.scrollTo(0, 0); // 스크롤 리셋
    nav(destination); // 페이지 이동
  };

  return resetScrollAndNavigate;
}
