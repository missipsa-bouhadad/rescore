import type { ReactNode } from "react";

type AuthLayoutProps = {
  title: string;
  subtitle: string;
  children: ReactNode;
};

// Shared layout of the account pages
function AuthLayout({ title, subtitle, children }: AuthLayoutProps) {
  return (
    <main className="mx-auto max-w-5xl px-4 py-6 md:py-12">
      <div className="rounded-3xl bg-white shadow-xl md:grid md:grid-cols-2">
        {/* Green panel */}
        <div className="relative overflow-hidden rounded-t-3xl bg-linear-to-br from-primary to-primary-dark px-6 py-8 text-white md:rounded-r-none md:rounded-l-3xl md:p-10">
          {/* App name, page title and subtitle */}
          <div className="relative">
            <p className="text-sm font-bold uppercase tracking-widest text-accent">ReScore</p>
            <h1 className="mt-2 text-3xl font-bold md:text-4xl">{title}</h1>
            <p className="mt-2 text-white/80">{subtitle}</p>
          </div>
        </div>

        {/* Page content */}
        <div className="p-6 md:p-10">{children}</div>
      </div>
    </main>
  );
}

export default AuthLayout;
