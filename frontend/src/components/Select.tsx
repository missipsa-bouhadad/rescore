import { useEffect, useId, useRef, useState, type KeyboardEvent } from "react";

type Option = {
  value: string;
  label: string;
};

type SelectProps = {
  label: string;
  name: string;
  options: Option[];
  required?: boolean;
};

// Lowercase and remove accents, so "residence" also finds "Résidence"
function simplify(text: string) {
  return text.normalize("NFD").replace(/[̀-ͯ]/g, "").toLowerCase();
}

// A field where you type to search, with suggestions in a list below
function Select({ label, name, options, required }: SelectProps) {
  const id = useId();
  const [open, setOpen] = useState(false);
  const [query, setQuery] = useState("");
  const [value, setValue] = useState("");
  const boxRef = useRef<HTMLDivElement>(null);

  // Options that contain what the user typed
  const suggestions = options.filter((option) => simplify(option.label).includes(simplify(query)));

  // Close the list when clicking outside
  useEffect(() => {
    function handleClick(event: MouseEvent) {
      if (!boxRef.current?.contains(event.target as Node)) setOpen(false);
    }
    document.addEventListener("mousedown", handleClick);
    return () => document.removeEventListener("mousedown", handleClick);
  }, []);

  function choose(option: Option) {
    setValue(option.value);
    setQuery(option.label);
    setOpen(false);
  }

  // Typing a new text removes the previous choice
  function handleType(text: string) {
    setQuery(text);
    setValue("");
    setOpen(true);
  }

  // Enter picks the first suggestion, Escape closes the list
  function handleKeyDown(event: KeyboardEvent) {
    if (event.key === "Enter" && open && suggestions.length > 0) {
      event.preventDefault();
      choose(suggestions[0]);
    }
    if (event.key === "Escape") setOpen(false);
  }

  return (
    <div ref={boxRef} className="relative">
      {/* Hidden field so the value is sent with the form and checked by "required" */}
      <input
        tabIndex={-1}
        name={name}
        value={value}
        required={required}
        onChange={() => {}}
        className="absolute bottom-0 left-4 h-px w-px opacity-0"
      />

      <input
        id={id}
        role="combobox"
        aria-expanded={open}
        autoComplete="off"
        // An empty placeholder lets CSS know when the field is empty
        placeholder=" "
        value={query}
        onChange={(event) => handleType(event.target.value)}
        onFocus={() => setOpen(true)}
        onKeyDown={handleKeyDown}
        className="peer h-14 w-full rounded-xl border-2 border-slate-200 bg-white px-4 pr-10 pt-5 outline-none transition focus:border-primary focus:ring-4 focus:ring-primary/15"
      />

      {/* Same moving label as Input */}
      <label
        htmlFor={id}
        className="pointer-events-none absolute left-4 top-4 text-slate-500 transition-all peer-focus:top-2 peer-focus:text-xs peer-focus:font-semibold peer-focus:text-primary peer-not-placeholder-shown:top-2 peer-not-placeholder-shown:text-xs peer-not-placeholder-shown:font-semibold"
      >
        {label}
      </label>

      {/* Search icon */}
      <svg
        className="pointer-events-none absolute right-4 top-1/2 size-5 -translate-y-1/2 text-slate-400 peer-focus:text-primary"
        viewBox="0 0 24 24"
        fill="none"
        stroke="currentColor"
        strokeWidth="2.5"
        strokeLinecap="round"
        aria-hidden="true"
      >
        <circle cx="11" cy="11" r="7" />
        <line x1="16.5" y1="16.5" x2="21" y2="21" />
      </svg>

      {open && (
        <ul
          role="listbox"
          className="absolute z-10 mt-2 max-h-64 w-full overflow-auto rounded-xl border border-slate-200 bg-white p-1 shadow-lg"
        >
          {suggestions.length === 0 && <li className="px-3 py-3 text-slate-500">Aucune résidence trouvée</li>}

          {suggestions.map((option) => (
            <li key={option.value} role="option" aria-selected={option.value === value}>
              <button
                type="button"
                onClick={() => choose(option)}
                className={`flex w-full items-center justify-between rounded-lg px-3 py-3 text-left hover:bg-primary/10 ${option.value === value ? "font-semibold text-primary" : ""}`}
              >
                {option.label}
                {option.value === value && <span>✓</span>}
              </button>
            </li>
          ))}
        </ul>
      )}
    </div>
  );
}

export default Select;
