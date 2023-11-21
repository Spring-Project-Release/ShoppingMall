import { useNavigate } from "react-router-dom";
import styled from "styled-components";
import useScrollReset from "../utils/useScrollReset";

const Container = styled.div`
  width: 100%;
  height: 7vh;

  border-bottom: 1px solid lightgray;

  position: sticky;
  top: 0;
  z-index: 100;

  display: flex;
  flex-direction: row;
  justify-content: start;
  align-items: center;

  background-color: white;
`;

const Login = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;

  width: 12%;
  height: 70%;
  border: 1px solid lightgray;

  transition: background-color 0.3s ease, color 0.3s ease;

  cursor: pointer;
  &:hover {
    background-color: lightgray;
    color: white;
  }
`;

const List = styled.div`
  width: 90%;
  height: 70%;

  display: flex;
  flex-direction: row;
  justify-content: start;
  align-items: center;
`;

const Item = styled.div`
  height: 100%;
  width: 12%;

  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;

  border-right: 1px solid lightgray;
  cursor: pointer;

  &:last-child {
    border: none;
  }
`;

export default function DetailBar() {
  const reset = useScrollReset();

  const onMove = (event: React.MouseEvent<HTMLElement>) => {
    console.log(event.currentTarget.id);
    // console.log-test

    reset(`/${event.currentTarget.id}`);
  };

  return (
    <Container>
      <List>
        <Item onClick={onMove} id={"login"}>
          <p>로 그 인</p>
        </Item>
        <Item onClick={onMove} id={"mypage"}>
          마이 페이지
        </Item>
        <Item onClick={onMove} id={"recent"}>
          최근 본 상품
        </Item>
        <Item onClick={onMove} id={"cart"}>
          장바구니
        </Item>
        <Item onClick={onMove} id={"cs"}>
          고객센터
        </Item>
      </List>
    </Container>
  );
}
