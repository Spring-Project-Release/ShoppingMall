import styled from "styled-components";
import { IRankingProps } from "./Ranking";
import { useNavigate } from "react-router-dom";

const Container = styled.div`
  display: flex;
  flex-direction: column;

  justify-content: space-between;
  align-items: center;
  width: 100%;
  height: 100%;
  background-color: yellow;

  cursor: pointer;
`;

const Rank = styled.div`
  width: 100%;
  height: 12%;
  background-color: purple;
  display: flex;
  flex-direction: column;
  justify-content: start;
  align-items: center;

  h4 {
    width: 100%;
  }

  img {
    width: 100%;
  }
`;

interface IRankingBoxProps {
  data: IRankingProps;
}

export default function RankingBox({ data }: IRankingBoxProps) {
  const nav = useNavigate();

  const onMove = () => {
    nav(`detail/${data.itemNumber}`);
  };

  return (
    <Container onClick={onMove}>
      <Rank>
        <h4>{data.rank} 위</h4>
        <img src={data.url} />
        <p>{data.author}</p>
        <b>{data.name}</b>
        <p>{data.price}</p>
      </Rank>
    </Container>
  );
}
