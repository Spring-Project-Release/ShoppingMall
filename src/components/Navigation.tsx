import { useNavigate } from "react-router-dom";
import styled from "styled-components";

const Container = styled.div`
  width: 100%;
  height: 10vh;

  background-color: black;

  display: flex;
  flex-direction: row;
  justify-content: space-between;
  align-items: center;
`;

const Logo = styled.div`
  height: 100%;

  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;

  color: white;
  cursor: pointer;
`;

const Buttons = styled.div`
  display: flex;
  flex-direction: row;
  justify-content: center;

  width: 40%;
  height: 100%;
`;

const Button = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;

  height: calc(100% - 16px);
  width: 25%;

  color: white;
  cursor: pointer;

  margin-top: 8px;
  transition: border-bottom 0.3s ease;

  &:hover {
    border-bottom: 8px solid red;
  }
`;

export default function Navigation() {
  const nav = useNavigate();

  const onMove = (event: React.MouseEvent<HTMLElement>) => {
    let destination = event.currentTarget.id;

    destination === "home" ? nav("/") : nav(`/${destination}`);
  };

  return (
    <Container>
      <Logo onClick={onMove} id="home">
        <h1>SHOPPING</h1>
      </Logo>

      <Buttons>
        <Button onClick={onMove}>
          <h2>세 일</h2>
        </Button>

        <Button onClick={onMove}>
          <h2>스페셜</h2>
        </Button>

        <Button onClick={onMove}>
          <h2>랭킹</h2>
        </Button>

        <Button onClick={onMove}>
          <h2>추천</h2>
        </Button>
      </Buttons>
    </Container>
  );
}
