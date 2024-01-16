import useScrollReset from "../utils/useScrollReset";

export default function Navigation() {
  const reset = useScrollReset();

  const onMove = (event: React.MouseEvent<HTMLElement>) => {
    let destination = event.currentTarget.id;

    destination === "home" ? reset("/") : reset(`/${destination}`);
  };

  return (
    <div className="w-full h-[10vh] bg-white flex flex-row justify-between items-center">
      <div
        onClick={onMove}
        id="home"
        className="h-full w-1/5 ml-3 flex flex-col justify-center items-center text-green-300 cursor-pointer"
      >
        <img className="h-auto w-3/5" src="../Frunet-icon.png" />
      </div>

      <div className="flex flex-row justify-center w-2/5 h-full">
        <div
          onClick={onMove}
          id={`sale`}
          className="flex flex-col justify-center items-center h-11 w-1/4 text-lime-400 cursor-pointer mt-2 nav-button-styles"
        >
          <h2 className="font-bold text-2xl">세 일</h2>
        </div>

        <div
          onClick={onMove}
          id={`special`}
          className="flex flex-col justify-center items-center h-11 w-1/4 text-lime-400 cursor-pointer mt-2 nav-button-styles"
        >
          <h2 className="font-bold text-2xl">스페셜</h2>
        </div>

        <div
          onClick={onMove}
          id={`ranking`}
          className="flex flex-col justify-center items-center h-11 w-1/4 text-lime-400 cursor-pointer mt-2 nav-button-styles"
        >
          <h2 className="font-bold text-2xl">랭킹</h2>
        </div>

        <div
          onClick={onMove}
          id={`recommand`}
          className="flex flex-col justify-center items-center h-11 w-1/4 text-lime-400 cursor-pointer mt-2 nav-button-styles"
        >
          <h2 className="font-bold text-2xl">추천</h2>
        </div>
      </div>
    </div>
  );
}
