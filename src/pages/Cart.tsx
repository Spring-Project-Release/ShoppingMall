import styled from "styled-components";
import Navigation from "../components/Navigation";
import DetailBar from "../components/DetailBar";
import Hood from "../components/Hood";
import { images, cardList } from "../jsons/imgList";
import { useState } from "react";
import { useForm } from "react-hook-form";
import Footer from "../components/Footer";
import Container from "../components/Container";

const AddressInput = styled.div``;

interface IAddressData {
  fullAddress: string;
  zoneCode: string;
}

export default function Cart() {
  const [address, setAddress] = useState<IAddressData | null>(null);

  const {
    register,
    handleSubmit,
    formState: { errors },
    setValue,
  } = useForm<IAddressData>();

  const handleAddressSearch = () => {
    window.daum.postcode.load(() => {
      new window.daum.Postcode({
        oncomplete: (data: any) => {
          const fullAddress = data.address;

          setValue("fullAddress", fullAddress);

          console.log(data);
        },
      }).open();
    });
  };

  const onValid = (data: IAddressData) => {
    console.log(data);
  };

  return (
    <Container>
      <Hood title="장바구니" />

      {/* BODY */}
      <div className="w-full flex flex-col justify-center items-center">
        {/* CART */}
        <div className="mt-6 w-3/5">
          <h2 className="text-2xl font-bold">장바구니</h2>
          {images &&
            images.items.map((item) => (
              // CART-BOX
              <div className="w-full mt-12 h-44 border border-gray-300 mx-6 flex flex-row">
                {/* CART-INFO */}
                <div className="w-2/3 flex flex-col justify-between p-6">
                  <div>
                    <h3>{item.name}</h3>
                    <p>{item.price}</p>
                  </div>

                  <p>수량</p>
                </div>
                {/* CART-IMG */}
                <div
                  style={{
                    backgroundImage: `url(${item.url})`,
                  }}
                  className="w-1/3 h-full bg-cover bg-center"
                ></div>
              </div>
            ))}
          <div className="w-full h-auto border-t mt-12 border-gray-300 mx-6 py-6 flex flex-col justify-around items-center">
            <div className="flex flex-row w-full justify-between mt-12">
              <h2 className="text-2xl font-bold">총 합계</h2>
              <h1 className="text-3xl font-bold">123123123 원</h1>
            </div>

            <div className="w-full grid grid-cols-8 flex-row justify-between gap-2 mt-12">
              {cardList &&
                cardList.cards.map((card) => (
                  // CARD
                  <div
                    key={card.cardNumber}
                    className="border border-gray-300 flex justify-center items-center p-1 cursor-pointer transition-colors duration-300 ease-in-out hover:bg-gray-300"
                  >
                    {card.cardName}
                  </div>
                ))}
            </div>

            <div className="w-full h-96 border-t mt-12 border-gray-300 mx-6 py-6 flex flex-col justify-start items-start">
              <div className="w-1/2">
                <h2 className="mb-3">주소 입력</h2>
                <form
                  onSubmit={handleSubmit(onValid)}
                  className="flex flex-col gap-3"
                >
                  {/* ADDRESSLINE */}
                  <div className="flex flex-row justify-between">
                    <input
                      className="w-full h-8 border border-gray-300"
                      defaultValue={address?.fullAddress}
                      {...register("fullAddress", {
                        required: "주소를 등록해 주세요",
                      })}
                      readOnly
                    />
                    {/* ADDRESS INPUT */}
                    <div
                      className="w-2/5 flex justify-center items-center cursor-pointer bg-gray-300 text-white"
                      onClick={handleAddressSearch}
                    >
                      <p>주소 입력</p>
                    </div>
                  </div>
                  <input
                    className="w-full h-8 border border-gray-300"
                    defaultValue={address?.zoneCode}
                    {...register("zoneCode", {
                      required: "상세 주소를 등록해 주세요.",
                    })}
                  />
                </form>
              </div>
              <div className="text-white px-6 py-12 cursor-pointer flex justify-center items-center">
                결제 하기
              </div>
            </div>
          </div>
        </div>
      </div>
    </Container>
  );
}
