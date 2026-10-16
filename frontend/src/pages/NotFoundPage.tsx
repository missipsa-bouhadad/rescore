import { Link } from "react-router";

function NotFoundPage() {
  return (
    <main className="mx-auto max-w-3xl px-4 py-16 text-center">
      <h1 className="text-4xl font-bold text-primary">Page introuvable</h1>
      <p className="mt-3">Cette page n'existe pas.</p>
      <Link to="/" className="mt-6 inline-block font-semibold text-primary underline">
        Retour à l'accueil
      </Link>
    </main>
  );
}

export default NotFoundPage;
