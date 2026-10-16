import { useState, type FormEvent } from "react";
import { Link } from "react-router";
import AuthLayout from "../components/AuthLayout";
import Button from "../components/Button";
import Input from "../components/Input";
import Select from "../components/Select";
import { residenceRankings } from "../data/mockData";

function RegisterPage() {
  const [submitted, setSubmitted] = useState(false);

  function handleSubmit(event: FormEvent) {
    event.preventDefault();
    setSubmitted(true);
  }

  if (submitted) {
    return (
      <AuthLayout title="Presque fini !" subtitle="Il ne reste qu'à confirmer votre adresse.">
        <div className="flex h-full flex-col items-center justify-center text-center">
          <span className="flex size-16 items-center justify-center rounded-full bg-primary/10 text-3xl">✉️</span>
          <h2 className="mt-4 text-2xl font-bold">Vérifiez vos e-mails</h2>
          <p className="mt-2 text-slate-600">
            Un lien de confirmation vous a été envoyé. Il est valable 24 heures.
          </p>
        </div>
      </AuthLayout>
    );
  }

  return (
    <AuthLayout title="Inscription" subtitle="Rejoignez votre résidence et défiez les autres.">
      <form onSubmit={handleSubmit} className="space-y-4">
        <Input label="Prénom" name="firstName" required />
        <Input label="E-mail (@etud.univ-pau.fr)" name="email" type="email" required />
        <Input label="Mot de passe" name="password" type="password" minLength={8} required />

        <Select
          label="Résidence"
          name="residence"
          required
          options={residenceRankings.map((residence) => ({
            value: String(residence.id),
            label: residence.name,
          }))}
        />

        <Button type="submit" className="w-full">
          S'inscrire
        </Button>
      </form>

      <p className="mt-6 text-center text-sm text-slate-600">
        Déjà inscrit ?{" "}
        <Link to="/connexion" className="font-semibold text-primary hover:underline">
          Se connecter
        </Link>
      </p>
    </AuthLayout>
  );
}

export default RegisterPage;
