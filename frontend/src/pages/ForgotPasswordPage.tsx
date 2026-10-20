import { useState, type FormEvent } from "react";
import { Link } from "react-router";
import AuthLayout from "../components/AuthLayout";
import Button from "../components/Button";
import EmailSentMessage from "../components/EmailSentMessage";
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
        <EmailSentMessage>
          Si un compte existe avec cette adresse, un lien pour changer le mot de passe vous a été envoyé.
        </EmailSentMessage>
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
