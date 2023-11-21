import styled from "styled-components";
import Navigation from "../components/Navigation";
import DetailBar from "../components/DetailBar";

const Container = styled.div`
  height: auto;
`;

export default function MyPage() {
  return (
    <Container>
      <Navigation />
      <DetailBar />
    </Container>
  );
}
