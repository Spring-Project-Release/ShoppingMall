import { useParams } from "react-router-dom";
import Navigation from "../components/Navigation";
import ProductDetail from "../components/ProductDetail";
import DetailBar from "../components/DetailBar";

export default function Detail() {
  const { productId } = useParams();

  return (
    <>
      <Navigation />
      <DetailBar />
      <ProductDetail productId={productId ? productId : "0"} />
    </>
  );
}
