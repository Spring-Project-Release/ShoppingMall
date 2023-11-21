import { Helmet } from "react-helmet";

interface IHoodProps {
  title: string;
}

export default function Hood({ title }: IHoodProps) {
  return (
    <Helmet>
      <title>{`푸르넷\t|\t${title}`}</title>
    </Helmet>
  );
}
