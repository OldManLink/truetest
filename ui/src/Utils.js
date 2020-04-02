function range(start, end) {
  return (new Array(end - start + 1)).fill(0).map((_, i) => start + i)
}

function sleep(ms) {
  return new Promise(resolve => setTimeout(resolve, ms));
}


const Utils = { range, sleep };
export default Utils;
