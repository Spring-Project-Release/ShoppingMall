export const addRecent = (itemNumber: string) => {
  const current = sessionStorage.getItem("recentList");
  const currentArray = current ? JSON.parse(current) : [];
  // 현재 값
  if (!currentArray.includes(itemNumber)) {
    currentArray.push(itemNumber);
    sessionStorage.setItem("recentList", JSON.stringify(currentArray));
  }
};
