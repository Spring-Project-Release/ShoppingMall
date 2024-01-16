import Hood from "../components/Hood";
import { useEffect, useState } from "react";
import { images } from "../jsons/imgList";
import useScrollReset from "../utils/useScrollReset";
import Container from "../components/Container";

export default function Recent() {
  const reset = useScrollReset();
  const [isList, setIsList] = useState<string[]>([]);

  const onMove = (itemNumber: string) => {
    reset(`/detail/${itemNumber}`);
  };

  useEffect(() => {
    const list = sessionStorage.getItem("recentList");
    if (list) {
      setIsList(JSON.parse(list));
    }
  }, []);

  return (
    <Container>
      <Hood title="최근 본 상품" />

      {/* BODY */}
      <div className="px-16 py-6 flex flex-col">
        <span className="flex flex-row gap-2">
          <p className="text-2xl">최근 본</p>
          <h1 className="text-2xl font-bold">상품</h1>
        </span>
        {isList.length === 0 ? (
          // EMPTY
          <div
            className="flex h-[60vh] flex-col justify-center items-center    
          "
          >
            <p className="text-2xl">최근 본 상품이 없습니다</p>
          </div>
        ) : (
          // RECOMMAND ITEM
          <div className="grid grid-cols-4 gap-3 mt-6">
            {images.items
              .filter((item) => isList.includes(item.itemNumber))
              .map((item) => (
                // PRODUCT
                <div
                  key={item.itemNumber}
                  className="h-[60vh] flex flex-col justify-between"
                >
                  <img
                    className="w-full bg-red-500 h-2/3 cursor-pointer transform duration-300 ease-in-out hover:scale-110"
                    src={item.url}
                    onClick={() => onMove(item.itemNumber)}
                  />
                  <div className="w-full h-1/5">
                    <p
                      className="cursor-pointer"
                      onClick={() => onMove(item.itemNumber)}
                    >
                      {item.name}
                    </p>
                    <div className="w-full flex flex-row justify-between">
                      <h2>{item.price}</h2>
                      <svg
                        xmlns="http://www.w3.org/2000/svg"
                        fill="none"
                        viewBox="0 0 24 24"
                        strokeWidth="1.5"
                        stroke="currentColor"
                        className="w-6 h-6 transition-colors duration-300 ease-in-out hover:text-lime-500 cursor-pointer"
                      >
                        <path
                          strokeLinecap="round"
                          strokeLinejoin="round"
                          d="M2.25 3h1.386c.51 0 .955.343 1.087.835l.383 1.437M7.5 14.25a3 3 0 00-3 3h15.75m-12.75-3h11.218c1.121-2.3 2.1-4.684 2.924-7.138a60.114 60.114 0 00-16.536-1.84M7.5 14.25L5.106 5.272M6 20.25a.75.75 0 11-1.5 0 .75.75 0 011.5 0zm12.75 0a.75.75 0 11-1.5 0 .75.75 0 011.5 0z"
                        />
                      </svg>
                    </div>
                  </div>
                </div>
              ))}
          </div>
        )}
      </div>
    </Container>
  );
}
