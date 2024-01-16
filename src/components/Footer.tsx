export default function Footer() {
  return (
    <div className="h-[24vh] mx-12 bg-white">
      <div className="py-24 h-full grid grid-cols-3 flex-row justify-between">
        <div className="flex flex-col px-8 py-12 border-r border-gray-300">
          <h3 className="font-bold text-lg">공지사항</h3>
        </div>
        <div className="flex flex-col px-8 py-12 border-r border-gray-300">
          <h3 className="font-bold text-lg">조합원 상담실</h3>
          <h2 className="mt-8 text-3xl font-bold text-lime-400">0000-0000</h2>
          <p>
            평일
            <b className="ml-4">오전 9시 ~ 오후 6시</b>
          </p>
          <p>
            주말
            <b className="ml-4">공휴일 휴무</b>
          </p>
        </div>
        <div className="flex flex-col px-8 py-12 border-r border-gray-300 border-none">
          <h3 className="font-bold text-lg">자주 묻는 질문</h3>
        </div>
      </div>
    </div>
  );
}
