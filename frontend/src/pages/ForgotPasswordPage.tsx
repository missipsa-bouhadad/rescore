import { useState, type FormEvent } from "react";
import { Link } from "react-router";
import AuthLayout from "../components/AuthLayout";
import Button from "../components/Button";
import Input from "../components/Input";

function ForgotPasswordPage() {
  const [submitted, setSubmitted] = useState(false);

  function handleSubmit(event: FormEvent) {
    event.preventDefault();
    setSubmitted(true);
  }

  return (
    <AuthLayout title="Mot de passe oublié" subtitle="Pas de panique, on vous envoie un lien pour le changer.">
      {submitted ? (
        <div className="text-center">
          <span className="mx-auto flex size-16 items-center justify-center rounded-full bg-primary/10 text-3xl">✉️</span>
          <p className="mt-4 text-slate-600">
            Si un compte existe avec cette adresse, un lien pour changer le mot de passe vous a été envoyé.
          </p>
        </div>
      ) : (
        <form onSubmit={handleSubmit} className="space-y-4">
          <Input label="E-mail universitaire" name="email" type="email" required />
          <Button type="submit" className="w-full">
            Envoyer le lien
          </Button>
        </form>
      )}

      <Link to="/connexion" className="mt-6 block text-center text-sm font-semibold text-primary hover:underline">
        Retour
      </Link>
    </AuthLayout>
  );
}

export default ForgotPasswordPage;
