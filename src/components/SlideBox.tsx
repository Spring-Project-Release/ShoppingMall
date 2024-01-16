interface ISlideBoxProps {
  rank: number;
  url: string;
  name: string;
  author: string;
  price: string;
}

export default function SlideBox(data: ISlideBoxProps) {
  return (
    <div className="w-3/12 h-1/2">
      <div className="w-full h-1/5">{data.rank}</div>
    </div>
  );
}
