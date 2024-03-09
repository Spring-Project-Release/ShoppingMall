import Hood from "../components/Hood";
import { useEffect, useState } from "react";
import { images } from "../jsons/imgList";
import useScrollReset from "../utils/useScrollReset";
import Container from "../components/Container";
import ItemListBox from "../components/Main/ItemListBox";
import ItemList from "../components/Main/ItemList";
import { getItemList } from "../apis/api";

/**
 * LJM 2024.03.08
 * 추후 상품 조회 API 연결시 다시 손 볼 예정
 * @returns
 */
export default function Recent() {
  const reset = useScrollReset();
  const [isList, setIsList] = useState<string[]>([]);

  const onMove = (itemNumber: string) => {
    reset(`/detail/${itemNumber}`);
  };

  const onTest = async () => {
    try {
      console.log(`GETITEMLIST TEST ::`);
      const response = await getItemList();
      console.log(response);
      console.log(`:: SUCCESS`);
    } catch (error) {
      console.log(`ERROR`);
      console.error(error);
    } finally {
      console.log(`:: TEST END`);
    }
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
      <div className="px-32 py-6 flex flex-col">
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
          <div className="mt-6">
            <ItemList list={images} onMove={onMove} />
          </div>
        )}
      </div>
      <div onClick={onTest}>
        <h2>TEST BUTTON</h2>
      </div>
    </Container>
  );
}
