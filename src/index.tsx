import React from "react";
import ReactDOM from "react-dom/client";
import App from "./App";
import { BrowserRouter } from "react-router-dom";
import { createGlobalStyle } from "styled-components";

const root = ReactDOM.createRoot(
  document.getElementById("root") as HTMLElement
);

const Grobals = createGlobalStyle`
  * {
    margin: 0;
    padding: 0;
  }

  body {
    user-select: none;
  }
`;

root.render(
  <BrowserRouter>
    <Grobals />
    <App />
  </BrowserRouter>
);
