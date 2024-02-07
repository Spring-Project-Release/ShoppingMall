import { useEffect, useState } from "react";

interface IReviewProps {
  memberId: string;
  itemId: string;
  reviewId: string;
  reviewText: string;
  likes: number;
}

interface IReviewMemberProps {
  grades: string[];
  memberName: string;
}

export default function Review({
  memberId,
  itemId,
  reviewId,
  reviewText,
  likes,
}: IReviewProps) {
  const [isMemberData, _] = useState<IReviewMemberProps>({
    grades: ["일반"],
    memberName: "이준모",
  });

  useEffect(() => {
    // memberId 로 api 요청해서 유저 값 가져오기 로직 추가 예정
  }, []);

  return (
    <div className="flex flex-row justify-between px-4 py-6 border-t border-slate-300">
      <div className="flex flex-col justify-start items-start w-1/4">
        <div className="flex flex-row gap-1 w-full">
          {isMemberData.grades.map((grade: string) => (
            <div className="bg-lime-500 flex justify-center items-center rounded-md px-2 py-1 text-xs border border-lime-500">
              <p className="text-white">{grade}</p>
            </div>
          ))}

          <div className="flex justify-center items-center">
            <h2 className="text-sm">{isMemberData.memberName}</h2>
          </div>
        </div>
      </div>
      {/* USER TAB */}
      <div className="w-3/4 flex flex-col gap-3">
        <h2 className="text-slate-500 text-base">
          {itemId || "[일일특가] 맛있는 메론"}
        </h2>
        <div className="text-slate-700 text-wrap text-sm">
          <p>
            123sldkfjsdifadf;adklfjadlf;kadhf;aldfkhadfoㅇㄻㅇ롬;ㅇ랴ㅣ몽ㄻ[ㅐ랴몽ㄹ;미ㅏㅇㄴ로
            ㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴ
          </p>
        </div>
        {/* TEXT BOX */}

        <div className="flex flex-row gap-1 justify-start">
          <div className="w-24 h-24 bg-red-500" />
          <div className="w-24 h-24 bg-red-500" />
          <div className="w-24 h-24 bg-red-500" />
          <div className="w-24 h-24 bg-red-500" />
        </div>
        {/* IMAGE BOX */}

        <div className="text-sm w-full flex flex-row justify-between items-center">
          <p className="text-slate-300">2024.01.01</p>
          <div className="px-2 gap-2 py-1 flex flex-row justify-around items-center text-slate-300 border-slate-300 border rounded-full">
            <h2>추천</h2>
            <h2>12</h2>
          </div>
        </div>
        {/* Review Info */}
      </div>
      {/* DETAIL REVIEW */}
    </div>
  );
}
