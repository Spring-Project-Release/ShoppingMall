import { useState } from "react";
import styled from "styled-components";
import { AiOutlineHeart } from "react-icons/ai";
import { AiTwotoneHeart } from "react-icons/ai";

interface IProductProps {
  productId: string;
}

const Container = styled.div`
  margin-top: 24px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  width: 100%;
  height: auto;

  /* background-color: blueviolet; */
`;

const Header = styled.div`
  width: 80%;

  display: flex;
  flex-direction: row;
  justify-content: start;
  align-items: start;

  gap: 48px;
  margin-bottom: 48px;
`;

const Info = styled.div`
  display: flex;
  flex-direction: column;
  background-color: red;
  width: 80%;
  height: 200vh;
`;

const Footer = styled.div``;

const ImgBox = styled.div`
  width: calc(50% - 24px);
  height: 70vh;

  background-color: yellow;
`;

const Purchase = styled.div`
  /* background-color: red; */
  width: calc(50% - 24px);
  height: 100%;

  display: flex;
  flex-direction: column;
  justify-content: start;
  align-items: start;

  gap: 12px;
`;

const Price = styled.div`
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  width: 50%;
`;

const DetailInfo = styled.div`
  width: 100%;
  height: auto;
`;

const Line = styled.div`
  border-top: 1px solid lightgray;
  display: flex;
  flex-direction: row;
  justify-content: space-between;

  padding: 18px 0;

  &:last-child {
    border-bottom: 1px solid lightgray;
  }
`;

const Head = styled.div`
  display: flex;
  width: 25%;
`;

const LineInfo = styled.div`
  width: 70%;
  display: flex;
  flex-direction: column;

  justify-content: center;

  p {
    font-size: 12px;
  }
`;

const PurchaseBox = styled.div`
  width: calc(100% - 26px);
  height: auto;
  border: 1px solid lightgray;

  padding: 24px 12px;
  display: flex;
  flex-direction: column;

  gap: 12px;
  margin-top: 48px;
`;

const PurchaseBoxLine = styled.div`
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  align-items: center;
`;

const CountBox = styled.div`
  display: flex;
  flex-direction: row;
`;

const Counter = styled.div`
  width: 64px;
  height: 36px;

  border-top: 1px solid lightgray;
  border-bottom: 1px solid lightgray;
  border-left: 1px solid lightgray;

  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;

  &:first-child {
    cursor: pointer;
  }

  &:last-child {
    border-right: 1px solid lightgray;
    cursor: pointer;
  }
`;

const ButtonBox = styled.div`
  width: 100%;
  height: auto;
`;

const ButtonLine = styled.div`
  display: flex;
  flex-direction: row;
  justify-content: end;
  align-items: end;

  gap: 12px;

  p {
    font-size: 12px;
  }
`;

const Buttons = styled.div`
  margin-top: 48px;
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  align-items: center;
`;

const Heart = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  cursor: pointer;
  width: 10%;

  padding: 18px;
  border: 1px solid lightgray;
`;

const PurchaseButton = styled.div`
  width: 80%;
  padding: 18px 0;
  border: 1px solid blueviolet;
  background-color: blueviolet;
  color: white;

  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  cursor: pointer;
`;

const InfoNavigator = styled.div`
  width: calc(100% - 2px);
  height: 48px;
  border: 1px solid lightgray;

  background-color: white;
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  align-items: center;

  position: sticky;
  top: calc(7vh);
  z-index: 100;
`;

const NavButton = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  border-right: 1px solid lightgray;

  width: 25%;
  height: calc(100% - 2px);
  cursor: pointer;

  &:last-child {
    border-right: none;
  }
`;

export default function ProductDetail({ productId }: IProductProps) {
  const [counter, setCounter] = useState(1);
  const [isHearted, setIsHearted] = useState(false);

  const onMinus = () => {
    if (1 < counter) {
      setCounter((current) => (current -= 1));
    }
  };

  const onPlus = () => {
    setCounter((current) => (current += 1));
  };

  const onHeart = () => {
    setIsHearted((current) => !current);
  };

  return (
    <Container>
      <Header>
        <ImgBox></ImgBox>
        <Purchase>
          <p>특급 배송</p>
          <h2>[일일특가] 머스크멜론 1.3kg</h2>
          <p>촉촉하게 머금은 달콤함</p>
          <Price>
            <h1
              style={{
                color: "tomato",
              }}
            >
              46%
            </h1>
            <h1>
              7900
              <span
                style={{
                  fontSize: "18px",
                }}
              >
                원
              </span>
            </h1>
          </Price>

          <p>원산지: 국산</p>

          <DetailInfo>
            <Line>
              <Head>
                <p>배 송</p>
              </Head>
              <LineInfo>
                <h4>샛별 배송</h4>
                <p>23시 전 주문시 아침 7시 전 도착</p>
              </LineInfo>
            </Line>
            <Line>
              <Head>
                <p>판매자</p>
              </Head>
              <LineInfo>
                <h4>이준모</h4>
              </LineInfo>
            </Line>

            <Line>
              <Head>
                <p>판매단위</p>
              </Head>
              <LineInfo>
                <h4>1통</h4>
              </LineInfo>
            </Line>
            <Line>
              <Head>
                <p>중량/용량</p>
              </Head>
              <LineInfo>
                <h4>1.3kg 내외</h4>
              </LineInfo>
            </Line>

            <Line>
              <Head>
                <p>포장타입</p>
              </Head>
              <LineInfo>
                <h4>냉장(종이포장)</h4>
              </LineInfo>
            </Line>

            <Line>
              <Head>
                <p>유통기한</p>
              </Head>
              <LineInfo>
                <p>농산물은 유통기한이 없으나 최대한 빨리 드시길 권장합니다.</p>
              </LineInfo>
            </Line>

            <Line>
              <Head>
                <p>안내사항</p>
              </Head>
              <LineInfo>
                <p>상품 특성상 3% 내외의 중량 차이가 발생 할 수 있습니다.</p>
                <p>신선식품 특성상 크기 및 형태가 일정하지 않을 수 있습니다.</p>
              </LineInfo>
            </Line>
          </DetailInfo>

          <PurchaseBox>
            <p>[일일특가] 머스크멜론 1.3kg</p>
            <PurchaseBoxLine>
              <CountBox>
                <Counter onClick={onMinus}>-</Counter>
                <Counter>{counter}</Counter>
                <Counter onClick={onPlus}>+</Counter>
              </CountBox>
              <p>7900 원</p>
            </PurchaseBoxLine>
          </PurchaseBox>

          <ButtonBox>
            <ButtonLine>
              <p>총 상품금액: </p>
              <h2> {counter * 7900} 원</h2>
            </ButtonLine>

            <Buttons>
              <Heart onClick={onHeart}>
                {isHearted ? (
                  <AiTwotoneHeart size={22} color="blueviolet" />
                ) : (
                  <AiOutlineHeart size={22} color="blueviolet" />
                )}
              </Heart>
              <PurchaseButton>구 매 하 기</PurchaseButton>
            </Buttons>
          </ButtonBox>
        </Purchase>
      </Header>
      <Info>
        <InfoNavigator>
          <NavButton>
            <h3>상품설명</h3>
          </NavButton>
          <NavButton>
            <h3>상세정보</h3>
          </NavButton>
          <NavButton>
            <h3>후 기</h3>
          </NavButton>
          <NavButton>
            <h3>문 의</h3>
          </NavButton>
        </InfoNavigator>
      </Info>
      <Footer></Footer>
    </Container>
  );
}
