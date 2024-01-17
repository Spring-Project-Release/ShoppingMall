import React from "react";
import ReactDOM from "react-dom/client";
import App from "./App";
import { BrowserRouter } from "react-router-dom";
import { ThemeProvider, createGlobalStyle } from "styled-components";
import { theme } from "../src/utils/colors";
import "./css/index.css";
import { RecoilRoot } from "recoil";

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
    min-width: 1200px;
  }
`;

root.render(
  <BrowserRouter>
    <ThemeProvider theme={theme}>
      <Grobals />
      <RecoilRoot>
        <App />
      </RecoilRoot>
    </ThemeProvider>
  </BrowserRouter>
);
