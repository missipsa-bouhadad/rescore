import { useEffect, useState, type SubmitEvent } from "react";
import { Link } from "react-router";
import { ApiError, getResidences, register, type Residence } from "../api";
import AuthLayout from "../components/AuthLayout";
import Button from "../components/Button";
import EmailSentMessage from "../components/EmailSentMessage";
import Input from "../components/Input";
import Select from "../components/Select";

// Only Pau for now
const CITY = "pau";

function RegisterPage() {
  const [residences, setResidences] = useState<Residence[]>([]);
  const [sentTo, setSentTo] = useState("");
  const [loading, setLoading] = useState(false);
  const [emailError, setEmailError] = useState("");
  const [error, setError] = useState("");

  // Load the residences of the city
  useEffect(() => {
    getResidences(CITY)
      .then(setResidences)
      .catch(() => setError("Impossible de charger les résidences. Réessayez plus tard."));
  }, []);

  async function handleSubmit(event: SubmitEvent<HTMLFormElement>) {
    event.preventDefault();
    const form = new FormData(event.currentTarget);
    const email = String(form.get("email"));
    setEmailError("");
    setError("");

    if (email !== email.toLowerCase()) {
      setEmailError("Saisissez l'adresse en minuscules.");
      return;
    }

    setLoading(true);
    try {
      await register({
        firstName: String(form.get("firstName")),
        email,
        password: String(form.get("password")),
        residenceId: Number(form.get("residence")),
      });
      setSentTo(email);
    } catch (e) {
      // Errors about the email 
      if (e instanceof ApiError && e.status === 409) {
        setEmailError("Cette adresse est déjà utilisée.");
      } else if (e instanceof ApiError && e.message === "Email domain not allowed") {
        setEmailError("Utilisez votre adresse @etud.univ-pau.fr.");
      } else if (e instanceof ApiError) {
        setError("L'inscription a échoué. Vérifiez les champs et réessayez.");
      } else {
        setError("Serveur injoignable. Réessayez plus tard.");
      }
    } finally {
      setLoading(false);
    }
  }

  if (sentTo) {
    return (
      <AuthLayout title="Presque fini !" subtitle="Il ne reste qu'à confirmer votre adresse.">
        <EmailSentMessage>
          Un lien de confirmation a été envoyé à <strong className="text-ink">{sentTo}</strong>. Il est valable 24
          heures. Pensez à vérifier vos spams.
        </EmailSentMessage>

        <Link to="/connexion" className="mt-6 block text-center text-sm font-semibold text-primary hover:underline">
          Retour à la connexion
        </Link>
      </AuthLayout>
    );
  }

  return (
    <AuthLayout title="Inscription" subtitle="Rejoignez votre résidence et défiez les autres.">
      <form onSubmit={handleSubmit} className="space-y-4">
        <Input label="Prénom" name="firstName" maxLength={100} required />
        <Input label="E-mail (@etud.univ-pau.fr)" name="email" type="email" error={emailError} required />
        <Input label="Mot de passe" name="password" type="password" minLength={8} maxLength={72} required />

        <Select
          label="Résidence"
          name="residence"
          required
          options={residences.map((residence) => ({
            value: String(residence.id),
            label: residence.name,
          }))}
        />

        {error && (
          <p role="alert" className="text-sm text-red-600">
            {error}
          </p>
        )}

        <Button type="submit" className="w-full" disabled={loading}>
          {loading ? "Inscription…" : "S'inscrire"}
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
