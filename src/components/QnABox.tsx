import { useState } from "react";
import styled from "styled-components";

const Box = styled.div`
  display: flex;
  flex-direction: column;
`;

const QnAInfo = styled.div<{ hidden: boolean }>`
  display: ${(props) => (props.hidden ? "flex" : "none")};
  flex-direction: row;
  background-color: lightgray;
  color: #3e3d3d;
`;

const QnA = styled.div`
  display: flex;
  flex-direction: row;
  border-bottom: 1px solid lightgray;
  cursor: pointer;
`;

const Type = styled.div`
  padding: 24px 0px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  width: 30%;

  font-weight: bold;
`;

const Title = styled.div`
  padding: 24px 24px;
  display: flex;
  flex-direction: row;
  justify-content: start;
  align-items: center;
  width: 70%;
`;

export interface IQnAProps {
  question: string;
  answer: string;
  type: string;
  key: number;
}

export default function QnABox({ type, question, answer }: IQnAProps) {
  const [isClicked, setIsClicked] = useState(false);

  const onClicked = () => {
    setIsClicked((current) => !current);
  };

  return (
    <Box>
      <QnA onClick={onClicked}>
        <Type>{type}</Type>
        <Title>{question}</Title>
      </QnA>
      <QnAInfo hidden={isClicked}>
        <Type>답 변</Type>
        <Title>{answer}</Title>
      </QnAInfo>
    </Box>
  );
}
