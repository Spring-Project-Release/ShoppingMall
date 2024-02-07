import styled from "styled-components";
import { images } from "../jsons/imgList";
import Slider from "react-slick";
import "slick-carousel/slick/slick.css";
import "slick-carousel/slick/slick-theme.css";
import useScrollReset from "../utils/useScrollReset";
import Hood from "../components/Hood";
import { useEffect, useState } from "react";
import { addRecent } from "../utils/addRecent";
import Container from "../components/Container";
import ItemListBox from "../components/Main/ItemListBox";
import TimerListBox from "../components/Main/TimerListBox";

export default function Main() {
  const reset = useScrollReset();
  const onMove = (itemNumber: string) => {
    addRecent(itemNumber);
    reset(`/detail/${itemNumber}`);
  };
  const [isLogin, setIsLogin] = useState(false);

  const settings = {
    dots: true,
    infinite: true,
    speed: 500,
    slidesToShow: 1,
    slidesToScroll: 1,
    arrows: false,
    autoplay: true,
    autoplaySpeed: 3000,
  };

  return (
    <Container>
      <Hood title="홈페이지" />

      {/* SLIDER */}
      <div>
        <Slider {...settings}>
          <div
            className={`h-96 bg-center bg-cover bg-no-repeat bg-[url('../assets/images/main_banner_01.jpg')]`}
          />
          <div
            className={`h-96 bg-center bg-cover bg-no-repeat bg-[url('../assets/images/main_banner_02.jpg')]`}
          />
          <div
            className={`h-96 bg-center bg-cover bg-no-repeat bg-[url('../assets/images/main_banner_03.jpg')]`}
          />
          <div
            className={`h-96 bg-center bg-cover bg-no-repeat bg-[url('../assets/images/main_banner_04.jpg')]`}
          />
        </Slider>
      </div>

      {/* BODY */}
      <ItemListBox onMove={onMove}>
        <div className="flex flex-col justify-center items-center">
          <h1 className="text-2xl font-bold mt-2 text-slate-800">
            마감 임박! 설 선물 랭킹 🔥
          </h1>
          <p className="text-base mt-2 font-slate-800 font-light text-slate-500">
            지금 주목해야 할 상품 최대 60% 할인
          </p>
        </div>
      </ItemListBox>

      <div className="mx-32">
        <div className="bg-lime-100 px-12 py-8">
          <h2 className="text-2xl text-slate-800 font-bold">
            [선물세트] 아직이라면 구매하세요!
          </h2>
          <p className="text-base text-slate-800">
            품절 임박 설 인기선물 최대 60%
          </p>
        </div>
      </div>

      <ItemListBox onMove={onMove}>
        <div className="flex flex-col justify-center items-center">
          <h1 className="text-2xl font-bold mt-2 text-slate-800">
            놓치기 쉬운 알뜰 물품 🎁
          </h1>
          <p className="text-base mt-2 font-slate-800 font-light text-slate-500">
            지금이 마지막 찬스!
          </p>
        </div>
      </ItemListBox>

      <TimerListBox onMove={onMove}>
        <h2 />
      </TimerListBox>
    </Container>
  );
}
