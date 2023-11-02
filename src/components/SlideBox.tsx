import styled from "styled-components";

const Container = styled.div`
  width: 24%;
  height: 50%;
`;

const Rank = styled.div`
  width: 100%;
  height: 20%;
`;

interface ISlideBoxProps {
  rank: number;
  url: string;
  name: string;
  author: string;
  price: string;
}

export default function SlideBox(data: ISlideBoxProps) {
  return (
    <Container>
      <Rank>{data.rank}</Rank>
    </Container>
  );
}
