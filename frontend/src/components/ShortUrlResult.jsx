import { useState } from "react";

function formatDate(iso) {
  if (!iso) return "No expiry";
  return new Date(iso).toLocaleDateString("en-US", {
    month: "short",
    day: "numeric",
    year: "numeric",
  });
}

function ShortUrlResult(props) {
  const date = formatDate(props.result.expiresAt);
  const [copied,setCopied] = useState(false);

  const handleCopy = async ()=>{
    try{
        await navigator.clipboard.writeText(props.result.shortUrl);
        setCopied(true);
        setTimeout(()=>setCopied(false),1500);

    }catch{

    }

  }

  return (
    <div className="rounded-xl bg-brand px-6 mt-2">
      <div className="py-6">
        <small className="flex mb-2 items-center text-green-500">
          <svg
            xmlns="http://www.w3.org/2000/svg"
            fill="none"
            viewBox="0 0 24 24"
            strokeWidth={1.5}
            stroke="currentColor"
            className="size-4 mr-1"
          >
            <path
              strokeLinecap="round"
              strokeLinejoin="round"
              d="M9 12.75 11.25 15 15 9.75M21 12a9 9 0 1 1-18 0 9 9 0 0 1 18 0Z"
            />
          </svg>
          Your short link is ready.
        </small>
        <div className="py-4 flex items-center rounded-xl bg-accent ">
          <a
            href={props.result.shortUrl}
            className="text-brand px-2 font-bold  flex-1 min-w-0 truncate"
            target="_blank"
          >
            {props.result.shortUrl}
          </a>
          <button className="rounded-xl mx-2 px-4 bg-brand-deep py-1 transition font-medium cursor-pointer"
          onClick={handleCopy}
          >
            {copied ? 'Copied':'Copy'}
          </button>
        </div>

        <div className="">
          <small className="block mb-2 mt-2 text-mist">Expires {date}</small>
          <small className=" mb-2 mt-2 text-mist truncate">
            {props.originalUrl}
          </small>
        </div>
      </div>
    </div>
  );
}
export default ShortUrlResult;
