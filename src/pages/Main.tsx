import styled from "styled-components";
import Navigation from "../components/Navigation";
import DetailBar from "../components/DetailBar";
import SideBar from "../components/SideBar";
import Footer from "../components/Footer";
import { images } from "../jsons/imgList";
import { useNavigate } from "react-router-dom";
import Slider from "react-slick";
import "slick-carousel/slick/slick.css";
import "slick-carousel/slick/slick-theme.css";
import { theme } from "../utils/colors";
import useScrollReset from "../utils/useScrollReset";
import Hood from "../components/Hood";
import { useEffect, useState } from "react";
import { addRecent } from "../utils/addRecent";
const Container = styled.div`
  width: 100%;
`;

const Body = styled.div<{ color?: string }>`
  padding: 72px 24px;
  background-color: ${(props) =>
    props.color === "mint"
      ? theme.mint
      : props.color === "green"
      ? theme.lightGreen
      : theme.white};

  display: flex;
  flex-direction: column;

  span {
    display: flex;
    flex-direction: row;
    gap: 8px;

    p {
      font-size: 32px;
    }

    h1 {
      font-size: 32px;
    }
  }
`;

const Recommand = styled.div`
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  gap: 12px;
  margin-top: 24px;
`;

const Product = styled.div`
  /* background-color: ${(props) => props.theme.green}; */
  height: 60vh;
  display: flex;
  flex-direction: column;
  justify-content: space-between;

  img {
    width: 100%;
    display: flexbox;
    background-color: red;
    height: 70%;

    cursor: pointer;
    transition: transform ease-in-out 0.3s;

    &:hover {
      transform: scale(1.1);
    }
  }
`;

const Info = styled.div`
  width: 100%;
  height: 20%;

  p {
    cursor: pointer;
  }
`;

const InfoLine = styled.div`
  width: 100%;
  display: flex;
  flex-direction: row;
  justify-content: space-between;

  svg {
    width: 10%;
    transition: color ease-in-out 0.3s;

    &:hover {
      color: ${(props) => props.theme.green};
      cursor: pointer;
    }
  }
`;

const SliderBox = styled.div<{ color?: string; url?: string }>`
  height: 360px;
  background-color: ${(props) =>
    props.color === "green"
      ? theme.lightGreen
      : props.color === "gray"
      ? theme.gray
      : theme.mint};

  background-image: url(${(props) => props.url});
  background-position: center;
  background-size: cover;
  background-repeat: no-repeat;
`;

