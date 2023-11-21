import React from "react";
import { Route, Routes } from "react-router-dom";
import Main from "./pages/Main";
import Login from "./pages/Login";
import Detail from "./pages/Detail";
import MyPage from "./pages/MyPage";
import Recent from "./pages/Recent";
import Cart from "./pages/Cart";
import Cs from "./pages/Cs";

export default function App() {
  return (
    <Routes>
      <Route path={"/"} element={<Main />} />
      <Route path={"/login"} element={<Login />} />
      <Route path={"/mypage"} element={<MyPage />} />
      <Route path={"/cs"} element={<Cs />} />
      <Route path={"/cart"} element={<Cart />} />
      <Route path={"/recent"} element={<Recent />} />
      <Route path={"/detail/:productId"} element={<Detail />} />
    </Routes>
  );
}
