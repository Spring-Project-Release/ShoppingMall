interface IPurchaseBoxProps {
  onMinus: React.MouseEventHandler<HTMLDivElement>;
  onPlus: React.MouseEventHandler<HTMLDivElement>;
  counter: number;
}

/**
 * LJM 2024.03.08 수량을 선택하는 박스
 * @param
 * @returns
 */
export default function PurcharseBox({
  onMinus,
  onPlus,
  counter,
}: IPurchaseBoxProps) {
  return (
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
    </div>
  );
}
