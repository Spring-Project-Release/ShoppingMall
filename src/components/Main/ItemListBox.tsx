import { ReactNode } from "react";
import { images } from "../../jsons/imgList";
import ItemList from "./ItemList";

interface IItemListBoxProps {
  children: ReactNode;
  onMove: Function;
}

export default function ItemListBox({ children, onMove }: IItemListBoxProps) {
  /**
   * 추후 리스트 API 로 가져와야 함
   *
   */
  return (
    <div className="mt-12 py-18 px-8 flex flex-col">
      {children}
      <ItemList list={images} onMove={onMove} />
    </div>
  );
}
