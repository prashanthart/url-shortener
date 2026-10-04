export default function ErrorAlert({error}){

    return (
        <div className="action_item px-5 mb-2 mt-4">
          <div className="bg-red-950 rounded-xl py-3 px-5 text-accent">{error}</div>
        </div>
    )

}

