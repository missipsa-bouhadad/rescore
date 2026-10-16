import { useState } from "react";
import { Link, NavLink } from "react-router";

const links = [
  { to: "/", label: "Accueil" },
  { to: "/classement", label: "Classement" },
  { to: "/residences", label: "Résidences" },
  { to: "/matchs", label: "Matchs" },
];

// Active link is green, others normal 
function linkClass({ isActive }: { isActive: boolean }) {
  return `block rounded-lg px-3 py-2 font-semibold ${isActive ? "text-primary" : "text-ink hover:text-primary"}`;
}

function Header() {
  const [menuOpen, setMenuOpen] = useState(false);

  return (
    <header className="bg-white shadow-sm">
      <div className="mx-auto flex max-w-3xl items-center justify-between px-4 py-3">
        <Link to="/" className="text-2xl font-bold text-primary">
          ReScore
        </Link>

        {/* Burger button (mobile only) */}
        <button
          type="button"
          className="min-h-11 min-w-11 rounded-lg text-2xl md:hidden"
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
        <nav className="border-t border-slate-200 px-4 py-2 md:hidden">
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
