import styled from "styled-components";
import Navigation from "../components/Navigation";
import DetailBar from "../components/DetailBar";
import Hood from "../components/Hood";

const Container = styled.div`
  width: 100%;
`;

export default function Recent() {
  return (
    <Container>
      <Hood title="최근 본 상품" />
      <Navigation />
      <DetailBar />
    </Container>
  );
}
