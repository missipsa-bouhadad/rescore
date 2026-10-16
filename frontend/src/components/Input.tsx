import { useId, useState, type InputHTMLAttributes } from "react";

type InputProps = InputHTMLAttributes<HTMLInputElement> & {
  label: string;
  error?: string;
};

function Input({ label, error, type = "text", ...props }: InputProps) {
  const id = useId();
  const [showPassword, setShowPassword] = useState(false);
  const isPassword = type === "password";

  return (
    <div>
      <div className="relative">
        <input
          id={id}
          type={isPassword && showPassword ? "text" : type}
          placeholder=" "
          className={`peer h-14 w-full rounded-xl border-2 bg-white px-4 pt-5 text-ink outline-none transition focus:ring-4 ${isPassword ? "pr-20" : ""} ${error ? "border-red-500 focus:ring-red-500/15" : "border-slate-200 focus:border-primary focus:ring-primary/15"}`}
          {...props}
        />

        <label
          htmlFor={id}
          className={`pointer-events-none absolute left-4 top-4 text-slate-500 transition-all peer-focus:top-2 peer-focus:text-xs peer-focus:font-semibold peer-not-placeholder-shown:top-2 peer-not-placeholder-shown:text-xs peer-not-placeholder-shown:font-semibold ${error ? "peer-focus:text-red-600" : "peer-focus:text-primary"}`}
        >
          {label}
        </label>

        {isPassword && (
          <button
            type="button"
            onClick={() => setShowPassword(!showPassword)}
            className="absolute right-3 top-1/2 -translate-y-1/2 rounded-lg px-2 py-1 text-sm font-semibold text-primary hover:bg-primary/10"
          >
            {showPassword ? "Masquer" : "Afficher"}
          </button>
        )}
      </div>

      {error && <p className="mt-1 text-sm text-red-600">{error}</p>}
    </div>
  );
}

export default Input;
