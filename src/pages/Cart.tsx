import styled from "styled-components";
import Navigation from "../components/Navigation";
import DetailBar from "../components/DetailBar";
import Hood from "../components/Hood";
import { images, cardList } from "../jsons/imgList";
import { theme } from "../utils/colors";

const Container = styled.div`
  width: 100%;
`;

const Body = styled.div`
  width: 100%;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
`;

const Carts = styled.div`
  margin-top: 24px;
  width: 60%;
  /* background-color: rebeccapurple; */
`;

const CartBox = styled.div`
  width: calc(100% - 2px);
  height: 180px;

  border: 1px solid lightgray;
  margin: 24px 0;

  display: flex;
  flex-direction: row;
`;

const CartInfo = styled.div`
  width: 70%;
  /* background-color: blue; */

  display: flex;
  flex-direction: column;
  justify-content: space-between;
  padding: 24px;
`;
const CartImg = styled.div<{ url: string }>`
  width: 30%;
  height: 100%;

  background-image: url(${(props) => props.url});
  background-position: center;
  background-size: cover;
`;

const Bill = styled.div`
  width: calc(100% - 50px);
  height: 360px;

  border-top: 1px solid lightgray;
  margin: 24px 0;
  padding: 0px 24px;

  display: flex;
  flex-direction: column;
  justify-content: space-around;
  align-items: center;
`;

const BillLine = styled.div`
  display: flex;
  flex-direction: row;
  width: 100%;
  justify-content: space-between;
`;

const Cards = styled.div`
  width: 100%;
  display: grid;
  grid-template-columns: repeat(8, 1fr);
  flex-direction: row;
  justify-content: space-between;
  gap: 8px;
`;

const Card = styled.div`
  border: 1px solid lightgray;
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 4px;

  cursor: pointer;
  transition: background-color 0.3s ease-in-out;

  &:hover {
    background-color: lightgray;
  }
`;

const Purchase = styled.div`
  background-color: ${(props) => theme.green};
  color: white;
  padding: 24px 48px;
  cursor: pointer;
`;

const Address = styled.div``;

export default function Cart() {
  return (
    <Container>
      <Hood title="장바구니" />
      <Navigation />
      <DetailBar />
      <Body>
        <Carts>
          <h2>장바구니</h2>
          {images &&
            images.items.map((item) => (
              <CartBox>
                <CartInfo>
                  <div>
                    <h3>{item.name}</h3>
                    <p>{item.price}</p>
                  </div>

                  <p>수량</p>
                </CartInfo>
                <CartImg url={item.url}></CartImg>
              </CartBox>
            ))}
          <Bill>
            <BillLine>
              <h2>총 합계</h2>
              <h1>123123123 원</h1>
            </BillLine>

            <Cards>
              {cardList &&
                cardList.cards.map((card) => (
                  <Card key={card.cardNumber}>{card.cardName}</Card>
                ))}
            </Cards>

            <BillLine>
              <Address>
                <h2>주소 입력</h2>
              </Address>
              <Purchase>결제 하기</Purchase>
            </BillLine>
          </Bill>
        </Carts>
      </Body>
    </Container>
  );
}
