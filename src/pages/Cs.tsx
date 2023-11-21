import styled from "styled-components";
import Navigation from "../components/Navigation";
import DetailBar from "../components/DetailBar";

const Container = styled.div`
  width: 100%;
`;

export default function Cs() {
  return (
    <Container>
      <Navigation />
      <DetailBar />
    </Container>
  );
}
