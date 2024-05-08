import { useSearchParams, useNavigate } from "react-router-dom";
import { useEffect } from "react";

function ReceivePage() {
  const [searchParams] = useSearchParams();
  const navigate = useNavigate();

  useEffect(() => {
    const accessToken = searchParams.get("accessToken");

    console.log("accessToken", accessToken);

    if (accessToken !== null) {
        localStorage.setItem("accessToken", accessToken);
    }

    if (localStorage.getItem("accessToken")) {
        return navigate("/main");
    }
  }, []);


  return (
    <div>
      <h1>Receive Page</h1>
    </div>
  );
}

export default ReceivePage;