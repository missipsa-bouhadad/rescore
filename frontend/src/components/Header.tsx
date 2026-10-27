import { useState } from "react";
import { Link, NavLink } from "react-router";

const links = [
  { to: "/", label: "Accueil" },
  { to: "/classement", label: "Classement" },
  { to: "/residences", label: "Résidences" },
  { to: "/matchs", label: "Matchs" },
  { to: "/connexion", label: "Connexion" },
];

// Active link has a light background
function linkClass({ isActive }: { isActive: boolean }) {
  return `block rounded-lg px-3 py-2 font-semibold text-white ${isActive ? "bg-white/20" : "hover:bg-white/10"}`;
}

function Header() {
  const [menuOpen, setMenuOpen] = useState(false);

  return (
    <header className="bg-primary shadow-sm">
      <div className="mx-auto flex max-w-3xl items-center justify-between px-4 py-3">
        {/* Logo + name ("Re" white, "Score" yellow) */}
        <Link to="/" className="flex items-center gap-2 text-2xl font-bold">
          <span className="rounded-lg bg-white p-1">
            <img src="/favicon.svg" alt="" className="h-7 w-7" />
          </span>
          <span>
            <span className="text-white">Re</span>
            <span className="text-accent">Score</span>
          </span>
        </Link>

        {/* Burger button (mobile only) */}
        <button
          type="button"
          className="min-h-11 min-w-11 rounded-lg text-2xl text-white md:hidden"
          aria-label={menuOpen ? "Fermer le menu" : "Ouvrir le menu"}
          aria-expanded={menuOpen}
          onClick={() => setMenuOpen(!menuOpen)}
        >
          {menuOpen ? "✕" : "☰"}
        </button>

        {/* Desktop menu */}
        <nav className="hidden gap-1 md:flex">
          {links.map((link) => (
            <NavLink key={link.to} to={link.to} end className={linkClass}>
              {link.label}
            </NavLink>
          ))}
        </nav>
      </div>

      {/* Mobile menu */}
      {menuOpen && (
        <nav className="border-t border-white/20 px-4 py-2 md:hidden">
          {links.map((link) => (
            <NavLink
              key={link.to}
              to={link.to}
              end
              className={linkClass}
              onClick={() => setMenuOpen(false)}
            >
              {link.label}
            </NavLink>
          ))}
        </nav>
      )}
    </header>
  );
}

export default Header;
