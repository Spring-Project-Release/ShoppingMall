import Hood from "../components/Hood";
import { images } from "../jsons/imgList";
import useScrollReset from "../utils/useScrollReset";
import Container from "../components/Container";
import ItemListBox from "../components/Main/ItemListBox";

export default function Special() {
  const reset = useScrollReset();

  const onMove = (itemNumber: string) => {
    reset(`/detail/${itemNumber}`);
  };
  return (
    <Container>
      <Hood title="랭킹" />

      {/* BODY */}
      {/* RECOMMAND ITEM */}
      <ItemListBox onMove={onMove}>
        <p className="text-2xl">지금 만나보세요</p>
        <h1 className="text-2xl font-bold">스페셜 물품</h1>
      </ItemListBox>
    </Container>
  );
}
