import { ReactNode } from "react";
import Navigation from "./Navigation";
import DetailBar from "./DetailBar";
import Footer from "./Footer";

interface IContainerProps {
  children: ReactNode;
}

export default function Container({ children }: IContainerProps) {
  return (
    <div className="flex flex-col relative">
      <Navigation />
      <DetailBar />

      <div>
        {/* MAIN */}
        {children}
      </div>

      <Footer />
    </div>
  );
}
