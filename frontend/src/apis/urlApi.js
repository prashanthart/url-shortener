const API_URL = (
  import.meta.env.APP_BASE_URL || "https://url-shortener-ym7s.onrender.com"
).replace(/\/$/, "");

async function shortUrl(data){

    const body = {originalUrl: data.originalUrl };
  if (data.customAlias) body.customAlias = data.customAlias;
  if (data.expiryDate) body.expirationDate = new Date(Date.now() + Number(data.expiryDate) * 86400000).toISOString();


    let res;
    try{
        res = await fetch(`${API_URL}/api/shorten`, { 
            method:"POST",
            headers: {"Content-type":"application/json"},
            body: JSON.stringify(body)
        })
    }catch{
        throw new Error("Cannot reach the server. Please try again later."); 
    }

    const result = await res.json().catch(()=>null);

    if(!res.ok){
        throw new Error(result?.message || "Request Failed.");
    }

    return result;

}
export default shortUrl;