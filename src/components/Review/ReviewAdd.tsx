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
    <form onSubmit={handleSubmit(onValid)} className="w-full flex flex-col">
      <input {...register("itemId")} hidden value={pathname} />
      <textarea
        {...register("review")}
        className="h-36 bg-slate-500"
      ></textarea>
      <button>작성</button>
    </form>
  );
}
