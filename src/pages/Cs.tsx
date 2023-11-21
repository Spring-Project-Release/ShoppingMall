import styled from "styled-components";
import Navigation from "../components/Navigation";
import DetailBar from "../components/DetailBar";
import Hood from "../components/Hood";

const Container = styled.div`
  width: 100%;
`;

export default function Cs() {
  return (
    <Container>
      <Hood title="고객센터" />
      <Navigation />
      <DetailBar />
    </Container>
  );
}
