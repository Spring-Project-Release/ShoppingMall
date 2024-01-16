import Hood from "../components/Hood";
import QnABox, { IQnAProps } from "../components/QnABox";
import { useEffect, useState } from "react";
import { qnas } from "../jsons/qnaList";
import Container from "../components/Container";

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

      {/* BODY */}
      <div className="w-full flex flex-col justify-start items-center mb-6 min-h-screen">
        <div className="mt-6 w-3/5">
          <div className="flex flex-row justify-start items-center gap-3">
            <h2 className="font-bold text-lg">FAQ</h2>
            <p>자주 묻는 질문</p>
          </div>
          <div className="flex flex-row gap-3 mt-7">
            {[
              "회원정보",
              "상품확인",
              "주문/결제",
              "배송",
              "교환/취소(반품)",
              "서비스",
              "전체",
            ].map((category: string, index: number) => (
              <div
                onClick={onTab}
                key={index}
                className="flex flex-col justify-center items-center px-3 py-2 cursor-pointer opacity-30 transition-opacity duration-300 ease-in-out hover:opacity-100"
              >
                <h4>{category}</h4>
              </div>
            ))}
          </div>

          <div className="flex flex-col mt-3 border-t-2 border-black w-full">
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
          </div>
        </div>
      </div>
    </Container>
  );
}
