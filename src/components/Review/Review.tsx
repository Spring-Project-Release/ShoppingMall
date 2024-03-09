import ReviewAdd from "./ReviewAdd";
import ReviewBox from "./ReviewBox";

export default function Review() {
  return (
    <div className="bg-transparent h-auto w-full">
      <div className="flex flex-col">
        {[1, 2, 3, 4, 5].map((value, index) => (
          <ReviewBox
            key={index}
            memberId={""}
            itemId={""}
            reviewId={""}
            reviewText={""}
            likes={0}
          />
        ))}

        <ReviewAdd />
      </div>
    </div>
  );
}
