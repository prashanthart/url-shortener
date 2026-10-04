import { useState } from "react";
import ErrorAlert from "./ErrorAlert";

function ShorturlForm(props) {
  const [originalUrl, setOriginalUrl] = useState("");
  const [customAlias, setCustomAlias] = useState("");
  const [expiryDate, setExpiryDate] = useState("365");

  const handleSubmit = (e) => {
    e.preventDefault();
    const payload = {
      originalUrl,
      customAlias,
      expiryDate,
    };
    props.onSubmit(payload);
  };

  const handleUrlChange = (e) => {
    setOriginalUrl(e.target.value);
  };

  const handlecustomAliasChange = (e) => {
    setCustomAlias(e.target.value);
  };

  const handleExpiryChange = (e) => {
    setExpiryDate(e.target.value);
  };

  return (
    <div className="rounded-xl bg-brand p-2">
      <form onSubmit={handleSubmit}>
        <div className="px-5 mt-2 mb-2">
          <label className="block py-1" htmlFor="longUrl">
            Long Url
          </label>
          <input
            className="w-full bg-brand-deep py-3 rounded-xl border border-slate-300 dark:border-slate-600 text-slate-300 px-2"
            placeholder="http://example.com/a/very/long/path?with=params"
            type="text"
            id="longUrl"
            onChange={handleUrlChange}
            value={originalUrl}
          />
        </div>

        <div className="grid gap-4 sm:grid-cols-2 px-5 mb-2 mt-2">
          <div className="mb-2">
            <label className="block py-1" htmlFor="customAlias">
              Custome Alias
              <small className="text-slate-400"> (optional)</small>
            </label>
            <input
              className="w-full bg-brand-deep py-3 rounded-xl border border-slate-300 dark:border-slate-600 text-slate-300 px-2"
              placeholder="my-link"
              type="text"
              id="customAlias"
              onChange={handlecustomAliasChange}
              value={customAlias}
            />
          </div>

          <div className="mb-2">
            <label className="block py-1" htmlFor="expires">
              Expires
              <small className="text-slate-400"> (optional)</small>
            </label>

            <select
              id="expires"
              value={expiryDate}
              onChange={handleExpiryChange}
              className="w-full bg-brand-deep py-3 rounded-xl border border-slate-300 dark:border-slate-600 text-slate-300 px-2"
            >
              <option value="365">In 1 year</option>
              <option value="1">In 1 day</option>
              <option value="7">In 1 week</option>
              <option value="30">In 1 month</option>
            </select>
          </div>
        </div>

        <div className="action_item px-5 mb-2 mt-2">
          <button
            type="submit"
            className="w-full bg-accent font-medium text-black rounded-xl p-3
            hover:opacity-90 active:scale-[0.99] cursor-pointer transition"
          >
            Shorten link
          </button>
        </div>
        {props.error && <ErrorAlert error={props.error} />}
      </form>
    </div>
  );
}

export default ShorturlForm;
