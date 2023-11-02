import styled from "styled-components";
import Navigation from "../components/Navigation";
import DetailBar from "../components/DetailBar";
import SideBar from "../components/SideBar";
import Ranking from "../components/Ranking";

const Container = styled.div`
  height: 300vh;
  width: 100%;
`;

const Body = styled.div`
  width: 100%;
  height: 100%;
  background-color: red;

  display: flex;
  flex-direction: row;
`;

const Article = styled.div`
  width: 80%;
  height: calc(100%);

  background-color: rebeccapurple;
  border-left: 1px solid gray;
`;

export default function Main() {
  return (
    <Container>
      <Navigation />
      <DetailBar />
      <Body>
        <SideBar />
        <Article>
          <Ranking />
        </Article>
      </Body>
    </Container>
  );
}
