import "../styles/globals.css";
import type { AppProps } from "next/app";

import ClippedDrawer from "../components/navbar/navbar";
import { ToastContainer } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";

function MyApp({ Component, pageProps }: AppProps) {
  return (
    <>
      <ClippedDrawer />
      <ToastContainer position="top-center" autoClose={5000} />
      <Component {...pageProps} />
    </>
  );
}

export default MyApp;
