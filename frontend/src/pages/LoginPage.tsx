import type { FormEvent } from "react";
import { Link } from "react-router";
import AuthLayout from "../components/AuthLayout";
import Button from "../components/Button";
import Input from "../components/Input";

function LoginPage() {
  function handleSubmit(event: FormEvent) {
    event.preventDefault();
  }

  return (
    <AuthLayout title="Connexion" subtitle="Content de vous revoir ! Vos matchs vous attendent.">
      <form onSubmit={handleSubmit} className="space-y-4">
        <Input label="E-mail universitaire" name="email" type="email" required />
        <Input label="Mot de passe" name="password" type="password" required />

        <Link to="/mot-de-passe-oublie" className="block text-right text-sm font-semibold text-primary hover:underline">
          Mot de passe oublié ?
        </Link>

        <Button type="submit" className="w-full">
          Se connecter
        </Button>
      </form>

      <p className="mt-6 text-center text-sm text-slate-600">
        Pas encore de compte ?{" "}
        <Link to="/inscription" className="font-semibold text-primary hover:underline">
          S'inscrire
        </Link>
      </p>
    </AuthLayout>
  );
}

export default LoginPage;
