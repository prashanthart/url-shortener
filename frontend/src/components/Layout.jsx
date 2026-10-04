// const container = "mx-auto w-full max-w-xl px-5"
const container = "mx-auto w-full max-w-xl px-5";

function Layout({ children }) {
  return (
    <div className="flex min-h-screen flex-col bg-brand text-white dark:bg-brand-deep">
      <main className={`${container} py-4`}>{children}</main>
      <footer className={`${container} text-mist`}>
        Links expire after 1 year unless you choose otherwise.
      </footer>
    </div>
  );
}
export default Layout;
