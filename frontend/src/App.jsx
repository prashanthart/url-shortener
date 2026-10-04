import Layout from "./components/Layout";
import ShorturlForm from "./components/ShorturlForm";
import ShortUrlResult from "./components/ShortUrlResult";
import shortUrl from "./apis/urlApi";
import { useState } from "react";

function App() {
  const [result,setResult] = useState(null);
  const [error,setError] = useState(null);
  const [originalUrl,setOriginalUrl] = useState();

  const handleSubmit = async (data) => {

    setOriginalUrl(data.originalUrl);
    try{
      const res = await shortUrl(data)
      setResult(res);
      setError(null);
    }
    catch(err){

      console.log("error",err?.message);

      setError(err?.message);
      setResult(null);
    }
  }
  return (
    <Layout>
      <h1 className="text-white text-5xl mb-5 font-bold leading-tight">
        Long links, cut down to size.
      </h1>
      <small className="block mb-4 text-mist">
        Paste any web address and get a short link you can share anywhere. Add
        your own alias or set an expiry if you need to.
      </small>
      <ShorturlForm onSubmit={handleSubmit} error = {error}></ShorturlForm>
     {result &&  <ShortUrlResult result={result} originalUrl={originalUrl}></ShortUrlResult>}
    </Layout>
  );
}

export default App;
