import { useParams } from "react-router-dom";
import Navigation from "../components/Navigation";
import ProductDetail from "../components/ProductDetail";
import DetailBar from "../components/DetailBar";
import Hood from "../components/Hood";
import Container from "../components/Container";

export default function Detail() {
  const { productId } = useParams();

  return (
    <Container>
      <Hood title="상품정보" />
      <ProductDetail productId={productId ? productId : "0"} />
    </Container>
  );
}
