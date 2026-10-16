import { useNavigate } from "react-router";
import Button from "../components/Button";
import { latestMatches, residenceRankings } from "../data/mockData";

function HomePage() {
  const navigate = useNavigate();

  return (
    <main className="mx-auto max-w-3xl px-4 py-8">
      {/* Hero */}
      <section className="rounded-2xl bg-white px-6 py-10 shadow-sm">
        <h1 className="text-4xl font-bold text-primary">ReScore</h1>
        <p className="mt-3 text-lg">
          Organisez des matchs entre résidences étudiantes et suivez le classement de votre ville.
        </p>
        <div className="mt-6 flex flex-wrap gap-3">
          <Button onClick={() => navigate("/inscription")}>S'inscrire</Button>
          <Button variant="outline">Voir le classement</Button>
        </div>
      </section>

      {/* Ranking preview */}
      <section className="mt-10">
        <h2 className="text-2xl font-bold">Classement des résidences</h2>
        <ol className="mt-4 divide-y divide-slate-200 rounded-xl bg-white shadow-sm">
          {residenceRankings.map((residence, index) => (
            <li key={residence.id} className="flex items-center gap-4 px-4 py-3">
              <span className="w-6 font-bold text-primary">{index + 1}</span>
              <span className="flex-1">{residence.name}</span>
              <span className="font-semibold">{residence.points} pts</span>
            </li>
          ))}
        </ol>
      </section>

      {/* Latest results */}
      <section className="mt-10">
        <h2 className="text-2xl font-bold">Derniers résultats</h2>
        <ul className="mt-4 space-y-3">
          {latestMatches.map((match) => (
            <li key={match.id} className="rounded-xl bg-white px-4 py-3 shadow-sm">
              <p className="text-sm text-slate-500">
                {match.sport} · {match.date}
              </p>
              <p className="mt-1 grid grid-cols-[1fr_auto_1fr] items-center gap-3 font-semibold">
                <span>{match.home}</span>
                <span className="text-primary">
                  {match.homeScore} - {match.awayScore}
                </span>
                <span className="text-right">{match.away}</span>
              </p>
            </li>
          ))}
        </ul>
      </section>
    </main>
  );
}

export default HomePage;
