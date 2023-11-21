import styled from "styled-components";
import Navigation from "../components/Navigation";
import DetailBar from "../components/DetailBar";
import Hood from "../components/Hood";

const Container = styled.div`
  height: auto;
`;

export default function MyPage() {
  return (
    <Container>
      <Hood title="마이 페이지" />
      <Navigation />
      <DetailBar />
    </Container>
  );
}
