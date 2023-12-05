import styled from "styled-components";
import Navigation from "../components/Navigation";
import DetailBar from "../components/DetailBar";
import Hood from "../components/Hood";
import QnABox, { IQnAProps } from "../components/QnABox";
import { useEffect, useState } from "react";
import { qnas } from "../jsons/qnaList";
import Footer from "../components/Footer";

const Container = styled.div`
  width: 100%;
`;

const Body = styled.div`
  width: 100%;
  display: flex;
  flex-direction: column;
  justify-content: start;
  align-items: center;

  margin-bottom: 24px;
  min-height: 100vh;
`;

const Main = styled.div`
  margin-top: 24px;
  width: 60%;
`;

const Headline = styled.div`
  display: flex;
  flex-direction: row;
  justify-content: start;
  align-items: center;
  gap: 12px;
`;

const TabList = styled.div`
  display: flex;
  flex-direction: row;
  gap: 12px;
  margin-top: 28px;
`;

const Tab = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;

  padding: 12px 6px;
  cursor: pointer;

  opacity: 0.3;
  transition: opacity 0.3s ease-in-out;
  &:hover {
    opacity: 1;
  }
`;

const QnAList = styled.div`
  display: flex;
  flex-direction: column;
  /* background-color: red; */

  margin-top: 12px;
  border-top: 3px solid black;

  width: 100%;
`;

export default function Cs() {
  const [isList, setIsList] = useState<IQnAProps[]>();
  const [isFilter, setIsFilter] = useState<string>("");

  const onTab = (event: React.MouseEvent<HTMLDivElement>) => {
    // console.log(event.target.innerText);
    let marker = event.currentTarget.innerText;
    marker === "전체" ? setIsFilter("") : setIsFilter(marker);
  };

  useEffect(() => {
    setIsList(qnas.qna);
  }, []);

  return (
    <Container>
      <Hood title="고객센터" />
      <Navigation />
      <DetailBar />
      <Body>
        <Main>
          <Headline>
            <h2>FAQ</h2>
            <p>자주 묻는 질문</p>
          </Headline>
          <TabList>
            <Tab onClick={onTab}>
              <h4>회원정보</h4>
            </Tab>
            <Tab onClick={onTab}>
              <h4>상품확인</h4>
            </Tab>
            <Tab onClick={onTab}>
              <h4>주문/결제</h4>
            </Tab>
            <Tab onClick={onTab}>
              <h4>배송</h4>
            </Tab>
            <Tab onClick={onTab}>
              <h4>교환/취소(반품)</h4>
            </Tab>
            <Tab onClick={onTab}>
              <h4>서비스</h4>
            </Tab>
            <Tab onClick={onTab}>
              <h4>전체</h4>
            </Tab>
          </TabList>

          <QnAList>
            {isList &&
              isList
                .filter((qna) => qna.type.includes(isFilter))
                .map((qna) => (
                  <QnABox
                    key={qna.key}
                    question={qna.question}
                    answer={qna.answer}
                    type={qna.type}
                  />
                ))}
          </QnAList>
        </Main>
      </Body>

      <Footer />
    </Container>
  );
}
