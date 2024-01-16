interface ISlideCategoryProps {
  title: string;
  category: string[];
}

export default function SlideCategory({
  title,
  category,
}: ISlideCategoryProps) {
  return (
    <div className="w-1/5 h-full bg-blue-400">
      <h4>{title}</h4>
      <div className="w-full">
        {category.map((item, index) => (
          <div className="w-full h-auto" key={index}>
            {item}
          </div>
        ))}
      </div>
      <div>
        {/* {images.items.map((item) => (
          <></>
        ))} */}
      </div>
    </div>
  );
}
