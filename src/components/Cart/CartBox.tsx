import { useState } from "react";
import PurcharseBox from "../Detail/PurchaseBox";
import { ICartListProps } from "../../pages/Cart";
import ItemList from "../Main/ItemList";

interface ICartBoxProps {
  item: any;
  isList: ICartListProps[];
  setIsList: Function;
}

export default function CartBox({ item, isList, setIsList }: ICartBoxProps) {
  const [isClicked, setIsClicked] = useState<boolean>(false);
  const [counter, setCounter] = useState(1);

  const onMinus = () => {
    if (1 < counter) {
      setCounter((current) => (current -= 1));
    }
  };

  const onPlus = () => {
    setCounter((current) => (current += 1));
  };

  const onClicked = () => {
    setIsClicked((current) => !current);
    if (isClicked) {
      setIsList((current: ICartListProps[]) =>
        current.filter((value) => value !== item.itemNumber)
      );
    } else {
      setIsList((current: ICartListProps[]) => [...current, item.itemNumber]);
    }
  };

  return (
    <div
      className={`w-full h-44 p-2 border-b last:border-none border-gray-200 flex flex-row justify-between items-center gap-2`}
    >
      {/* CART-IMG */}

      <div
        onClick={onClicked}
        className={`${
          isClicked
            ? "bg-lime-400 text-white border border-lime-400"
            : "bg-white text-slate-300 border border-slate-300"
        } w-8 h-8 rounded-full flex flex-col justify-center p-1 items-center cursor-pointer`}
      >
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
            d="m4.5 12.75 6 6 9-13.5"
          />
        </svg>
      </div>

      <div className="w-full flex flex-row justify-between items-center h-full">
        <div
          style={{
            backgroundImage: `url(${item.url})`,
          }}
          className="w-1/3 h-full bg-cover bg-center"
        ></div>
        <div className="w-1/2 flex flex-col gap-4">
          <h3>{item.name}</h3>

          <div className="flex flex-row justify-between">
            <PurcharseBox onPlus={onPlus} onMinus={onMinus} counter={counter} />

            {/* CART-INFO */}
            <div className="flex flex-col justify-between">
              <div>
                <p>{item.price * counter} 원</p>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}
