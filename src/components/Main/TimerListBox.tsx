import { ReactNode } from "react";
import { images } from "../../jsons/imgList";
import TimerList from "./TimerList";

interface IItemListBoxProps {
  children: ReactNode;
  onMove: Function;
}

export default function TimerListBox({ children, onMove }: IItemListBoxProps) {
  /**
   * 추후 리스트 API 로 가져와야 함
   *
   */
  return (
    <div className="mt-12 py-18 px-32 flex flex-col">
      {children}
      <TimerList list={images} onMove={onMove} />
    </div>
  );
}
