import styled from "styled-components";
import Hood from "../components/Hood";
import { images, cardList } from "../jsons/imgList";
import { useEffect, useState } from "react";
import { useForm } from "react-hook-form";
import Container from "../components/Container";
import CartBox from "../components/Cart/CartBox";

export interface ICartListProps {
  itemId: number;
  amount: number;
  price: number;
}

export interface ICartProps {
  list?: ICartListProps[];
  address: string;
}

export default function Cart() {
  const [address, setAddress] = useState<string>();
  const [isList, setIsList] = useState<ICartListProps[]>([]);
  const [isTotal, setIsTotal] = useState<number>(0);
  const {
    register,
    handleSubmit,
    formState: { errors },
    setValue,
    getValues,
  } = useForm();

  const handleAddressSearch = () => {
    window.daum.postcode.load(() => {
      new window.daum.Postcode({
        oncomplete: (data: any) => {
          const fullAddress = data.address;

          setAddress(fullAddress);
          console.log(data);
          setValue("address", fullAddress);
        },
      }).open();
    });
  };

  const onValid = (data: any) => {
    console.log(data);
  };

  useEffect(() => {
    setValue("list", JSON.stringify(isList));
    const totalAmount = isList.reduce((accumulator, item) => {
      return accumulator + item.amount * item.price;
    }, 0);

    // isTotal 업데이트
    setIsTotal(totalAmount);
  }, [isList]);

  return (
    <Container>
      <Hood title="장바구니" />

      {/* BODY */}
      <div className="w-full flex flex-col justify-center items-center">
        {/* CART */}
        <div className="px-32 mt-6 w-full">
          <h2 className="text-2xl font-bold mb-12">장바구니</h2>
          <form onSubmit={handleSubmit(onValid)}>
            <input {...register("list")} hidden />
            <div className="flex flex-row justify-between items-start gap-6">
              <div className="flex flex-col w-3/5">
                {images &&
                  images.items.map((item, index) => (
                    // CART-BOX
                    <CartBox
                      key={index}
                      item={item}
                      isList={isList}
                      setIsList={setIsList}
                    />
                  ))}
              </div>

              <div className="relative w-2/5 text-slate-700 py-6 px-8 flex flex-col gap-4 justify-start items-center bg-lime-50">
                {/* 배송지 입력 */}
                <div className="w-full flex flex-row justify-start items-center gap-2 font-bold">
                  <svg
                    xmlns="http://www.w3.org/2000/svg"
                    fill="none"
                    viewBox="0 0 24 24"
                    strokeWidth="1.5"
                    stroke="currentColor"
                    className="w-6 h-6"
                  >
                    <path
                      strokeLinecap="round"
                      strokeLinejoin="round"
                      d="M15 10.5a3 3 0 1 1-6 0 3 3 0 0 1 6 0Z"
                    />
                    <path
                      strokeLinecap="round"
                      strokeLinejoin="round"
                      d="M19.5 10.5c0 7.142-7.5 11.25-7.5 11.25S4.5 17.642 4.5 10.5a7.5 7.5 0 1 1 15 0Z"
                    />
                  </svg>
                  <h2>배송지</h2>
                </div>

                <div className="w-full text-slate-700 text-sm">
                  {address ? address : "주소를 입력해 주세요."}
                </div>
                <input hidden {...register("address")} />

                <div
                  onClick={handleAddressSearch}
                  className="w-full text-white cursor-pointer bg-lime-400 flex justify-center items-center py-2"
                >
                  <span>배송지 변경</span>
                </div>

                {/* 결제 금액 */}
                <div className="w-full flex flex-col gap-4 text-slate-700">
                  <div className="w-full flex flex-row justify-between items-center">
                    <span>상품 금액</span>
                    <span>{isTotal} 원</span>
                  </div>
                  <div className="w-full flex flex-row justify-between items-center">
                    <span>상품 할인 금액</span>
                    <span>-{3000} 원</span>
                  </div>
                  <div className="w-full flex flex-row justify-between items-center">
                    <span>배송비</span>
                    <span>0 원</span>
                  </div>
                  <div className="w-full flex flex-row justify-between items-center">
                    <span>결제예정금액</span>
                    <span>{isTotal - 3000} 원</span>
                  </div>
                </div>

                <button className="focus:outline-none flex flex-col justify-center items-center py-4 absolute left-0 rounded-lg font-bold text-white cursor-pointer -bottom-20 w-full bg-lime-400">
                  <h2>주문 하기</h2>
                </button>

                <div className="absolute left-0 -bottom-40 text-xs text-slate-400">
                  <p>쿠폰/적립금은 주문서에서 사용가능합니다.</p>
                  <p>[주문완료] 상태일 때만 주문 취소 가능합니다.</p>
                  <p>
                    상품별로 적립금 기준이 다를 수 있습니다. (상품
                    상세페이지에서 확인 가능합니다.)
                  </p>
                </div>
              </div>
            </div>
          </form>
        </div>
      </div>
    </Container>
  );
}
