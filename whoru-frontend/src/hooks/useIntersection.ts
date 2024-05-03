import { RefObject, useCallback, useState } from "react";

const useIntersection = (targetRef : RefObject<HTMLDivElement>) =>{
  let observeRef : IntersectionObserver | null = null;
  const [interSecting, setInterSecting] = useState(false);
  const getObserver = useCallback(()=>{
      if(!observeRef){
          observeRef = new IntersectionObserver(entries=>{
              setInterSecting(entries[0]?.isIntersecting);
          });
      }
      return observeRef;
  },[observeRef])
  if(!targetRef.current){
      console.log("TTT");
      return;
  }
  const elem = targetRef.current;
  const observer = getObserver();
  observer.observe(elem);
  
  
  return interSecting;
}
export default useIntersection;