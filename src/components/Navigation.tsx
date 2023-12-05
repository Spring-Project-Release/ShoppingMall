import { useNavigate } from "react-router-dom";
import styled from "styled-components";
import useScrollReset from "../utils/useScrollReset";

const Container = styled.div`
  width: 100%;
  height: 10vh;

  background-color: white;

  display: flex;
  flex-direction: row;
  justify-content: space-between;
  align-items: center;
`;

const Logo = styled.div`
  height: 100%;
  margin-left: 12px;
  width: 20%;

  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;

  color: ${(props) => props.theme.green};
  cursor: pointer;

  img {
    width: 80%;
    height: auto;
  }
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

  color: #76bd42;
  cursor: pointer;

  margin-top: 8px;
  transition: border-bottom 0.3s ease;

  &:hover {
    border-bottom: 3px solid #76bd42;
  }
`;

export default function Navigation() {
  const reset = useScrollReset();

  const onMove = (event: React.MouseEvent<HTMLElement>) => {
    let destination = event.currentTarget.id;

    destination === "home" ? reset("/") : reset(`/${destination}`);
  };

  return (
    <Container>
      <Logo onClick={onMove} id="home">
        <img src="../Frunet-icon.png" />
      </Logo>

      <Buttons>
        <Button onClick={onMove} id={`sale`}>
          <h2>세 일</h2>
        </Button>

        <Button onClick={onMove} id={`special`}>
          <h2>스페셜</h2>
        </Button>

        <Button onClick={onMove} id={`ranking`}>
          <h2>랭킹</h2>
        </Button>

        <Button onClick={onMove} id={`recommand`}>
          <h2>추천</h2>
        </Button>
      </Buttons>
    </Container>
  );
}
