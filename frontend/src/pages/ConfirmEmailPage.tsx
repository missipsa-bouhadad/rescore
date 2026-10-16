import { useNavigate } from "react-router";
import AuthLayout from "../components/AuthLayout";
import Button from "../components/Button";

function ConfirmEmailPage() {
  const navigate = useNavigate();

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
