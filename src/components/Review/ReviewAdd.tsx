import { useForm } from "react-hook-form";
import { postCreateReview } from "../../apis/api";
import { useLocation } from "react-router-dom";
import { useEffect } from "react";

export interface IReviewProps {
  itemId: number;
  review: string;
}

export default function ReviewAdd() {
  const { register, handleSubmit, trigger } = useForm<IReviewProps>();
  const { pathname } = useLocation();

  const onValid = async (data: IReviewProps) => {
    if (data.review) {
      try {
        console.log(data);
        console.log(`RESPONSE START ::`);
        const response = await postCreateReview(data);
        console.log(response);
      } catch (error) {
        console.error(error);
      } finally {
      }
    } else {
    }
  };

  useEffect(() => {
    console.log(pathname);
  }, []);

  return (
    <form
      onSubmit={handleSubmit(onValid)}
      className="w-full gap-4 flex flex-col"
    >
      <input
        {...register("itemId")}
        hidden
        value={Number(pathname.split("/")[2])}
      />
      <textarea
        {...register("review")}
        className="resize-none h-36 bg-lime-100 text-slate-700 focus:outline-none"
      />
      <button className="bg-lime-400 py-4 rounded-lg text-white font-bold">
        작 성
      </button>
    </form>
  );
}
