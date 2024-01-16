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
        <h1 className="text-2xl font-bold">이 물품은 어떠세요?</h1>
      </ItemListBox>

      <ItemListBox onMove={onMove}>
        <h1 className="text-2xl">놓치기 쉬운</h1>
        <h1 className="text-2xl font-bold">알뜰 물품</h1>
      </ItemListBox>

      <ItemListBox onMove={onMove}>
        <h1 className="text-2xl">지금 가장 많이 이용하는</h1>
        <h1 className="text-2xl font-bold">인기 물품</h1>
      </ItemListBox>

      <ItemListBox onMove={onMove}>
        <p className="text-2xl">푸르넷의 가치를 담은</p>
        <h1 className="text-2xl font-bold">브랜드 관</h1>
      </ItemListBox>
    </Container>
  );
}
