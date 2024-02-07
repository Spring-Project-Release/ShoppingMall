export function timer(): string {
  const now = new Date();
  const tomorrow = new Date();
  tomorrow.setDate(now.getDate() + 1);
  tomorrow.setHours(0, 0, 0, 0);
  const diff = tomorrow.getTime() - now.getTime();

  const hours = Math.floor(diff / (1000 * 60 * 60));
  const minutes = Math.floor((diff / (1000 * 60)) % 60);
  const seconds = Math.floor((diff / 1000) % 60);

  return `${padWithZero(hours)}:${padWithZero(minutes)}:${padWithZero(
    seconds
  )}`;
}

function padWithZero(number: number): string {
  return number < 10 ? `0${number}` : number.toString();
}
