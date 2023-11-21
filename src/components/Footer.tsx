import styled from "styled-components";
import { theme } from "../utils/colors";

const Container = styled.div`
  width: 100%;
  height: 24vh;

  margin-top: 48px;
  background-color: ${(props) => props.theme.white};
`;

const Contents = styled.div`
  padding: 0 72px;
  height: 100%;

  display: grid;
  grid-template-columns: 1fr 1fr 1fr;
  flex-direction: row;
  justify-content: space-between;
`;

const Content = styled.div`
  display: flex;
  flex-direction: column;

  padding: 0px 48px;

  border-right: 1px solid ${(props) => props.theme.gray};

  &:last-child {
    border: none;
  }

  h2 {
    margin-top: 12px;
    color: ${(props) => props.theme.green};
    font-size: 36px;
  }

  b {
    margin-left: 12px;
  }
`;

export default function Footer() {
  return (
    <Container>
      <Contents>
        <Content>
          <h3>공지사항</h3>
        </Content>
        <Content>
          <h3>조합원 상담실</h3>
          <h2>0000-0000</h2>
          <p>
            평일
            <b>오전 9시 ~ 오후 6시</b>
          </p>
          <p>
            주말
            <b>공휴일 휴무</b>
          </p>
        </Content>
        <Content>
          <h3>자주 묻는 질문</h3>
        </Content>
      </Contents>
    </Container>
  );
}
