import React from "react";
import ReactDOM from "react-dom/client";
import App from "./App";
import { BrowserRouter } from "react-router-dom";
import { ThemeProvider, createGlobalStyle } from "styled-components";
import { theme } from "../src/utils/colors";

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
    <ThemeProvider theme={theme}>
      <Grobals />
      <App />
    </ThemeProvider>
  </BrowserRouter>
);
