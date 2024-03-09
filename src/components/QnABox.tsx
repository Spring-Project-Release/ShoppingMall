import { useState } from "react";

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
    // BOX
    <div className="flex flex-col text-xs">
      {/* QNA */}
      <div
        onClick={onClicked}
        className={`flex flex-row border-b border-gray-300 cursor-pointer
        ${isClicked && "bg-lime-100"} transition-color duration-300`}
      >
        <div className="px-6 flex flex-col justify-center items-center w-1/3 font-bold">
          {type}
        </div>
        <div
          className="py-9 px-12 flex justify-start items-center w-2/3"
          style={{ whiteSpace: "pre-line" }}
        >
          {question}
        </div>
      </div>

      <div
        className={`${
          !isClicked && "hidden"
        } flex flex-row bg-lime-50 text-slate-700 ${
          isClicked ? "opacity-100" : "opacity-0"
        } transition-opacity duration-300`}
      >
        <div className="px-6 flex flex-col justify-center items-center w-1/3 font-bold">
          답 변
        </div>
        <div
          className="py-9 px-12 flex justify-start items-center w-2/3"
          style={{ whiteSpace: "pre-line" }}
        >
          {answer}
        </div>
      </div>
    </div>
  );
}
