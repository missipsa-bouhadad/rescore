import { useEffect, useRef, useState } from "react";
import { Link, useNavigate, useSearchParams } from "react-router";
import { ApiError, verifyEmail } from "../api";
import AuthLayout from "../components/AuthLayout";
import Button from "../components/Button";

// Messages for the errors sent by the server
const errors: Record<string, string> = {
  "Link expired": "Ce lien a expiré ( valable pendant 24 heures).",
  "Link already used": "Ce lien a déjà été utilisé. Votre compte est déjà confirmé.",
};

function ConfirmEmailPage() {
  const navigate = useNavigate();
  // Reads the ?token=... of the URL
  const [searchParams] = useSearchParams();
  const token = searchParams.get("token");
  const [status, setStatus] = useState<"loading" | "success" | "error">(token ? "loading" : "error");
  const [error, setError] = useState("Ce lien n'est pas valide.");
  // Send the token only once (React runs effects twice in dev, the second call would say "already used" !)
  const sent = useRef(false);

  // Send the token of the link to the server
  useEffect(() => {
    if (!token || sent.current) return;
    sent.current = true;

    verifyEmail(token)
      .then(() => setStatus("success"))
      .catch((e) => {
        if (e instanceof ApiError) {
          setError(errors[e.message] ?? "Ce lien n'est pas valide.");
        } else {
          setError("Serveur injoignable. Réessayez plus tard.");
        }
        setStatus("error");
      });
  }, [token]);

  if (status === "loading") {
    return (
      <AuthLayout title="Confirmation" subtitle="Un instant…">
        <p className="text-center text-slate-600">Vérification de votre lien…</p>
      </AuthLayout>
    );
  }

  if (status === "error") {
    return (
      <AuthLayout title="Oups !" subtitle="La confirmation n'a pas fonctionné.">
        <div className="text-center">
          <h2 className="text-2xl font-bold">Lien non valide</h2>
          <p role="alert" className="mt-2 text-slate-600">
            {error}
          </p>
          <Link to="/connexion" className="mt-6 block text-sm font-semibold text-primary hover:underline">
            Retour à la connexion
          </Link>
        </div>
      </AuthLayout>
    );
  }

  return (
    <AuthLayout title="Bienvenue !" subtitle="Votre compte ReScore est prêt.">
      <div className="text-center">
        <span className="mx-auto flex size-16 items-center justify-center rounded-full bg-primary/10 text-primary">
          <svg
            className="size-8"
            viewBox="0 0 24 24"
            fill="none"
            stroke="currentColor"
            strokeWidth="3"
            strokeLinecap="round"
            strokeLinejoin="round"
            aria-hidden="true"
          >
            <polyline points="5 12 10 17 19 7" />
          </svg>
        </span>
        <h2 className="mt-4 text-2xl font-bold">Compte confirmé</h2>
        <p className="mt-2 text-slate-600">Votre adresse e-mail est vérifiée, vous pouvez vous connecter.</p>
        <Button onClick={() => navigate("/connexion")} className="mt-6 w-full">
          Se connecter
        </Button>
      </div>
    </AuthLayout>
  );
}

export default ConfirmEmailPage;