const SliderTab = styled.div`
  margin-bottom: 36px;
`;

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
      <Navigation />
      <DetailBar />
      <SliderTab>
        <Slider {...settings}>
          <SliderBox color="green" url="../../main_banner_01.jpg" />
          <SliderBox color="gray" url="../../main_banner_02.jpg" />
          <SliderBox color="green" url="../../main_banner_03.jpg" />
          <SliderBox color="gray" url="../../main_banner_04.jpg" />
        </Slider>
      </SliderTab>
      <Body color={"white"}>
        <h1>이 물품은 어떠세요?</h1>
        <Recommand>
          {images.items.map((item) => (
            <Product key={item.itemNumber}>
              <img src={item.url} onClick={() => onMove(item.itemNumber)} />
              <Info>
                <p onClick={() => onMove(item.itemNumber)}>{item.name}</p>
                <InfoLine>
                  <h2>{item.price}</h2>
                  <svg
                    xmlns="http://www.w3.org/2000/svg"
                    fill="none"
                    viewBox="0 0 24 24"
                    strokeWidth="1.5"
                    stroke="currentColor"
                    className="w-6 h-6"
                  >
                    <path
                      strokeLinecap="round"
                      strokeLinejoin="round"
                      d="M2.25 3h1.386c.51 0 .955.343 1.087.835l.383 1.437M7.5 14.25a3 3 0 00-3 3h15.75m-12.75-3h11.218c1.121-2.3 2.1-4.684 2.924-7.138a60.114 60.114 0 00-16.536-1.84M7.5 14.25L5.106 5.272M6 20.25a.75.75 0 11-1.5 0 .75.75 0 011.5 0zm12.75 0a.75.75 0 11-1.5 0 .75.75 0 011.5 0z"
                    />
                  </svg>
                </InfoLine>
              </Info>
            </Product>
          ))}
        </Recommand>
      </Body>

      <Body color={"mint"}>
        <span>
          <p>놓치기 쉬운</p>
          <h1>알뜰 물품</h1>
        </span>
        <Recommand>
          {images.items
            .map((item) => (
              <Product key={item.itemNumber}>
                <img src={item.url} onClick={() => onMove(item.itemNumber)} />
                <Info>
                  <p onClick={() => onMove(item.itemNumber)}>{item.name}</p>
                  <InfoLine>
                    <h2>{item.price}</h2>
                    <svg
                      xmlns="http://www.w3.org/2000/svg"
                      fill="none"
                      viewBox="0 0 24 24"
                      strokeWidth="1.5"
                      stroke="currentColor"
                      className="w-6 h-6"
                    >
                      <path
                        strokeLinecap="round"
                        strokeLinejoin="round"
                        d="M2.25 3h1.386c.51 0 .955.343 1.087.835l.383 1.437M7.5 14.25a3 3 0 00-3 3h15.75m-12.75-3h11.218c1.121-2.3 2.1-4.684 2.924-7.138a60.114 60.114 0 00-16.536-1.84M7.5 14.25L5.106 5.272M6 20.25a.75.75 0 11-1.5 0 .75.75 0 011.5 0zm12.75 0a.75.75 0 11-1.5 0 .75.75 0 011.5 0z"
                      />
                    </svg>
                  </InfoLine>
                </Info>
              </Product>
            ))
            .slice(0, 4)}
        </Recommand>
      </Body>

      <Body color={"white"}>
        <span
          style={{
            display: "flex",
            flexDirection: "column",
          }}
        >
          <p>지금 가장 많이 이용하는</p>
          <h1>인기 물품</h1>
        </span>
        <Recommand>
          {images.items
            .map((item) => (
              <Product key={item.itemNumber}>
                <img src={item.url} onClick={() => onMove(item.itemNumber)} />
                <Info>
                  <p onClick={() => onMove(item.itemNumber)}>{item.name}</p>
                  <InfoLine>
                    <h2>{item.price}</h2>
                    <svg
                      xmlns="http://www.w3.org/2000/svg"
                      fill="none"
                      viewBox="0 0 24 24"
                      strokeWidth="1.5"
                      stroke="currentColor"
                      className="w-6 h-6"
                    >
                      <path
                        strokeLinecap="round"
                        strokeLinejoin="round"
                        d="M2.25 3h1.386c.51 0 .955.343 1.087.835l.383 1.437M7.5 14.25a3 3 0 00-3 3h15.75m-12.75-3h11.218c1.121-2.3 2.1-4.684 2.924-7.138a60.114 60.114 0 00-16.536-1.84M7.5 14.25L5.106 5.272M6 20.25a.75.75 0 11-1.5 0 .75.75 0 011.5 0zm12.75 0a.75.75 0 11-1.5 0 .75.75 0 011.5 0z"
                      />
                    </svg>
                  </InfoLine>
                </Info>
              </Product>
            ))
            .slice(0, 4)}
        </Recommand>
      </Body>

      <Body color={"green"}>
        <span
          style={{
            display: "flex",
            flexDirection: "column",
          }}
        >
          <p>푸르넷의 가치를 담은</p>
          <h1>브랜드 관</h1>
        </span>
        <Recommand>
          {images.items
            .map((item) => (
              <Product key={item.itemNumber}>
                <img src={item.url} onClick={() => onMove(item.itemNumber)} />
                <Info>
                  <p onClick={() => onMove(item.itemNumber)}>{item.name}</p>
                  <InfoLine>
                    <h2>{item.price}</h2>
                    <svg
                      xmlns="http://www.w3.org/2000/svg"
                      fill="none"
                      viewBox="0 0 24 24"
                      strokeWidth="1.5"
                      stroke="currentColor"
                      className="w-6 h-6"
                    >
                      <path
                        strokeLinecap="round"
                        strokeLinejoin="round"
                        d="M2.25 3h1.386c.51 0 .955.343 1.087.835l.383 1.437M7.5 14.25a3 3 0 00-3 3h15.75m-12.75-3h11.218c1.121-2.3 2.1-4.684 2.924-7.138a60.114 60.114 0 00-16.536-1.84M7.5 14.25L5.106 5.272M6 20.25a.75.75 0 11-1.5 0 .75.75 0 011.5 0zm12.75 0a.75.75 0 11-1.5 0 .75.75 0 011.5 0z"
                      />
                    </svg>
                  </InfoLine>
                </Info>
              </Product>
            ))
            .slice(0, 4)}
        </Recommand>
      </Body>
      <Footer />
    </Container>
  );
}
