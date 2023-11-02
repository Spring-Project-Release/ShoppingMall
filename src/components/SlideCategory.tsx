import { useState } from "react";
import styled from "styled-components";
import { images } from "../jsons/imgList";

const Container = styled.div`
  width: 20%;
  height: 100%;
  background-color: blue;
`;

const List = styled.div`
  width: 100%;
`;

const Item = styled.div`
  width: 100%;
  height: auto;
`;

interface ISlideCategoryProps {
  title: string;
  category: string[];
}

const Slider = styled.div``;

export default function SlideCategory({
  title,
  category,
}: ISlideCategoryProps) {
  return (
    <Container>
      <h4>{title}</h4>
      <List>
        {category.map((item, index) => (
          <Item key={index}>{item}</Item>
        ))}
      </List>
      <Slider>
        {/* {images.items.map((item) => (
          <></>
        ))} */}
      </Slider>
    </Container>
  );
}
