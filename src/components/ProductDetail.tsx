import { useState } from "react";
import { AiOutlineHeart } from "react-icons/ai";
import { AiTwotoneHeart } from "react-icons/ai";
import Review from "./Review/Review";

interface IProductProps {
  productId: string;
}

export default function ProductDetail({ productId }: IProductProps) {
  const [counter, setCounter] = useState(1);
  const [isHearted, setIsHearted] = useState(false);

  const onMinus = () => {
    if (1 < counter) {
      setCounter((current) => (current -= 1));
    }
  };

  const onPlus = () => {
    setCounter((current) => (current += 1));
  };

  const onHeart = () => {
    setIsHearted((current) => !current);
  };

  return (
    <div className="mt-6 flex flex-col justify-center items-center w-full h-auto">
      {/* HEADER */}
      <div className="w-4/5 flex flex-row justify-start items-start gap-12 mb-12">
        {/* IMG BOX */}
        <div className="w-1/2 h-[70vh] bg-yellow-300"></div>

        {/* PURCHASE */}
        <div className="w-1/2 h-full flex flex-col justify-start items-start gap-3">
          <p>특급 배송</p>
          <h2 className="text-lg font-bold">[일일특가] 머스크멜론 1.3kg</h2>
          <p>촉촉하게 머금은 달콤함</p>

          {/* PRICE */}
          <div className="flex flex-row justify-between w-1/2">
            <h1 className="text-2xl font-bold text-red-500">46%</h1>
            <h1 className="text-2xl font-bold">
              7900
              <span className="text-base ml-2">원</span>
            </h1>
          </div>

          <p>원산지: 국산</p>

          {/* DETAIL INFO */}
          <div className="w-full h-auto">
            {/* LINE */}
            <div className="border-t py-3 border-gray-300 flex flex-row justify-between px-4">
              {/* 
  &:last-child {
    border-bottom: 1px solid lightgray;
  }
              */}
              {/* HEAD */}
              <div className="flex flex-col justify-center w-1/4">
                <p>배 송</p>
              </div>
              <div className="w-3/4 flex flex-col justify-center">
                <h4 className="text-sm font-bold">샛별 배송</h4>
                <p className="text-xs">23시 전 주문시 아침 7시 전 도착</p>
              </div>
            </div>
            <div className="border-t py-3 border-gray-300 flex flex-row justify-between px-4">
              <div className="flex flex-col justify-center w-1/4">
                <p>판매자</p>
              </div>
              <div className="w-3/4 flex flex-col justify-center">
                <h4 className="text-sm font-bold">이준모</h4>
              </div>
            </div>

            <div className="border-t py-3 border-gray-300 flex flex-row justify-between px-4">
              <div className="flex flex-col justify-center w-1/4">
                <p>판매단위</p>
              </div>
              <div className="w-3/4 flex flex-col justify-center">
                <h4 className="text-sm font-bold">1통</h4>
              </div>
            </div>
            <div className="border-t py-3 border-gray-300 flex flex-row justify-between px-4">
              <div className="flex flex-col justify-center w-1/4">
                <p>중량/용량</p>
              </div>
              <div className="w-3/4 flex flex-col justify-center">
                <h4 className="text-sm font-bold">1.3kg 내외</h4>
              </div>
            </div>

            <div className="border-t py-3 border-gray-300 flex flex-row justify-between px-4">
              <div className="flex flex-col justify-center w-1/4">
                <p>포장타입</p>
              </div>
              <div className="w-3/4 flex flex-col justify-center">
                <h4 className="text-sm font-bold">냉장(종이포장)</h4>
              </div>
            </div>

            <div className="border-t py-3 border-gray-300 flex flex-row justify-between px-4">
              <div className="flex flex-col justify-center w-1/4">
                <p>유통기한</p>
              </div>
              <div className="w-3/4 flex flex-col justify-center">
                <p className="text-xs">
                  농산물은 유통기한이 없으나 최대한 빨리 드시길 권장합니다.
                </p>
              </div>
            </div>

            <div className="border-t border-b py-3 border-gray-300 flex flex-row justify-between px-4">
              <div className="flex flex-col justify-center w-1/4">
                <p>안내사항</p>
              </div>
              <div className="w-3/4 flex flex-col justify-center">
                <p className="text-xs">
                  상품 특성상 3% 내외의 중량 차이가 발생 할 수 있습니다.
                </p>
                <p className="text-xs">
                  신선식품 특성상 크기 및 형태가 일정하지 않을 수 있습니다.
                </p>
              </div>
            </div>
          </div>

          {/* PURCHASE BOX */}
          <div className="w-full h-auto border border-gray-300 px-6 py-3 flex flex-col gap-3 mt-12">
            <p>[일일특가] 머스크멜론 1.3kg</p>

            {/* PURCHASE BOX LINE */}
            <div className="flex flex-row justify-between items-center">
              {/* COUNT BOX */}
              <div className="flex flex-row">
                {/* COUNTERS */}
                <div
                  className="w-16 h-8 border-t border-b border-l border-gray-300 flex flex-col justify-center items-center cursor-pointer"
                  onClick={onMinus}
                >
                  -
                </div>
                <div className="w-16 h-8 border-t border-b border-l border-gray-300 flex flex-col justify-center items-center">
                  {counter}
                </div>
                <div
                  className="w-16 h-8 border-t border-b border-l border-r border-gray-300 flex flex-col justify-center items-center cursor-pointer"
                  onClick={onPlus}
                >
                  +
                </div>
              </div>
              <p>7900 원</p>
            </div>
          </div>

          {/* BUTTON BOX */}
          <div className="w-full h-auto">
            {/* BUTTON LINE */}
            <div className="flex flex-row justify-end items-end gap-3">
              <p className="text-sm">총 상품금액: </p>
              <h2 className="text-xl font-bold"> {counter * 7900} 원</h2>
            </div>

            {/* BUTTONS */}
            <div className="mt-12 flex flex-row justify-between items-center">
              {/* HEART */}
              <div
                onClick={onHeart}
                className="flex flex-col justify-center items-center cursor-pointer w-1/6 p-4 border border-gray-300"
              >
                {isHearted ? (
                  <AiTwotoneHeart size={24} className="text-lime-500" />
                ) : (
                  <AiOutlineHeart size={24} className="text-lime-500" />
                )}
              </div>
              {/* PURCHASE BUTTON */}
              <div className="w-4/5 px-4 py-4 border bg-lime-500 border-lime-500 text-white flex flex-col justify-center items-center cursor-pointer">
                구 매 하 기
              </div>
            </div>
          </div>
        </div>
      </div>
      <div className="flex flex-col w-4/5 h-auto">
        {/* INFO */}
        {/* INFO NAV */}
        <div className="w-full h-12 border border-gray-300 bg-white flex flex-row justify-between items-center sticky top-[6vh] z-50">
          {/* NAV BUTTON */}
          {["상품설명", "상세정보", "후 기", "문 의"].map((tag: string) => (
            <div className="flex justify-center items-center border-r border-gray-300 w-1/4 h-full cursor-pointer last:border-none">
              <h3>{tag}</h3>
            </div>
          ))}
        </div>
        <div className="bg-red-500 h-[100vh] w-full" />

        {/* 후 기 */}
        <Review />
      </div>
    </div>
  );
}
