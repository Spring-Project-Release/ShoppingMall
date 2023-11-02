import styled from "styled-components";
import SlideCategory from "./SlideCategory";
import RankingBox from "./RankingBox";

import { images } from "../jsons/imgList";
import { useEffect, useState } from "react";

const Container = styled.div`
  width: 100%;
  height: 100vh;
  display: flex;
  flex-direction: row;
  justify-content: center;
  align-items: center;
`;

const RankingBoxs = styled.div`
  width: 80%;
  height: 100%;
  background-color: green;

  display: grid;
  grid-template-columns: 1fr 1fr 1fr 1fr;
  grid-template-rows: 1fr 1fr;
  flex-direction: row;
  gap: 12px;
`;

const RankDetail = styled.div`
  background-color: red;
`;

export interface IRankingProps {
  rank: number;
  url: string;
  name: string;
  author: string;
  price: string;
  itemNumber: string;
}

export default function Ranking() {
  const [data, setData] = useState<IRankingProps[]>();

  useEffect(() => {
    setData(images.items);
  }, []);

  return (
    <Container>
      <SlideCategory
        title={"실시간 랭킹"}
        category={["상의", "하의", "아우터", "신발"]}
      />
      <RankingBoxs>
        {data?.map((value) => (
          <RankingBox data={value} />
        ))}
      </RankingBoxs>
    </Container>
  );
}
