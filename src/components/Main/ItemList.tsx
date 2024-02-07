interface IItemListProps {
  list: any;
  onMove: Function;
  //   추후 list 의 object 형식을 참고해야 함
}

/**
 *
 * 추후 type definition 필수
 *
 * @param 아이템 리스트, onMove 함수
 * @returns 리스트 묶음 반환
 */
export default function ItemList({ list, onMove }: IItemListProps) {
  return (
    <div className="grid grid-cols-4 gap-4 mt-12">
      {list.items.map((item: any) => (
        <div
          key={item.itemNumber}
          className="h-[420px] flex flex-col justify-start"
        >
          <div className="relative rounded-lg overflow-hidden">
            <img
              src={item.url}
              onClick={() => onMove(item.itemNumber)}
              className="w-full flex h-[240px] cursor-pointer transform transition-transform duration-300 ease-in-out hover:scale-110"
            />
            <span className="px-2 py-1 text-sm absolute top-2 left-2 text-white bg-lime-500 rounded-lg">
              일일 특가
            </span>
          </div>
          <div className="hover:bg-lime-500 hover:text-white transition-color ease-in-out duration-300 hover:cursor-pointer flex my-6 justify-center items-center gap-2 border hover:border-lime-200 border-slate-200 py-2">
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
                d="M2.25 3h1.386c.51 0 .955.343 1.087.835l.383 1.437M7.5 14.25a3 3 0 00-3 3h15.75m-12.75-3h11.218c1.121-2.3 2.1-4.684 2.924-7.138a60.114 60.114 0 00-16.536-1.84M7.5 14.25L5.106 5.272M6 20.25a.75.75 0 11-1.5 0 .75.75 0 011.5 0zm12.75 0a.75.75 0 11-1.5 0 .75.75 0 011.5 0z"
              />
            </svg>
            <h2>담기</h2>
          </div>
          <div className="w-full h-1/5">
            <p
              className="cursor-pointer truncate"
              onClick={() => onMove(item.itemNumber)}
            >
              {item.name}
            </p>
            <div className="w-full flex flex-row justify-between">
              <h2 className="font-bold text-lg">{item.price}</h2>
            </div>
          </div>
        </div>
      ))}
    </div>
  );
}
