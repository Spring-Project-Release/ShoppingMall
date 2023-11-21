import { useParams } from "react-router-dom";
import Navigation from "../components/Navigation";
import ProductDetail from "../components/ProductDetail";
import DetailBar from "../components/DetailBar";
import Hood from "../components/Hood";

export default function Detail() {
  const { productId } = useParams();

  return (
    <>
      <Hood title="상품정보" />
      <Navigation />
      <DetailBar />
      <ProductDetail productId={productId ? productId : "0"} />
    </>
  );
}
