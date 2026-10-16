import type { ButtonHTMLAttributes } from "react";

type ButtonProps = ButtonHTMLAttributes<HTMLButtonElement> & {
  variant?: "primary" | "accent" | "outline";
};

const colors = {
  primary: "bg-primary text-white hover:bg-primary-dark",
  accent: "bg-accent text-ink hover:brightness-95",
  outline: "border-2 border-primary text-primary hover:bg-primary hover:text-white",
};

function Button({ variant = "primary", className = "", ...props }: ButtonProps) {
  return (
    <button
      className={`min-h-11 rounded-lg px-4 font-semibold disabled:opacity-50 ${colors[variant]} ${className}`}
      {...props}
    />
  );
}

export default Button;
